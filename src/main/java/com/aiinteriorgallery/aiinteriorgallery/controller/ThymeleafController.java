package com.aiinteriorgallery.aiinteriorgallery.controller;

import com.aiinteriorgallery.aiinteriorgallery.repository.ConceptRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    private final ConceptRepository conceptRepository;

    public ThymeleafController(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    @GetMapping("/view")
    public String viewConcepts(Model model) {
        model.addAttribute("concepts", conceptRepository.findAll());
        return "concepts"; // corresponds to templates/concepts.html
    }
}
