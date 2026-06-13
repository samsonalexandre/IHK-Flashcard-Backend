# FIAE Lern-Trainer – Samson Elektronik

Ein interaktiver, webbasierter Karteikarten-Trainer zur gezielten Vorbereitung auf die IHK-Abschlussprüfung zum **Fachinformatiker für Anwendungsentwicklung (FIAE)**. Die Anwendung basiert auf einem robusten Spring Boot Backend und einem modernen, responsiven HTML5/Java-Script Frontend.

## Features

* **Automatische Daten-Initialisierung:** Beim Start wird die integrierte H2-Datenbank automatisch bereinigt und über eine `karten.json` mit 270 Fachfragen befüllt.
* **Intelligente Bildpfad-Zuordnung:** Automatischer und fehlertoleranter Abgleich der Bildmedien (Vorder- und Rückseite) mit der lokalen Festplatten-Struktur.
* **Präzise Filtermöglichkeiten:** Filterung der Karteikarten nach spezifischen IHK-Lernbereichen und drei Schwierigkeitsgraden (Leicht, Mittel, Schwer).
* **Automatisches Lesezeichen (Persistent LocalStorage):** Die Anwendung merkt sich im Browser exakt den Lernbereich, die Schwierigkeit und die Kartennummer. Nach einem Neustart oder Tab-Schließen wird genau dort fortgesetzt, wo man aufgehört hat.
* **Fortschrittsanzeige:** Live-Anzeige der aktuellen Position innerhalb des gewählten Lernbereichs (z. B. *Karte 14 von 38*).

---

## Technische Stack

* **Backend:** Java, Spring Boot 3.x, Spring Data JPA, Jakarta Persistence
* **Datenbank:** H2 In-Memory-Datenbank
* **Frontend:** HTML5, CSS3 (Modern Off-White Design), Vanilla JavaScript (ES6+)
* **Daten-Parsing:** Jackson (ObjectMapper)
* **Branding:** Samson Elektronik

---

## Projektstruktur (Wichtige Pfade)

```text
├── src/main/java/de/samson/flashcards/
│   ├── FlashcardApplication.java     # Hauptklasse
│   ├── controller/
│   │   ├── FlashcardController.java  # REST-API für Filter & Abfragen
│   │   └── WebController.java        # Weiterleitung auf index.html
│   ├── model/
│   │   └── Flashcard.java            # JPA-Entity für die Datenbank
│   ├── repository/
│   │   └── FlashcardRepository.java  # Datenbank-Schnittstelle
│   └── setup/
│       └── DataInitializer.java      # JSON-Importer & Pfad-Korrektur
│
└── src/main/resources/
    ├── karten.json                   # Rohdaten der 270 Karteikarten
    └── static/
        ├── index.html                # Frontend & UI-Logik
        └── images/                   # Bild-Ordner (_01_arbeitsaufgaben bis _07_auftragsabschluss)
