package com.aiinteriorgallery.aiinteriorgallery.controller;

import com.aiinteriorgallery.aiinteriorgallery.model.Concept;
import com.aiinteriorgallery.aiinteriorgallery.repository.ConceptRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/concepts")
public class ConceptController {

    private final ConceptRepository conceptRepository;

    public ConceptController(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    @GetMapping
    public ResponseEntity<List<Concept>> getConcepts() {
        return ResponseEntity.ok(conceptRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Concept> createConcept(@RequestBody Concept concept) {
        Concept savedConcept = conceptRepository.save(concept);
        return ResponseEntity.ok(savedConcept);
    }
}
