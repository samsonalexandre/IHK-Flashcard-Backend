package de.samson.flashcards.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fachbereich;
    private String frage;
    @Column(length = 1000)
    private String antwort;
    private String imagePath; // Hier speichern wir z.B. "/images/_03_it_systeme/1"
}