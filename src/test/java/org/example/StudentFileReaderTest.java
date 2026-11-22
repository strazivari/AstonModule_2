package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Objects;

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
    void shouldReadStudentsFromFile() throws IOException, URISyntaxException {
        Path path = Path.of(Objects.requireNonNull(getClass().getResource("/studentsTest")).toURI());

        List<Student> students = StudentFileReader.readStudents(path);

        assertEquals(3, students.size());

        assertEquals("Vlad", students.get(0).getName());
        assertEquals("Anna", students.get(1).getName());
        assertEquals("Oleg", students.get(2).getName());

        for (Student student : students) {
            assertEquals(5, student.getBooks().size());
        }

        Book firstBook = students.get(0).getBooks().getFirst();

        assertEquals("Old Book", firstBook.getTitle());
        assertEquals(100, firstBook.getPages());
        assertEquals(1999, firstBook.getYear());
    }

    @Test
    void shouldSkipEmptyLines() throws IOException {
        String content = "\n"
                + "Vlad|Java,300,2020;Spring,400,2021;SQL,250,2019;Docker,200,2022;Linux,350,2018"
                + "\n"
                + "  \n";

        Path path = createFile(content);

        List<Student> students = StudentFileReader.readStudents(path);

        assertEquals(1, students.size());
    }

    @Test
    void shouldRejectInvalidStudentFormat() throws IOException {
        Path path = createFile("Vlad without separator");

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
