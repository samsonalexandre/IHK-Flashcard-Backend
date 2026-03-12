package de.samson.flashcards;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    // Hier erbt Spring Boot automatisch alle Funktionen wie save(), findAll(), delete()
}