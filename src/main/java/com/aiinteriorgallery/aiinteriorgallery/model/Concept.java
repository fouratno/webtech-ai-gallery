package com.aiinteriorgallery.aiinteriorgallery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "concepts")
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String promptArtist;
    private String aiTool;
    private String imageUrl;

    public Concept() {
        // Default constructor required by JPA
    }

    public Concept(String title, String promptArtist, String aiTool, String imageUrl) {
        this.title = title;
        this.promptArtist = promptArtist;
        this.aiTool = aiTool;
        this.imageUrl = imageUrl;
    }

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
}
