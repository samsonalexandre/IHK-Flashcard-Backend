package de.samson.flashcards.repository;

import de.samson.flashcards.model.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    // "ContainingIgnoreCase" sucht extrem fehlertolerant!
    List<Flashcard> findByFachbereichContainingIgnoreCase(String fachbereich);

    List<Flashcard> findBySchwierigkeit(Integer schwierigkeit);

    // Kombinierte, tolerante Suche
    List<Flashcard> findByFachbereichContainingIgnoreCaseAndSchwierigkeit(String fachbereich, Integer schwierigkeit);
}