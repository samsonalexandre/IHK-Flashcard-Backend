package de.samson.flashcards.controller;

import de.samson.flashcards.model.Flashcard;
import de.samson.flashcards.repository.FlashcardRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {

    private final FlashcardRepository repository;

    public FlashcardController(FlashcardRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Flashcard> getCards(
            @RequestParam(value = "fachbereich", required = false) String fachbereich,
            @RequestParam(value = "schwierigkeit", required = false) Integer schwierigkeit) {

        if (fachbereich != null) {
            fachbereich = fachbereich.trim();
        }

        if (fachbereich != null && !fachbereich.isEmpty() && schwierigkeit != null) {
            return repository.findByFachbereichContainingIgnoreCaseAndSchwierigkeit(fachbereich, schwierigkeit);
        }
        else if (fachbereich != null && !fachbereich.isEmpty()) {
            return repository.findByFachbereichContainingIgnoreCase(fachbereich);
        }
        else if (schwierigkeit != null) {
            return repository.findBySchwierigkeit(schwierigkeit);
        }

        return repository.findAll();
    }
}
