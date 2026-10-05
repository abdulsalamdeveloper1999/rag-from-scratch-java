package com.abdul.rag.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.abdul.rag.model.LlmResponse;
import com.abdul.rag.model.LlmResponse.Content;
import com.abdul.rag.model.LlmResponse.OutputItem;
import com.abdul.rag.model.RetrievalResult;

@Service
public class RagService {

    private final EmbeddingService embeddingService;
    private final RetrievalService retrievalService;
    private final RestClient restClient;

    public RagService(EmbeddingService embeddingService, RetrievalService retrievalService,
            @Value("${openai.api.key}") String apikey) {
        this.embeddingService = embeddingService;
        this.retrievalService = retrievalService;

        this.restClient = RestClient.builder().baseUrl("https://api.openai.com")
                .defaultHeader("Authorization", "Bearer " + apikey).build();

    }

    public String ask(String question) {

        List<Double> embeddings = embeddingService.createEmbedding(question);

        List<RetrievalResult> results = retrievalService.search(embeddings, 0.70, 4);

        if (results.isEmpty()) {
            return "I don't have enough information in the provided data.";
        }

        StringBuilder contextBuilder = new StringBuilder();

        results.forEach(result -> contextBuilder.append("- ").append(result.content()).append("\n"));

        String prompt = """
                Answer the question using only the context below.

                If the context does not contain enough information, say:
                "I don't have enough information in the provided data."

                Context:
                %s

                Question:
                %s
                """.formatted(contextBuilder, question);

        Map<String, Object> req = Map.of(
                "model", "gpt-6-luna",
                "input", prompt

        );

        LlmResponse response = restClient.post().uri("/v1/responses").body(req).retrieve().body(LlmResponse.class);
        System.out.println(response);
        for (OutputItem item : response.output()) {
            if ("message".equals(item.type())) {
                for (Content content : item.content()) {
                    if ("output_text".equals(content.type())) {
                        return content.text();
                    }
                }

            }
        }

        return "No answer returned";

    }

}
