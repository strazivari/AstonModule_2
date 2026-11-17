package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        List<Student> students = StudentFileReader.readStudents(
                Path.of("src/main/resources/students")
        );

        students.stream()
                .peek(System.out::println)
                .map(Student::getBooks)
                .flatMap(List::stream)
                .sorted(Comparator.comparingInt(Book::getPages))
                .distinct()
                .filter(book -> book.getYear() > 2000)
                .limit(3)
                .map(Book::getYear)
                .findFirst()
                .ifPresentOrElse(
                        year -> System.out.println("Год: " + year),
                        () -> System.out.println("Book not found")
                );
    }
}
