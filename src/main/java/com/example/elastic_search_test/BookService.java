package com.example.elastic_search_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public Book save(Book book) {
        return repository.save(book);
    }

    public List<Book> saveAll(List<Book> book) {
        return repository.saveAll(book);
    }

    public List<Book> all() {
        return repository.findAll();
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public Book getById(UUID id) {
        return repository.findById(id).orElse(null);
    }
}

