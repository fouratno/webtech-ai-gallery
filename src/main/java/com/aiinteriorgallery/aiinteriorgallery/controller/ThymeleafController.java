package com.aiinteriorgallery.aiinteriorgallery.controller;
import com.aiinteriorgallery.aiinteriorgallery.model.Concept;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class ThymeleafController {

    @GetMapping("/view")
    public String showConcepts(Model model) {
        List<Concept> concepts = List.of(
                new Concept("Tropical Kitchen", "Furat Nouairia", "Midjourney v6"),
                new Concept("Minimalist Living Room", "AI Studio", "SDXL"),
                new Concept("Rustic Bedroom", "Luna Design", "DALL·E 3")
        );
        model.addAttribute("concepts", concepts);
        return "concepts";  // corresponds to templates/concepts.html
    }
}
