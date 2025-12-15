package com.aiinteriorgallery.aiinteriorgallery.controller;

import com.aiinteriorgallery.aiinteriorgallery.model.Concept;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@CrossOrigin(origins = "*") // allow frontend on Render to call the API
public class ConceptController {

    @GetMapping("/concepts")
    public ResponseEntity<List<Concept>> getConcepts() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Load JSON file from src/main/resources/data/concepts.json
            InputStream inputStream =
                    new ClassPathResource("data/concepts.json").getInputStream();

            List<Concept> concepts = mapper.readValue(
                    inputStream,
                    new TypeReference<List<Concept>>() {}
            );

            return ResponseEntity.ok(concepts);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
