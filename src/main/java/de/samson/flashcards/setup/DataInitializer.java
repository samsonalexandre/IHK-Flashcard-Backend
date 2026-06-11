package de.samson.flashcards.setup;

import de.samson.flashcards.model.Flashcard;
import de.samson.flashcards.repository.FlashcardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final FlashcardRepository repository;

    public DataInitializer(FlashcardRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        repository.deleteAll();

        // Wir speichern nur die Basis des Namens (ohne "_1.jpg")
        repository.save(new Flashcard(
                null,
                "Hardware",
                "Bearbeitung von Arbeitsaufgaben",
                "Hier steht deine Antwort-Erklärung",
                "/images/_03_it_systeme/53" // WICHTIG: Keine Endung hier!
        ));

        System.out.println("Zack! Karte geladen.");
    }
}