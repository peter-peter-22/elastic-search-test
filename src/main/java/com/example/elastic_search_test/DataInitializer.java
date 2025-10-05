package com.example.elastic_search_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        // Delete existing index if needed
        bookRepository.deleteAll();

        // Add sample books
        Book book1 = new Book("1", "Elasticsearch Basics", "Introduction to search engines", "author123", 4.5);
        Book book2 = new Book("2", "Spring Boot Guide", "Building apps with Spring", "author456", 4.8);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}
