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

        // Karte 1: Bereich IT-Systeme (Bild 53_1.jpg und 53_2.jpg)
        repository.save(new Flashcard(
                null,
                "Beurteilen marktgängiger IT-Systeme und Lösungen",
                "Virtualisierung von Hostsystemen",
                "Hypervisor Typ 1 läuft direkt auf der Hardware. Typ 2 läuft auf einem Betriebssystem.",
                "/images/_03_it_systeme/53"
        ));

        // Karte 2: Bereich Qualitätssicherung (Bild 191_1.jpg und 191_2.jpg)
        repository.save(new Flashcard(
                null,
                "Qualitätssichernde Maßnahmen",
                "Qualitätsmanagement im Projekt",
                "Erklärung und Ablauf laut Karteikarte.",
                "/images/_05_qualitaetssicherung/191"
        ));

        System.out.println("Perfekt! Die offiziellen IHK-Lernkarten wurden in der H2-Datenbank hinterlegt.");
    }
}