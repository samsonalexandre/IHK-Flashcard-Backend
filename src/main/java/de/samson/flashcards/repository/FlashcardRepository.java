package de.samson.flashcards.repository;

import de.samson.flashcards.model.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    // Spring Boot Magic: Schreibt den SQL-Befehl für uns!
    List<Flashcard> findByFachbereich(String fachbereich);
}