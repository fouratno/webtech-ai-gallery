package com.aiinteriorgallery.aiinteriorgallery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "concepts")
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String promptArtist;

    @NotBlank
    @Column(nullable = false)
    private String aiTool;

    @NotBlank
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String imageUrl;

    public Concept() {
    }

    public Concept(String title, String promptArtist, String aiTool, String imageUrl) {
        this.title = title;
        this.promptArtist = promptArtist;
        this.aiTool = aiTool;
        this.imageUrl = imageUrl;
    }

    public Concept(Long id, String title, String promptArtist, String aiTool, String imageUrl) {
        this.id = id;
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
