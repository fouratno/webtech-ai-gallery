# AI Interior Gallery

**Thema:** Online-Galerie für AI-generierte Interior-Design-Konzepte  
**Kurs:** Webtechnologien – HTW Berlin  
**Dozent:** Prof. Dr. Arif Wider  

---

## 🧠 Projektbeschreibung
Die **AI Interior Gallery** ist eine Web-Anwendung, die AI-generierte Interior-Design-Konzepte darstellt.  
Jedes Konzept enthält Informationen über den *Prompt Artist* und das verwendete *AI-Tool*.  

Milestone 1 umfasst die Implementierung eines **Spring-Boot-Backends**,  
das über eine REST-Schnittstelle JSON-Daten bereitstellt und zusätzlich  
eine Server-Side-Rendering-Seite mit **Thymeleaf** anzeigt.

---

## ⚙️ Tech-Stack
- **JDK 25**
- **Spring Boot 3.5.6**
- **Gradle 9.0-Milestone-3**
- **Thymeleaf Template Engine**
- **Spring Web & DevTools**
- **H2 (In-Memory-DB für Entwicklung)**

---

## 🚀 Funktionen (Milestone 1)
| Route | Beschreibung |
|-------|---------------|
| `GET /concepts` | Gibt eine JSON-Liste von Interior-Konzepte-Objekten zurück *(REST API)* |
| `GET /view` | Rendert dieselben Konzepte serverseitig mit **Thymeleaf** *(SSR Demo)* |

Beispiel-Antwort (`/concepts`):
```json
[
  {"title": "Tropical Kitchen", "promptArtist": "Furat Nouairia", "aiTool": "Midjourney v6"},
  {"title": "Rustic Bedroom", "promptArtist": "Luna Design", "aiTool": "DALL·E 3"}
]

▶️ Projekt lokal starten

Repository klonen

git clone https://github.com/fouratnouairia/webtech-ai-gallery.git
cd webtech-ai-gallery


Anwendung starten

./gradlew bootRun


Browser öffnen:

http://localhost:8080/concepts
 → JSON-API

http://localhost:8080/view
 → SSR-Galerie

📂 Projektstruktur
src/
 └── main/
     ├── java/com/aiinteriorgallery/
     │   ├── AiinteriorgalleryApplication.java
     │   ├── controller/
     │   │   ├── ConceptController.java
     │   │   └── ThymeleafController.java
     │   └── model/
     │       └── Concept.java
     └── resources/
         ├── templates/
         │   └── concepts.html
         └── static/css/
             └── styles.css

✉️ Autor

Fourat Nouairia
HTW Berlin – Wirtschaftsinformatik
