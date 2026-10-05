package com.abdul.rag.model;

import java.util.List;

public record LlmResponse(
        List<OutputItem> output) {

    public record OutputItem(
            String type,
            List<Content> content) {
    }

    public record Content(
            String type,
            String text) {
    }
}