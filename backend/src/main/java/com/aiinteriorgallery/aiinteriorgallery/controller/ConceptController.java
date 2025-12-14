package com.aiinteriorgallery.aiinteriorgallery.controller;

import com.aiinteriorgallery.aiinteriorgallery.model.Concept;
import com.aiinteriorgallery.aiinteriorgallery.repository.ConceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/concepts")
public class ConceptController {
    
    private final ConceptRepository conceptRepository;
    
    // Constructor injection
    @Autowired
    public ConceptController(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }
    
    /**
     * GET /concepts - Retrieve all concepts
     * @return List of all concepts from database
     */
    @GetMapping
    public ResponseEntity<List<Concept>> getAllConcepts() {
        try {
            List<Concept> concepts = conceptRepository.findAllOrderByIdDesc();
            return ResponseEntity.ok(concepts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /concepts/{id} - Retrieve a specific concept by ID
     * @param id The concept ID
     * @return The concept if found, 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Concept> getConceptById(@PathVariable Long id) {
        try {
            Optional<Concept> concept = conceptRepository.findById(id);
            return concept.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * POST /concepts - Create a new concept
     * @param concept The concept data from request body
     * @return The saved concept with generated ID
     */
    @PostMapping
    public ResponseEntity<Concept> createConcept(@Valid @RequestBody Concept concept) {
        try {
            // Validate required fields
            if (concept.getTitle() == null || concept.getTitle().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            // Set ID to null to ensure new entity creation
            concept.setId(null);
            
            // Save to database
            Concept savedConcept = conceptRepository.save(concept);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedConcept);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * PUT /concepts/{id} - Update an existing concept
     * @param id The concept ID to update
     * @param concept The updated concept data
     * @return The updated concept or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Concept> updateConcept(@PathVariable Long id, @Valid @RequestBody Concept concept) {
        try {
            Optional<Concept> existingConcept = conceptRepository.findById(id);
            
            if (existingConcept.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            // Update fields
            Concept conceptToUpdate = existingConcept.get();
            conceptToUpdate.setTitle(concept.getTitle());
            conceptToUpdate.setPromptArtist(concept.getPromptArtist());
            conceptToUpdate.setAiTool(concept.getAiTool());
            conceptToUpdate.setImageUrl(concept.getImageUrl());
            
            Concept updatedConcept = conceptRepository.save(conceptToUpdate);
            return ResponseEntity.ok(updatedConcept);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * DELETE /concepts/{id} - Delete a concept
     * @param id The concept ID to delete
     * @return 204 No Content if successful, 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcept(@PathVariable Long id) {
        try {
            if (!conceptRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            
            conceptRepository.deleteById(id);
            return ResponseEntity.noContent().build();
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /concepts/search - Search concepts by title
     * @param title The title to search for
     * @return List of matching concepts
     */
    @GetMapping("/search")
    public ResponseEntity<List<Concept>> searchConceptsByTitle(@RequestParam String title) {
        try {
            List<Concept> concepts = conceptRepository.findByTitleContainingIgnoreCase(title);
            return ResponseEntity.ok(concepts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}