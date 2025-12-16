package com.aiinteriorgallery.aiinteriorgallery.service;

import com.aiinteriorgallery.aiinteriorgallery.model.Concept;
import com.aiinteriorgallery.aiinteriorgallery.repository.ConceptRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ConceptService {

    private final ConceptRepository conceptRepository;

    public ConceptService(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    public List<Concept> getAllConcepts() {
        return conceptRepository.findAll();
    }

    public Concept createConcept(Concept concept) {
        trimFields(concept);
        return conceptRepository.save(concept);
    }

    private void trimFields(Concept concept) {
        concept.setTitle(StringUtils.trimWhitespace(concept.getTitle()));
        concept.setPromptArtist(StringUtils.trimWhitespace(concept.getPromptArtist()));
        concept.setAiTool(StringUtils.trimWhitespace(concept.getAiTool()));
        concept.setImageUrl(StringUtils.trimWhitespace(concept.getImageUrl()));
    }
}
