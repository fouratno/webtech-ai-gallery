package com.aiinteriorgallery.aiinteriorgallery.config;

import com.aiinteriorgallery.aiinteriorgallery.model.Concept;
import com.aiinteriorgallery.aiinteriorgallery.repository.ConceptRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class ConceptDataInitializer implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ConceptDataInitializer.class);

    private final ConceptRepository conceptRepository;

    public ConceptDataInitializer(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    @Override
    public void run(String... args) {
        if (conceptRepository.count() > 0) {
            return;
        }

        List<Concept> concepts =
                List.of(
                        new Concept(
                                "Scandinavian Calm",
                                "Ingrid Studio",
                                "Midjourney",
                                "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85"),
                        new Concept(
                                "Warm Loft",
                                "Atelier North",
                                "Stable Diffusion",
                                "https://images.unsplash.com/photo-1484100356142-db6ab6244067"),
                        new Concept(
                                "Minimal Workspace",
                                "Kairo Creative",
                                "DALL·E",
                                "https://images.unsplash.com/photo-1505691938895-1758d7feb511"));

        conceptRepository.saveAll(concepts);
        LOG.info("Seeded {} concepts into the database", concepts.size());
    }
}
