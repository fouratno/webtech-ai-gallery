package com.aiinteriorgallery.aiinteriorgallery.repository;

import com.aiinteriorgallery.aiinteriorgallery.model.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConceptRepository extends JpaRepository<Concept, Long> {
}
