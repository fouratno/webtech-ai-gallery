package com.aiinteriorgallery.aiinteriorgallery.repository;

import com.aiinteriorgallery.aiinteriorgallery.model.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConceptRepository extends JpaRepository<Concept, Long> {
    
    // Find concepts by title (case-insensitive)
    List<Concept> findByTitleContainingIgnoreCase(String title);
    
    // Find concepts by AI tool
    List<Concept> findByAiToolIgnoreCase(String aiTool);
    
    // Custom query to find concepts ordered by creation (id)
    @Query("SELECT c FROM Concept c ORDER BY c.id DESC")
    List<Concept> findAllOrderByIdDesc();
    
    // Check if concept exists by title
    boolean existsByTitleIgnoreCase(String title);
}