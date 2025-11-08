package com.example.elastic_search_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private final BookService bookService;

    public DataInitializer(BookService service) {
        this.bookService = service;
    }

    @Override
    public void run(String... args) {
        // Delete existing index if needed
        bookService.deleteAll();

        // Add sample books
        List<Book> books = List.of(
                new Book(null, "Elasticsearch Basics", "Introduction to search engines", "author123", 4.5f),
                new Book(null, "Spring Boot Guide", "Building apps with Spring", "author123", 4.8f),
                new Book(null, "Elasticsearch Intermediate ", "Advanced usage of search engines", "author123", 5.0f),
                new Book(null, "The Car", "A car that goes forward", "author456", 4.2f),
                new Book(null, "The Next Car", "A car that goes faster", "author456", 4.7f),
                new Book(null, "The Slower Car", "A car that goes slower", "author456", 3.5f),
                new Book(null, "The Dead Car", "A car that doesn't go forward", "author456", 1.0f),
                new Book(null, "The Inverted Car", "A car that only goes backwards", "author456", 0.1f)
        );
        bookService.saveAll(books);
    }
}
