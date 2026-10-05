package com.abdul.rag.model;

public class DocumentModels {

    public record AddDocumentRequest(String content) {
    }

    public record AddDocumentResponse(String message) {
    }

}