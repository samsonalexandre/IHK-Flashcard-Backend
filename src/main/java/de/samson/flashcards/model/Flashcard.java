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
    private Integer schwierigkeit; // 1 = leicht, 2 = mittel, 3 = schwer
    private String frage;
    @Column(length = 1000)
    private String antwort;
    private String imagePath; // Hier speichere ich z.B. "/images/_03_it_systeme/1"
}