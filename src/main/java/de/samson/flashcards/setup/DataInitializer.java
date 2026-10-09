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
        // lösche alte Einträge, um bei jedem Start sauber neu einzulesen
        repository.deleteAll();

        try {
            // 1. Die JSON-Datei aus dem resources-Ordner laden
            ClassPathResource resource = new ClassPathResource("karten.json");
            InputStream inputStream = resource.getInputStream();

            // 2. Das JSON in Hilfsklasse (FlashcardImportDTO) übersetzen
            List<FlashcardImportDTO> importKarten = objectMapper.readValue(
                    inputStream, new TypeReference<List<FlashcardImportDTO>>() {}
            );

            // 3. Durch alle Karten gehen und in Spring-Karten umwandeln
            for (FlashcardImportDTO dto : importKarten) {
                Flashcard karte = new Flashcard();

                karte.setFachbereich(dto.getThema());
                karte.setSchwierigkeit(dto.getSchwierigkeit());
                karte.setFrage("Karten-ID: " + dto.getId()); // nutze die ID als Fragetext
                karte.setAntwort("Lösung auf der Rückseite");

                // Pfad-Umbau passend zur Festplatten-Ordnerstruktur:
                if (dto.getPfad_v() != null) {
                    // Extrahiere die Dateinummer (z.B. "27" aus "assets/01_arbeitsaufgaben/27_1.jpg")
                    String[] parts = dto.getPfad_v().split("/");
                    String dateiName = parts[parts.length - 1]; // "27_1.jpg"
                    String nummer = dateiName.replace("_1.jpg", ""); // "27"

                    // Standard-Ordner falls nichts matcht
                    String ordner = "_01_arbeitsaufgaben";
                    String thema = dto.getThema();

                    // Ordner-Zuweisung basierend auf dem echten Fachbereich
                    if ("Informieren und Beraten von Kunden und Kundinnen".equals(thema)) {
                        ordner = "_02_kundenberatung";
                    } else if ("Beurteilen marktgängiger IT-Systeme und Lösungen".equals(thema)) {
                        ordner = "_03_it_systeme";
                    } else if ("Entwickeln, Erstellen und Betreuen von IT-Lösungen".equals(thema)) {
                        ordner = "_04_it_loesungen";
                    } else if ("Qualitätssichernde Maßnahmen".equals(thema)) {
                        ordner = "_05_qualitaetssicherung";
                    } else if ("IT-Sicherheit und Datenschutz, Ergonomie".equals(thema)) {
                        ordner = "_06_it_sicherheit";
                    } else if ("Auftragsabschluss und Leistungserbringung".equals(thema)) {
                        ordner = "_07_auftragsabschluss";
                    }

                    // Der absolute Pfad für das Frontend
                    String basisPfad = "/images/" + ordner + "/" + nummer;
                    karte.setImagePath(basisPfad);
                }

                // In die H2 Datenbank speichern
                repository.save(karte);
            }

            System.out.println("Erfolgreich " + importKarten.size() + " Karten mit korrigierten Pfaden importiert!");

        } catch (Exception e) {
            System.err.println("Fehler beim JSON-Import: " + e.getMessage());
        }
    }

    // --- Hilfsklasse (DTO) passgenau für Python-JSON ---
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