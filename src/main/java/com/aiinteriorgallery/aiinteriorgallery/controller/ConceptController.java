package com.aiinteriorgallery.aiinteriorgallery.controller;

import com.aiinteriorgallery.aiinteriorgallery.model.Concept;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class ConceptController {

    @GetMapping("/concepts")
    public ResponseEntity<List<Concept>> getConcepts() {
        try {
            // Load JSON file from resources/data
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("data/concepts.json").getInputStream();
            List<Concept> concepts = mapper.readValue(inputStream, new TypeReference<>() {
            });
            return ResponseEntity.ok(concepts);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}