package de.samson.flashcards;

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
        repository.save(new Flashcard(null, "Hardware", "Was bedeutet die Abkürzung 'ALU'?", "Arithmetic Logic Unit (Rechenwerk)"));
        repository.save(new Flashcard(null, "Netzwerk", "Auf welcher Schicht des OSI-Modells arbeitet ein Router?", "Schicht 3 (Network Layer)"));
        repository.save(new Flashcard(null, "WISO", "Nenne ein Beispiel für ein einseitiges Rechtsgeschäft.", "Kündigung oder Testament"));

        System.out.println("Zack! 3 Test-Karten wurden in der Datenbank gespeichert.");
    }
}