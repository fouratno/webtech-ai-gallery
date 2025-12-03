package com.aiinteriorgallery.aiinteriorgallery.model;

public class Concept {
    private String title;
    private String promptArtist;
    private String aiTool;
    private String imageUrl;

    // ✅ Default constructor (required for Jackson)
    public Concept() {}

    // ✅ Full constructor (optional, for your own use)
    public Concept(String title, String promptArtist, String aiTool, String imageUrl) {
        this.title = title;
        this.promptArtist = promptArtist;
        this.aiTool = aiTool;
        this.imageUrl = imageUrl;
    }

    // ✅ Getters and setters (Jackson needs these)
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getPromptArtist() {
        return promptArtist;
    }
    public void setPromptArtist(String promptArtist) {
        this.promptArtist = promptArtist;
    }

    public String getAiTool() {
        return aiTool;
    }
    public void setAiTool(String aiTool) {
        this.aiTool = aiTool;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
