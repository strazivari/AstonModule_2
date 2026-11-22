package org.example;

import java.util.Objects;
import java.util.List;

public final class Student {
    private final String name;
    private final List<Book> books;

    public Student(String name, List<Book> books) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Поле Student не может быть пустым");
        }

        Objects.requireNonNull(books, "Book list не может быть Null");

        if(books.size() < 5){
        throw new IllegalArgumentException("У Student должно быть минимум 5 книг");
        }
        this.name = name.trim();
        this.books = List.copyOf(books);
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Студент{" +
                "Имя='" + name + '\'' +
                ", Книги=" + books +
                '}';
    }
}