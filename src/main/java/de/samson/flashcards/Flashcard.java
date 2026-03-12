package de.samson.flashcards;

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

    private String fachbereich; // z.B. "Hardware" oder "Netzwerke"
    private String frage;
    @Column(length = 1000) // Falls die Antwort mal länger ist
    private String antwort;
}
