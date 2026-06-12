package de.samson.flashcards.setup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.samson.flashcards.model.Flashcard;
import de.samson.flashcards.repository.FlashcardRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final FlashcardRepository repository;
    private final ObjectMapper objectMapper; // Spring Boot stellt diesen JSON-Leser automatisch bereit!

    public DataInitializer(FlashcardRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) {
        // Wir löschen alte Einträge, um bei jedem Start sauber neu einzulesen
        repository.deleteAll();

        try {
            // 1. Die JSON-Datei aus dem resources-Ordner laden
            ClassPathResource resource = new ClassPathResource("karten.json");
            InputStream inputStream = resource.getInputStream();

            // 2. Das JSON in unsere Hilfsklasse (FlashcardImportDTO) übersetzen
            List<FlashcardImportDTO> importKarten = objectMapper.readValue(
                    inputStream, new TypeReference<List<FlashcardImportDTO>>() {}
            );

            // 3. Durch alle Python-Karten gehen und in Spring-Karten umwandeln
            for (FlashcardImportDTO dto : importKarten) {
                Flashcard karte = new Flashcard();

                karte.setFachbereich(dto.getThema());
                karte.setSchwierigkeit(dto.getSchwierigkeit());
                karte.setFrage("Karten-ID: " + dto.getId()); // Wir nutzen die ID als Fragetext
                karte.setAntwort("Lösung auf der Rückseite");

                // Pfad-Umbau: Wir ändern "assets/" zu "/images/" und schneiden "_1.jpg" ab!
                // Neuer Code (mit dem fehlenden Unterstrich!):
                if (dto.getPfad_v() != null) {
                    String basisPfad = dto.getPfad_v()
                            .replace("assets/", "/images/_") // <-- Hier ist der magische Unterstrich!
                            .replace("_1.jpg", "");
                    karte.setImagePath(basisPfad);
                }

                // In die H2 Datenbank speichern
                repository.save(karte);
            }

        } catch (Exception e) {
            System.err.println("Fehler beim JSON-Import: " + e.getMessage());
        }
    }

    // --- Hilfsklasse (DTO) passgenau für dein Python-JSON ---
    @Getter
    @Setter
    public static class FlashcardImportDTO {
        private Integer id;
        private String thema;
        private Integer schwierigkeit;
        private String pfad_v;
        private String pfad_r;

        public void setId(Integer id) { this.id = id; }

        public void setThema(String thema) { this.thema = thema; }

        public void setSchwierigkeit(Integer schwierigkeit) { this.schwierigkeit = schwierigkeit; }

        public void setPfad_v(String pfad_v) { this.pfad_v = pfad_v; }

        public void setPfad_r(String pfad_r) { this.pfad_r = pfad_r; }
    }
}