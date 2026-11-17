import org.example.Book;
import org.example.Main;
import org.example.Student;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private String captureOutput(List<Student> students) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (PrintStream capture = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);

            Main.processStudents(students);
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }

    @Test
    void shouldFindFirstYearAfterSortingAndFiltering() {

        Student vlad = new Student("vlad", List.of(
                new Book("Old book", 100, 1999),
                new Book("Java", 400, 2020),
                new Book("Spring", 250, 2015),
                new Book("Docker", 300, 2022),
                new Book("Shared", 125, 2005)
        ));

        Student anna = new Student("Anna", List.of(
                new Book("Shared", 125, 2005),
                new Book("Testing", 110, 2017),
                new Book("SQL", 200, 2001),
                new Book("Linux", 300, 1990),
                new Book("Cloud", 500, 2023)
        ));

        String output = captureOutput(List.of(vlad, anna));

        assertTrue(output.contains("student{name='Vlad'"));
        assertTrue(output.contains("student{name='Anna'"));

        assertTrue(output.contains("Год: 2017"));
    }

    @Test
    void shouldPrintMessageWhenMoBooksMatch() {

        Student student = new Student("Vlad", List.of(
                new Book("Book A", 100, 1990),
                new Book("Book C", 200, 1995),
                new Book("Book B", 300, 1998),
                new Book("Book D", 400, 1999),
                new Book("Book E", 500, 2000)
        ));

        String output = captureOutput(List.of(student));

        assertTrue(output.contains("Книга не найдена"));
    }
}
