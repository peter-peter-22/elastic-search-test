package com.example.elastic_search_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.data.util.Streamable;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ElasticsearchOperations operations;

    @GetMapping("/search/title")
    public List<Book> searchByTitle(@RequestParam String query) {
        return bookRepository.searchByTitle(query);
    }

    @GetMapping("/search/multi")
    public List<Book> searchMulti(@RequestParam String query) {
        return bookRepository.searchMultiField(query);
    }

    @GetMapping("/filter/author")
    public List<Book> filterByAuthor(@RequestParam String authorId) {
        return bookRepository.filterByAuthorId(authorId);
    }

    @GetMapping("/search/fuzzy")
    public List<Book> fuzzySearch(@RequestParam String query) {
        return bookRepository.fuzzySearchByTitle(query);
    }

    @GetMapping("/predict/title")
    public List<Book> predictTitle(@RequestParam String prefix) {
        return bookRepository.predictTitle(prefix);
    }

    @GetMapping("/sorted/rating")
    public List<Book> getSortedByRating() {
        return Streamable.of(bookRepository.findAll(Sort.by(Sort.Direction.DESC, "rating"))).toList();
    }

    @GetMapping("/search/sorted")
    public List<Book> searchSorted(@RequestParam String query) {
        Query searchQuery = new StringQuery("{\"match\": {\"title\": \"" + query + "\"}}")
                .addSort(Sort.by(Sort.Direction.DESC, "rating").and(Sort.by(Sort.Direction.ASC, "_score")));
        SearchHits<Book> searchHits = operations.search(searchQuery, Book.class);
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
