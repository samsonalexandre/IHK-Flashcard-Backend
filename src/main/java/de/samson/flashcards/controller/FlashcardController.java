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
            @RequestParam(required = false) String fachbereich,
            @RequestParam(required = false) Integer schwierigkeit) {

        // --- ENTFERNE ODER KOMMENTIERE DIESE ZEILE AUS ---
        // System.out.println("Filter erhalten: Fachbereich='" + fachbereich + "'...");

        if (fachbereich != null) {
            fachbereich = fachbereich.trim();
        }

        if (fachbereich != null && !fachbereich.isEmpty() && schwierigkeit != null) {
            return repository.findByFachbereichContainingIgnoreCaseAndSchwierigkeit(fachbereich, schwierigkeit);
        }
        else if (fachbereich != null && !fachbereich.isEmpty()) {
            // --- HIER KEINE PRINT-AUSGABEN MEHR ---
            return repository.findByFachbereichContainingIgnoreCase(fachbereich);
        }
        else if (schwierigkeit != null) {
            return repository.findBySchwierigkeit(schwierigkeit);
        }

        return repository.findAll();
    }
}