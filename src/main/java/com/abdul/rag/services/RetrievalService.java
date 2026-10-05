package com.abdul.rag.services;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.abdul.rag.model.RetrievalResult;

@Service
public class RetrievalService {

    private final JdbcTemplate jdbcTemplate;

    public RetrievalService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RetrievalResult> search(List<Double> embedding, double threshold, int limit) {

        String vector = embedding.toString();

        return jdbcTemplate.query("""
                SELECT
                    id,
                    content,
                    embedding <=> ?::vector AS distance
                FROM documents
                WHERE embedding <=> ?::vector < ?
                ORDER BY embedding <=> ?::vector
                LIMIT ?
                """, (rs, rowNum) -> new RetrievalResult(rs.getLong("id"), rs.getString("content"),
                rs.getDouble("distance")), vector, vector, threshold, vector, limit);

    }

}
