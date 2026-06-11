package de.samson.flashcards.controller;

import de.samson.flashcards.model.Flashcard;
import de.samson.flashcards.repository.FlashcardRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {

    private final FlashcardRepository repository;

    // Spring Boot übergibt (injiziert) das Repository hier automatisch
    public FlashcardController(FlashcardRepository repository) {
        this.repository = repository;
    }

    // Wenn ein GET-Request an /api/flashcards kommt, feuert diese Methode
    @GetMapping
    public List<Flashcard> getAllCards() {
        return repository.findAll(); // Holt alle Karten aus der Datenbank und macht daraus JSON
    }
}
