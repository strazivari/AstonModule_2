package org.example;

import java.util.Objects;

public final class Book {
    private final String title;
    private final int pages;
    private final int year;

    public Book(String title, int pages, int year) {

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Название книги должно быть заполнено");
        }

        if (pages <= 0) {
            throw new IllegalArgumentException("Количество страниц должно быть > 0");
        }

        if (year <= 0) {
            throw new IllegalArgumentException("некорректный год публикации");
        }

        this.title = title.trim();
        this.pages = pages;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public int getPages() {
        return  pages;
    }

    public int getYear() {
        return year;
    }

    @Override
    public  boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Book other)) {
            return false;
        }

        return pages == other.pages
                && year == other.year
                && title.equals(other.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, pages, year);
    }

    @Override
    public String toString() {
        return "Книга {" +
                "Название='" + title + '\'' +
                ", страницы=" + pages +
                ", год=" + year +
                '}';
    }
}
