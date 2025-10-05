package com.example.elastic_search_test;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookRepository extends ElasticsearchRepository<Book, String> {
    @Query("{\"match\": {\"title\": \"?0\"}}")
    List<Book> searchByTitle(String query);

    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title^2\", \"description\"]}}")  // Boost title
    List<Book> searchMultiField(String query);

    @Query("{\"bool\": {\"filter\": {\"term\": {\"authorId\": \"?0\"}}}}")
    List<Book> filterByAuthorId(String authorId);

    //@Query("{\"fuzzy\": {\"title\": {\"value\": \"?0\", \"fuzziness\": 2}}}")
    @Query("{\"match\": {\"title\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}")
    List<Book> fuzzySearchByTitle(String query);

    @Query("{\"match_phrase_prefix\": {\"title\": {\"query\": \"?0\", \"max_expansions\": 50}}}")
    List<Book> predictTitle(String prefix);


}