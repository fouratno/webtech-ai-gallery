package com.aiinteriorgallery.aiinteriorgallery.controller;
import com.aiinteriorgallery.aiinteriorgallery.model.Concept;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class ThymeleafController {

    @GetMapping("/view")
    public String viewConcepts(Model model) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("data/concepts.json").getInputStream();
            List<Concept> concepts = mapper.readValue(inputStream, new TypeReference<List<Concept>>() {});
            model.addAttribute("concepts", concepts);
            return "concepts"; // corresponds to templates/concepts.html
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
