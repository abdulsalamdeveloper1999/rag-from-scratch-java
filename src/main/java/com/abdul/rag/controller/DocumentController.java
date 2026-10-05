package com.abdul.rag.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdul.rag.model.DocumentModels.AddDocumentRequest;
import com.abdul.rag.model.DocumentModels.AddDocumentResponse;
import com.abdul.rag.services.DocumentService;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService dService;

    public DocumentController(DocumentService dService) {
        this.dService = dService;
    }

    @PostMapping
    public AddDocumentResponse addDocument(@RequestBody AddDocumentRequest req) {
        dService.addDocument(req.content());

        return new AddDocumentResponse("Document added successfully");

    }

}
