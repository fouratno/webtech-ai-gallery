package com.aiinteriorgallery.aiinteriorgallery.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.aiinteriorgallery.aiinteriorgallery.model.Concept;
import com.aiinteriorgallery.aiinteriorgallery.repository.ConceptRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ConceptControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ConceptRepository conceptRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        conceptRepository.deleteAll();
        conceptRepository.saveAll(
                List.of(
                        new Concept("Loft Living", "Studio A", "Midjourney", "https://example.com/one.jpg"),
                        new Concept(
                                "Cozy Bedroom", "Luna", "DALL·E", "https://example.com/two.jpg")));
    }

    @Test
    void returnsPersistedConcepts() throws Exception {
        mockMvc
                .perform(get("/concepts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", equalTo("Loft Living")))
                .andExpect(jsonPath("$[1].promptArtist", equalTo("Luna")));
    }

    @Test
    void createsAndReturnsConcept() throws Exception {
        Concept request =
                new Concept(
                        "Sunny Kitchen",
                        "Nova",
                        "Stable Diffusion",
                        "https://example.com/three.jpg");

        mockMvc
                .perform(
                        post("/concepts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title", equalTo("Sunny Kitchen")));

        mockMvc.perform(get("/concepts")).andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void postConcept_createsEntity() throws Exception {
        mockMvc
                .perform(
                        post("/concepts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                  \"title\":\"Test Concept\",
                                                  \"promptArtist\":\"Tester\",
                                                  \"aiTool\":\"Midjourney\",
                                                  \"imageUrl\":\"https://example.com/test.jpg\"
                                                }
                                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        mockMvc.perform(get("/concepts")).andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void rejectsBlankPayload() throws Exception {
        Concept invalid = new Concept(" ", " ", " ", " ");

        mockMvc
                .perform(
                        post("/concepts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", equalTo("Title is required")));
    }
}
