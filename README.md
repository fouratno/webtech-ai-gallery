🏠 AI Interior Gallery

AI Interior Gallery ist eine Webanwendung zur Verwaltung und Präsentation von KI-generierten Interior-Design-Konzepten.
Nutzer können bestehende Konzepte ansehen und über eine Weboberfläche neue Konzepte anlegen, die dauerhaft in einer Datenbank gespeichert werden.

Das Projekt wurde im Rahmen der Lehrveranstaltung Webtechnologien umgesetzt und erfüllt die Anforderungen der Meilensteine M3 und M4.

🔧 Tech-Stack
Frontend

Vue.js 3

TypeScript

Vite

HTML / CSS

Vitest (Tests)

Backend

Spring Boot

Java 21

REST API

JPA / Hibernate

Datenbank

PostgreSQL

DevOps / Qualität

GitHub Actions (CI/CD)

Automatisierte Frontend- und Backend-Tests

Deployment auf Render

Konfiguration über Environment Variables (keine Secrets im Repository)

🏗️ Architektur

Die Anwendung ist klar in Frontend und Backend getrennt:

Das Vue-Frontend stellt die Benutzeroberfläche bereit.

Das Spring-Boot-Backend stellt eine REST-Schnittstelle zur Verfügung.

Die Kommunikation erfolgt ausschließlich über HTTP (JSON).

Die Daten werden persistent in einer PostgreSQL-Datenbank gespeichert.

CORS ist gezielt für das Frontend konfiguriert.

REST-Endpunkte (Auszug):

GET /concepts – Abrufen aller Konzepte

POST /concepts – Anlegen eines neuen Konzepts

✨ Funktionsumfang / Use-Cases

Anzeige aller gespeicherten Interior-Konzepte

Anlegen neuer Konzepte über ein Formular im Frontend

Validierung der Eingaben im Frontend

Persistente Speicherung der Daten in PostgreSQL

REST-basierte Kommunikation zwischen Frontend und Backend

Fehler- und Ladezustände im Frontend

Automatisierte Tests (Frontend & Backend)

CI/CD-Pipeline mit GitHub Actions

Deployment von Frontend und Backend auf Render

🖥️ Deployment (öffentlich erreichbar)

Frontend:
https://webtech-ai-gallery-2.onrender.com/

Backend:
https://webtech-ai-gallery-backen.onrender.com/

Beide Services sind öffentlich erreichbar und kommunizieren produktiv miteinander.

🚀 Lokales Setup (Kurzfassung)
Voraussetzungen

Java 21+

Node.js 18+

PostgreSQL oder Docker

Git

Backend starten
./gradlew bootRun


Backend läuft unter:
👉 http://localhost:8080

Frontend starten
cd frontend
npm install
npm run dev


Frontend läuft unter:
👉 http://localhost:5173

🧪 Tests & Continuous Integration

Frontend:

Komponenten- und Logiktests mit Vitest

Backend:

Spring-Boot-Tests

CI/CD:

GitHub Actions führt Tests automatisch bei Push & Pull Requests aus

Nur erfolgreiche Builds werden gemergt

Die CI-Pipeline ist im Repository unter .github/workflows/ci.yml definiert.

🔐 Sicherheit & Konfiguration

Keine Zugangsdaten im Klartext im Repository

Datenbank-Zugangsdaten werden ausschließlich über Environment Variables gesetzt

Separate Konfiguration für lokale Entwicklung und Produktion

📚 Projektkontext

Dieses Projekt wurde im Rahmen der Webtechnologien-Übung umgesetzt und demonstriert:

moderne Webarchitektur

REST-basierte Kommunikation

persistente Datenhaltung

Deployment & CI/CD

saubere Trennung von Frontend und Backend

👤 Autor

Fourat Nouairia
HTW Berlin – Wirtschaftsinformatik
Modul: Webtechnologien
