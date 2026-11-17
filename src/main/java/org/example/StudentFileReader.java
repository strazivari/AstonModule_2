package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StudentFileReader {

    private StudentFileReader() {

    }

    public static List<Student> readStudents(Path path)
        throws IOException {
        List<Student> students = new ArrayList<>();

        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        for (String line : lines) {

            if (line.isBlank()) {
                continue;
            }

            String[] studentData = line.split("\\|", -1);

            if (studentData.length !=2) {
                throw new IllegalArgumentException("Неверный формат:" + line);
            }

            String studentName = studentData[0].trim();

            List<Book> books = createBooks(studentData);

            students.add(new Student(studentName, books));
        }

        return students;
    }

    private static List<Book> createBooks(String[] studentData) {
        List<Book> books = new ArrayList<>();

        String[] bookEntries = studentData[1].split(";", -1);

        for (String bookEntry : bookEntries) {

            String[] bookData = bookEntry.split(",", -1);

            if (bookData.length != 3) {
                throw new IllegalArgumentException("Неверный формат данных:" + bookEntry);
            }
            String title = bookData[0].trim();

            int pages;
            int year;

            try {
                pages = Integer.parseInt(bookData[1].trim());

                year = Integer.parseInt(bookData[2].trim());
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("Неверный number: " + bookEntry, e);
            }

            books.add(new Book(title, pages, year));
        }
        return books;
    }
}
