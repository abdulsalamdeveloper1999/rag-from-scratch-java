package com.abdul.rag.model;

public class AskModels {

    public record AskQuestion(String question) {
    }

    public record AskResponse(String question, String answer) {
    }

}