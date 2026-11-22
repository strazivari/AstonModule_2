package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    private List<Book> createFiveBooks() {
        return List.of(
                new Book("Java", 300, 2020),
                new Book("Spring", 400, 2021),
                new Book("SQL", 250, 2019),
                new Book("Docker", 200, 2022),
                new Book("Linux", 350, 2018)
        );
    }

    @Test
    void shouldCreateStudentWithFiveBooks() {
        Student student = new Student("Vlad", createFiveBooks());
        assertEquals("Vlad", student.getName());
        assertEquals(5, student.getBooks().size());
    }

    @Test
    void shouldRejectLessThanFiveBooks() {
        List<Book> books = createFiveBooks().subList(0,4);
        assertThrows(IllegalArgumentException.class, () ->
                new Student("Vlad", books)
        );
    }

    @Test
    void shouldRejectInvalidStudentData() {
        assertAll(
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Student("", createFiveBooks())
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Student("   ", createFiveBooks())
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Student(null, createFiveBooks())
                ),
                () -> assertThrows(
                        NullPointerException.class,
                        () -> new Student("Vlad", null)
                )
        );
    }

    @Test
    void shouldProtectBooksFromExternalChanges() {
        List<Book> original = new ArrayList<>(createFiveBooks());
        Student student = new Student("Vlad", original);

        original.clear();

        assertEquals(5, student.getBooks().size());

        assertThrows(UnsupportedOperationException.class, () ->
                student.getBooks().clear()
        );
    }

    @Test
    void shouldHaveReadableToString() {
        Student student = new Student("Vlad", createFiveBooks());

        String result = student.toString();

        assertTrue(result.contains("Vlad"));
        assertTrue(result.contains("Java"));
        assertTrue(result.contains("2020"));
    }
}
