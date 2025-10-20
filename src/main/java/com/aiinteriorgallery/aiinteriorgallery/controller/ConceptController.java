package com.aiinteriorgallery.aiinteriorgallery.controller;

import com.aiinteriorgallery.aiinteriorgallery.model.Concept;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ConceptController {

    @GetMapping("/concepts")
    public ResponseEntity<List<Concept>> getConcepts() {
        List<Concept> concepts = List.of(
                new Concept("Tropical Kitchen", "Furat Nouairia", "Midjourney v6"),
                new Concept("Minimalist Living Room", "AI Studio", "SDXL"),
                new Concept("Rustic Bedroom", "Luna Design", "DALL·E 3")
        );
        return ResponseEntity.ok(concepts);
    }
}
