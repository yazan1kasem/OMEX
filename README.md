# OMEX – Medizinisches Quizspiel

OMEX ist eine Java-basierte Desktop-Anwendung, die medizinisches Wissen auf interaktive Weise vermittelt. Nutzer können aus verschiedenen medizinischen Kategorien Multiple-Choice-Fragen beantworten und erhalten direkt Feedback zu ihren Antworten. Die Anwendung eignet sich besonders für Schüler:innen und Studierende in medizinischen Ausbildungsrichtungen, zur Prüfungsvorbereitung oder zur Festigung des Grundlagenwissens.

---

## Inhaltliche Übersicht

OMEX bietet folgende Kernfunktionen:

- Auswahl medizinischer Fachkategorien und Unterkategorien
- Quiz mit Multiple-Choice-Fragen
- Sofortiges Feedback (richtig/falsch)
- Ergebnisansicht mit richtiger und falscher Antworten
- SQLite-Datenbank zur Speicherung der Fragen
- Benutzerfreundliche Oberfläche mit Login- und Registrierungssystem

---

## Benutzeroberfläche (Screenshots)

### Login

![Login](https://github.com/user-attachments/assets/588f11dd-f8e6-4ee5-9ca8-dc8dfbd8164b)  
*Benutzer können sich mit bestehenden Zugangsdaten anmelden.*

---

### Registrierung

![Signup](https://github.com/user-attachments/assets/8c633ac8-ad33-4721-9dac-693db60aadd5)  
*Neuen Benutzeraccount erstellen mit individuellen Zugangsdaten.*

---

### Auswahl der Quiz-Kategorien

![image](https://github.com/user-attachments/assets/e353fc0d-c4a5-483a-9ce8-0f51ffc74c1e)
*Hauptkategorien für medizinische Themen wie Anatomie, Pathologie oder Pharmakologie.*

---

### Auswahl von Unterkategorien

![image](https://github.com/user-attachments/assets/1dbfb038-a017-44d4-87ab-7741f2c647c2)
*Feinere Auswahl innerhalb der Kategorien zur gezielten Themenbearbeitung.*

---

### Richtig beantwortete Fragen

![Richtig](https://github.com/user-attachments/assets/bce92e9a-ce3a-4c53-a581-c0120156ccdc)  
*Darstellung aller korrekt beantworteten Fragen nach einem Durchlauf.*

---

### Falsch beantwortete Fragen

![image](https://github.com/user-attachments/assets/a434fd36-276b-4977-86be-59c53e71efab)
*Anzeige der falsch gewählten Antworten zur gezielten Nachbearbeitung.*

Auf hellblauem Hintergrund steht die Erklärung zur richtigen Antwort, diese erscheint nur bei der falsch Beantwortung einer Frage
---

### Metabereich

![Meta](https://github.com/user-attachments/assets/55dae130-cd47-4be1-ac4a-17b1a9b6abe0)  
*Zusätzliche Informationen, Einstellungen oder Anwendungsnavigation.*

---

## Technologie-Stack

| Komponente         | Technologie        |
|--------------------|--------------------|
| Programmiersprache | Java               |
| Build Tool         | Gradle             |
| Datenbank          | SQLite (`OMEX.db`) |
| GUI                | Java Swing / AWT   |

---

## Projektstruktur

```
OMEX/
├── src/                     → Java-Quellcode der Anwendung
├── OMEX.db / OMEX.sqlite    → SQLite-Datenbank mit Fragen und Antworten
├── build.gradle             → Gradle Build-Konfiguration
├── settings.gradle          → Projektdefinition
├── gradlew / gradlew.bat    → Gradle Wrapper
```

---

## Ausführung

### Voraussetzungen

- Java 17 oder höher
- Gradle (lokal oder via Wrapper)

### Lokaler Start

```bash
git clone https://github.com/yazan1kasem/OMEX.git
cd OMEX
./gradlew run
```

---

## Weiterentwicklungsmöglichkeiten

- Integration eines Punktesystems oder Ranglisten
- Erweiterung um neue Fragetypen (z. B. Zuordnungen)
- Online-Synchronisation der Ergebnisse
- Webbasierte Version (z. B. mit JavaFX oder Spring Boot)

---

## Lizenz

Das Projekt wurde zu Ausbildungszwecken entwickelt. Lizenzinformationen sind nicht explizit angegeben.
