package com.aiinteriorgallery.aiinteriorgallery.model;

public class Concept {
    private String title;
    private String promptArtist;
    private String aiTool;

    public Concept(String title, String promptArtist, String aiTool) {
        this.title = title;
        this.promptArtist = promptArtist;
        this.aiTool = aiTool;
    }

    public String getTitle() { return title; }
    public String getPromptArtist() { return promptArtist; }
    public String getAiTool() { return aiTool; }
}
