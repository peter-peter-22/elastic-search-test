package com.example.elastic_search_test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Document(indexName = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookIndex {
    @Id
    private UUID id;

    @Field(type = FieldType.Text)  // Analyzed for full-text search
    private String title;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)  // Exact match for filtering/sorting
    private String authorId;

    @Field(type = FieldType.Double)
    private Double rating;
}
