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
    // Neu: Wir akzeptieren einen optionalen Suchparameter
    @GetMapping
    public List<Flashcard> getCards(@RequestParam(required = false) String fachbereich) {
        if (fachbereich != null && !fachbereich.isEmpty()) {
            // Gefilterte Liste zurückgeben
            return repository.findByFachbereich(fachbereich);
        }
        // Alle zurückgeben, wenn nichts ausgewählt ist
        return repository.findAll();
    }
}
