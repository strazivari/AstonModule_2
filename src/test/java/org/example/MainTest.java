package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

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
    void shouldFindFirstYearAfterSortingAndFiltering() throws Exception {

        Path path = Path.of(Objects.requireNonNull(getClass().getResource("/studentsTest")).toURI());

        List<Student> students = StudentFileReader.readStudents(path);

        String output = captureOutput(students);

        assertAll(
                () -> assertTrue(output.contains("Студент{Имя='Vlad'")),
                () -> assertTrue(output.contains("Студент{Имя='Anna'")),
                () -> assertTrue(output.contains("Студент{Имя='Oleg'")),
                () -> assertTrue(output.contains("Год: 2005"),
                        () -> "Фактический вывод:\n" + output
                )
        );

    }

    @Test
    void shouldPrintMessageWhenNoBooksMatch() {

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
