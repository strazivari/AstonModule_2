import org.example.Student;
import org.example.StudentFileReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.NoSuchFileException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentFileReaderTest {

    @TempDir
    Path tempDir;

    private Path createFile(String content) throws IOException {
        Path path = tempDir.resolve("students");

        Files.writeString(path, content, StandardCharsets.UTF_8);
        return path;
    }

    @Test
    void shouldReadStudentsFromFile() throws IOException {
        String content = """
                Vlad|Java,300,2020;String,400,2021;SQL,250,2019;Docker,200
                Anna|HTML,150,2020;CSS,180,2021;JS,300,2022;React,400,2023
                """;

        Path path = createFile(content);

        List<Student> students = StudentFileReader.readStudents(path);

        assertEquals(2, students.size());
        assertEquals("Vlad", students.get(0).getName());
        assertEquals("Anna", students.get(1).getName());

        assertEquals(5, students.get(0).getBooks().size());
        assertEquals(5, students.get(1).getBooks().size());

        assertEquals("Java", students.get(0).getBooks().get(0).getTitle());

        assertEquals(2020, students.get(0).getBooks().get(0).getYear());
    }

    @Test
    void shouldSkipEmptyLines() throws IOException {
        String content = """
                Vlad|Java,300,2020;Spring,400,2021;SQL,250,2019;Docker,200,2022;Linux,350,2018
                """;

        Path path = createFile(content);

        List<Student> students = StudentFileReader.readStudents(path);

        assertEquals(1, students.size());
    }

    @Test
    void shouldRejectInvalidStudentFormat() throws IOException {
        Path path = createFile("Vlad withiut separator");

        assertThrows(IllegalArgumentException.class, () ->
                StudentFileReader.readStudents(path)
        );
    }

    @Test
    void shouldRejectInvalidBookFormat() throws IOException {
        Path path = createFile("Vlad|Java,300;Spring,400,2021");

        assertThrows(IllegalArgumentException.class, () -> StudentFileReader.readStudents(path));
    }

    @Test
    void shouldRejectInvalidPageNumber() throws IOException {
        Path path = createFile("Vlad|Java,abc,2020;Spring,400,2021");

        assertThrows(IllegalArgumentException.class, () -> StudentFileReader.readStudents(path));
    }

    @Test void shouldRejectStudentWithFourBooks() throws IOException {
        Path path = createFile("Vlad|Java,300,2020;Spring,400,2021;SQL,250,2019;Docker,200,2022");

        assertThrows(IllegalArgumentException.class, () -> StudentFileReader.readStudents(path));
    }

    @Test
    void shouldThrowExceptionForMissingFile() {
        Path missing = tempDir.resolve("missing");

        assertThrows(NoSuchFileException.class, () -> StudentFileReader.readStudents(missing));
    }
}
