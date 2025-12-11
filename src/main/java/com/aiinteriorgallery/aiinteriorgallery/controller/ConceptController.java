package com.aiinteriorgallery.aiinteriorgallery.controller;

import com.aiinteriorgallery.aiinteriorgallery.model.Concept;
import com.aiinteriorgallery.aiinteriorgallery.repository.ConceptRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/concepts")
@CrossOrigin
public class ConceptController {

    private final ConceptRepository conceptRepository;

    public ConceptController(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    @GetMapping
    public ResponseEntity<List<Concept>> getConcepts() {
        List<Concept> concepts = conceptRepository.findAll();
        return ResponseEntity.ok(concepts);
    }

    @PostMapping
    public ResponseEntity<Concept> createConcept(@RequestBody Concept concept) {
        Concept saved = conceptRepository.save(concept);
        return ResponseEntity
                .created(URI.create("/concepts/" + saved.getId()))
                .body(saved);
    }
}
