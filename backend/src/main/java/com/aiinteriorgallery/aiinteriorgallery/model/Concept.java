package com.aiinteriorgallery.aiinteriorgallery.model;

import jakarta.persistence.*;

@Entity
@Table(name = "concepts")
public class Concept {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(name = "prompt_artist")
    private String promptArtist;
    
    @Column(name = "ai_tool")
    private String aiTool;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    // No-args constructor (required by JPA)
    public Concept() {}
    
    // Constructor with all fields except id
    public Concept(String title, String promptArtist, String aiTool, String imageUrl) {
        this.title = title;
        this.promptArtist = promptArtist;
        this.aiTool = aiTool;
        this.imageUrl = imageUrl;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    @Override
    public String toString() {
        return "Concept{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", promptArtist='" + promptArtist + '\'' +
                ", aiTool='" + aiTool + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}