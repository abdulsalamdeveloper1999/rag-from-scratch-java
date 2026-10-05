package com.abdul.rag.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.abdul.rag.model.EmbeddingResponse;

@Service
public class EmbeddingService {
    private final RestClient restClient;

    public EmbeddingService(@Value("${openai.api.key}") String apiKey) {
        this.restClient = RestClient.builder().baseUrl("https://api.openai.com")
                .defaultHeader("Authorization", "Bearer " + apiKey).build();
    }

    public List<Double> createEmbedding(String text) {
        Map<String, Object> req = Map.of(
                "model", "text-embedding-3-small",
                "input", text);

        EmbeddingResponse embeddingResponse = restClient.post().uri("/v1/embeddings").body(req).retrieve()
                .body(EmbeddingResponse.class);

        return embeddingResponse.data().get(0).embedding();
    }

}
