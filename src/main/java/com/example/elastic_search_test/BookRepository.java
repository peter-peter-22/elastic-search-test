package com.example.elastic_search_test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    @Modifying
    @Query(value = "delete from books", nativeQuery = true)
    void deleteAll();
}