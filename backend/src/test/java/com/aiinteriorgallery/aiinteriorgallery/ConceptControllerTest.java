package com.aiinteriorgallery.aiinteriorgallery;

import com.aiinteriorgallery.aiinteriorgallery.controller.ConceptController;
import com.aiinteriorgallery.aiinteriorgallery.model.Concept;
import com.aiinteriorgallery.aiinteriorgallery.repository.ConceptRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConceptController.class)
class ConceptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConceptRepository conceptRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllConcepts_ShouldReturnListOfConcepts() throws Exception {
        // Given
        List<Concept> concepts = Arrays.asList(
            new Concept("Modern Living Room", "John Doe", "DALL-E", "https://example.com/image1.jpg"),
            new Concept("Cozy Bedroom", "Jane Smith", "Midjourney", "https://example.com/image2.jpg")
        );
        
        when(conceptRepository.findAllOrderByIdDesc()).thenReturn(concepts);

        // When & Then
        mockMvc.perform(get("/concepts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Modern Living Room"))
                .andExpect(jsonPath("$[1].title").value("Cozy Bedroom"));
    }

    @Test
    void createConcept_ShouldReturnCreatedConcept() throws Exception {
        // Given
        Concept newConcept = new Concept("Test Concept", "Test Artist", "Test Tool", "https://example.com/test.jpg");
        Concept savedConcept = new Concept("Test Concept", "Test Artist", "Test Tool", "https://example.com/test.jpg");
        savedConcept.setId(1L);
        
        when(conceptRepository.save(any(Concept.class))).thenReturn(savedConcept);

        // When & Then
        mockMvc.perform(post("/concepts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newConcept)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Concept"))
                .andExpect(jsonPath("$.promptArtist").value("Test Artist"));
    }

    @Test
    void createConcept_WithEmptyTitle_ShouldReturnBadRequest() throws Exception {
        // Given
        Concept invalidConcept = new Concept("", "Test Artist", "Test Tool", "https://example.com/test.jpg");

        // When & Then
        mockMvc.perform(post("/concepts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidConcept)))
                .andExpect(status().isBadRequest());
    }
}