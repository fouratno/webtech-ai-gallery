package com.aiinteriorgallery.aiinteriorgallery.controller;

import com.aiinteriorgallery.aiinteriorgallery.model.Concept;
import com.aiinteriorgallery.aiinteriorgallery.service.ConceptService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/concepts")
public class ConceptController {

    private final ConceptService conceptService;

    public ConceptController(ConceptService conceptService) {
        this.conceptService = conceptService;
    }

    @GetMapping
    public ResponseEntity<List<Concept>> getConcepts() {
        return ResponseEntity.ok(conceptService.getAllConcepts());
    }

    @PostMapping
    public ResponseEntity<?> createConcept(
            @RequestBody Concept concept, UriComponentsBuilder uriComponentsBuilder) {
        String title = trim(concept.getTitle());
        String promptArtist = trim(concept.getPromptArtist());
        String aiTool = trim(concept.getAiTool());
        String imageUrl = trim(concept.getImageUrl());

        if (!StringUtils.hasText(title)) {
            return ResponseEntity.badRequest().body("Title is required");
        }
        if (!StringUtils.hasText(promptArtist)) {
            return ResponseEntity.badRequest().body("Prompt artist is required");
        }
        if (!StringUtils.hasText(aiTool)) {
            return ResponseEntity.badRequest().body("AI tool is required");
        }
        if (!StringUtils.hasText(imageUrl)) {
            return ResponseEntity.badRequest().body("Image URL is required");
        }
        if (imageUrl.length() > 500) {
            return ResponseEntity.badRequest().body("Image URL must be 500 characters or fewer");
        }

        concept.setTitle(title);
        concept.setPromptArtist(promptArtist);
        concept.setAiTool(aiTool);
        concept.setImageUrl(imageUrl);

        Concept created = conceptService.createConcept(concept);
        URI location = uriComponentsBuilder.path("/concepts/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
