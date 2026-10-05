package com.abdul.rag.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdul.rag.model.AskModels.AskQuestion;
import com.abdul.rag.model.AskModels.AskResponse;
import com.abdul.rag.services.RagService;

@RestController
@RequestMapping("/api/rag")
public class RagController {
    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/ask")
    public AskResponse ask(@RequestBody AskQuestion request) {
        System.out.println("Question received: " + request.question());
        String answer = ragService.ask(request.question());
        return new AskResponse(request.question(), answer);
    }
}
