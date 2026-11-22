package org.example;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    @Test
    void shouldCreateBook() {
        Book book = new Book("Java Basics", 320, 2021);

        assertEquals("Java Basics", book.getTitle());
        assertEquals(320, book.getPages());
        assertEquals(2021, book.getYear());
    }

    @Test
    void shouldConsiderIdenticalBooksEqual() {
        Book first = new Book("Java Basics", 320, 2021);
        Book second = new Book("Java Basics", 320, 2021);

        assertNotSame(first, second);
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldConsiderDifferentBooksNotEqual() {
        Book first = new Book("Java Basics", 320, 2021);
        Book second = new Book("Java Basics", 400, 2021);

        assertNotEquals(first, second);
    }

    @Test
    void shouldRemoverDuplicateBooks() {
        Book first = new Book("Java Basics", 320, 2021);
        Book second = new Book("Java Basics", 320, 2021);
        Book third = new Book("Java Basics", 464, 2008);

        HashSet<Book> uniqueBooks = new HashSet<>(
                List.of(first, second, third)
        );

        assertEquals(2, uniqueBooks.size());
    }

    @Test
    void shouldRejectInvalidBookData() {
        assertAll(
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Book("", 320, 2021)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Book(null, 320, 2021)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Book("Java", 0, 2021)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Book("Java", 320, -1)
                )
        );
    }
}
