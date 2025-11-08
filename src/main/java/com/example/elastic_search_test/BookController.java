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
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookIndexRepository bookIndexRepository;
    @Autowired
    private ElasticsearchOperations operations;
    @Autowired
    private BookService bookService;

    @GetMapping("/search/title")
    public List<BookIndex> searchByTitle(@RequestParam String query) {
        return bookIndexRepository.searchByTitle(query);
    }

    @GetMapping("/search/multi")
    public List<BookIndex> searchMulti(@RequestParam String query) {
        return bookIndexRepository.searchMultiField(query);
    }

    @GetMapping("/filter/author")
    public List<BookIndex> filterByAuthor(@RequestParam String authorId) {
        return bookIndexRepository.filterByAuthorId(authorId);
    }

    @GetMapping("/search/fuzzy")
    public List<BookIndex> fuzzySearch(@RequestParam String query) {
        return bookIndexRepository.fuzzySearchByTitle(query);
    }

    @GetMapping("/predict/title")
    public List<BookIndex> predictTitle(@RequestParam String prefix) {
        return bookIndexRepository.predictTitle(prefix);
    }

    @GetMapping("/sorted/rating")
    public List<BookIndex> getSortedByRating() {
        return Streamable.of(bookIndexRepository.findAll(Sort.by(Sort.Direction.DESC, "rating"))).toList();
    }

    @GetMapping("/search/sorted")
    public List<BookIndex> searchSorted(@RequestParam String query) {
        Query searchQuery = new StringQuery("{\"match\": {\"title\": \"" + query + "\"}}")
                .addSort(Sort.by(Sort.Direction.DESC, "rating").and(Sort.by(Sort.Direction.ASC, "_score")));
        SearchHits<BookIndex> searchHits = operations.search(searchQuery, BookIndex.class);
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable UUID id) {
        return bookService.getById(id);
    }
}
