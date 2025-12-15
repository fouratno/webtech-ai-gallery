# AI Interior Gallery

**Thema:** Online-Galerie für AI-generierte Interior-Design-Konzepte  
**Kurs:** Webtechnologien – HTW Berlin  
**Dozent:** Prof. Dr. Arif Wider  

---

## 🧠 Projektbeschreibung
Die **AI Interior Gallery** ist eine Web-Anwendung, die AI-generierte Interior-Design-Konzepte darstellt und persistent in **PostgreSQL** speichert. 
Jedes Konzept enthält Informationen über den *Prompt Artist*, das verwendete *AI-Tool* und ein *Bild*.

---

## ⚙️ Tech-Stack
- **JDK 21** (toolchain and container images)
- **Spring Boot 3.5.6**
- **Gradle 9.0-Milestone-3**
- **Thymeleaf Template Engine**
- **Spring Web & DevTools**
- **PostgreSQL** als persistente Datenbank

---

## 🚀 Funktionen (M4)
| Route | Beschreibung |
|-------|---------------|
| `GET /concepts` | Liefert alle Konzepte aus der Postgres-Datenbank als JSON |
| `POST /concepts` | Speichert ein neues Konzept (Body: `title`, `promptArtist`, `aiTool`, `imageUrl`) |
| `GET /view` | Rendert alle Konzepte serverseitig mit **Thymeleaf** (liest ebenfalls aus der DB) |

**Persistenz:** Konzepte werden in PostgreSQL gespeichert (JPA/Hibernate, `ddl-auto=update`).

▶️ Projekt lokal starten

1. Postgres bereitstellen und Datenbank/Benutzer anlegen.
2. Umgebungsvariablen setzen (z. B. in deiner Shell):
   ```bash
   export DB_URL=jdbc:postgresql://localhost:5432/ai_gallery
   export DB_USERNAME=postgres
   export DB_PASSWORD=deinPasswort
   export PORT=8080
   ```
3. Backend starten:
   ```bash
   ./gradlew bootRun
   ```
4. Browser öffnen:
   - http://localhost:8080/concepts → JSON-API
   - http://localhost:8080/view → SSR-Galerie

---

## ☁️ Deployment auf Render

### Backend (Docker Service)
- **Dockerfile** nutzt JDK 21 für Build & Runtime und startet automatisch `java -jar app.jar`.
- Server-Port: wird über `PORT` gesetzt (Render Vorgabe, Fallback 8080).
- **Umgebungsvariablen:**
  - `DB_URL` (z. B. `jdbc:postgresql://<host>:5432/<db>`)
  - `DB_USERNAME`
  - `DB_PASSWORD`
  - `PORT` (optional)
  - `APP_CORS_ALLOWED_ORIGINS` (optional, Komma-separiert; Standard: `http://localhost:5173,https://*.onrender.com`)
- **CORS-Origins anpassen:** In `src/main/java/com/aiinteriorgallery/aiinteriorgallery/config/CorsConfig.java` die Render-Frontend-Domain pflegen.

### Frontend (Static Site)
- **Root Directory:** `frontend`
- **Build Command:** `npm install && npm run build`
- **Publish Directory:** `dist`
- **Environment Variable:** `VITE_API_BASE_URL` (z. B. `https://<dein-backend>.onrender.com`); für Production liegt eine Vorlage unter `frontend/.env.production`.
- Die Vue-App lädt Konzepte per GET und erlaubt das Anlegen neuer Konzepte per POST (Formular in `frontend/src/components/ConceptList.vue`).

📂 Projektstruktur
src/
 └── main/
     ├── java/com/aiinteriorgallery/
     │   ├── AiinteriorgalleryApplication.java
     │   ├── controller/
     │   │   ├── ConceptController.java
     │   │   └── ThymeleafController.java
     │   ├── repository/
     │   │   └── ConceptRepository.java
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
