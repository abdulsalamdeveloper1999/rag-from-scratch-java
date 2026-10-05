package com.abdul.rag.services;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private final EmbeddingService embeddingService;
    private final JdbcTemplate jdbcTemplate;

    public DocumentService(EmbeddingService embeddingService, JdbcTemplate jdbcTemplate) {
        this.embeddingService = embeddingService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addDocument(String content) {
        List<Double> embedding = embeddingService.createEmbedding(content);

        String vector = embedding.toString();

        jdbcTemplate.update(

                """
                        INSERT INTO documents (content, embedding)
                        VALUES (?, ?::vector)
                        """, content, embedding

        );

    }

}
