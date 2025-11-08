package com.example.elastic_search_test.search;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.UUID;

public interface BookIndexRepository extends ElasticsearchRepository<BookIndex, UUID> {
    @Query("{\"match\": {\"title\": \"?0\"}}")
    List<BookIndex> searchByTitle(String query);

    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title^2\", \"description\"]}}")
        // Boost title
    List<BookIndex> searchMultiField(String query);

    @Query("{\"bool\": {\"filter\": {\"term\": {\"authorId\": \"?0\"}}}}")
    List<BookIndex> filterByAuthorId(String authorId);

    //@Query("{\"fuzzy\": {\"title\": {\"value\": \"?0\", \"fuzziness\": 2}}}")
    @Query("{\"match\": {\"title\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}")
    List<BookIndex> fuzzySearchByTitle(String query);

    @Query("{\"match_phrase_prefix\": {\"title\": {\"query\": \"?0\", \"max_expansions\": 50}}}")
    List<BookIndex> predictTitle(String prefix);


}