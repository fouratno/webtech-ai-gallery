package com.aiinteriorgallery.aiinteriorgallery.controller;

import com.aiinteriorgallery.aiinteriorgallery.service.ConceptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    private final ConceptService conceptService;

    public ThymeleafController(ConceptService conceptService) {
        this.conceptService = conceptService;
    }

    @GetMapping("/view")
    public String viewConcepts(Model model) {
        model.addAttribute("concepts", conceptService.getAllConcepts());
        return "concepts";
    }
}
