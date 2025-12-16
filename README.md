🖼️ AI Interior Gallery

Eine Web-Applikation zur Verwaltung und Präsentation von KI-generierten Interior-Design-Konzepten.
Nutzer können neue Design-Konzepte über eine Web-Oberfläche erstellen, die persistent in einer Datenbank gespeichert und anschließend in einer Galerie angezeigt werden.

🔗 Live-Deployment (Render)

Frontend (Vue.js):
👉 https://webtech-ai-gallery-2.onrender.com/

Backend (Spring Boot REST API):
👉 https://webtech-ai-gallery-backen.onrender.com/

GET-Endpoint (Beispiel):
👉 https://webtech-ai-gallery-backen.onrender.com/concepts

🎯 Projektidee

Die Anwendung setzt die ursprüngliche Projektidee um, eine kuratierte Galerie von KI-gestützten Interior-Design-Prompts bereitzustellen.
Jedes Konzept besteht aus Metadaten (Titel, Prompt-Artist, KI-Tool, Bild-URL) und kann über das Frontend erstellt und angezeigt werden.

🧱 Architektur & Tech-Stack
Frontend

Vue.js 3

TypeScript

Vite

Fetch API

Deployment: Render

Backend

Spring Boot

REST API

JPA / Hibernate

PostgreSQL (Produktion)

H2 (lokale Entwicklung)

Deployment: Render (Docker)

Datenbank

PostgreSQL (Render Managed Database)

Persistente Speicherung der Entitäten

🔁 Kommunikation Frontend ↔ Backend
Methode	Endpoint	Beschreibung
GET	/concepts	Alle Konzepte abrufen
POST	/concepts	Neues Konzept erstellen

Das Frontend kommuniziert ausschließlich über HTTP mit der Spring-Boot-REST-API (JSON).

🗄️ Datenmodell (Concept)
{
  "id": 1,
  "title": "Minimal Loft",
  "promptArtist": "Studio A",
  "aiTool": "Midjourney",
  "imageUrl": "https://example.com/image.jpg"
}

🚀 Nutzung der Anwendung

Öffne das Frontend

Fülle das Formular „Create Concept“ aus

Klicke auf Create Concept

Das Konzept wird:

per POST an das Backend gesendet

in PostgreSQL gespeichert

automatisch in der Galerie angezeigt

Nach einem Reload bleiben die Daten erhalten (persistente DB)

🧪 Tests & Continuous Delivery
Frontend-Tests

Framework: Vitest

Getestet werden u.a.:

Rendering der App

Laden von Konzepten (GET)

Erstellen neuer Konzepte (POST)

Tests werden beim Build automatisch ausgeführt

Backend-Tests

Lokale Tests vorhanden

In der Render-CI schlägt das automatische Nachladen von Maven-Dependencies gelegentlich fehl (403),
dies ist dokumentiert und beeinflusst die Funktionalität der produktiven Anwendung nicht.

🔐 Sicherheit & Konfiguration

Keine Credentials im GitHub-Repository

Alle sensiblen Daten (DB-Zugang, Ports) werden über Environment Variables konfiguriert

Separate Konfigurationen für:

lokale Entwicklung (application.properties)

Produktion (application-prod.properties)

📁 Projektstruktur (Auszug)
webtech-ai-gallery/
│
├── frontend/                # Vue.js Frontend
│   ├── src/
│   └── tests/
│
├── src/main/java/            # Spring Boot Backend
│   ├── controller/
│   ├── model/
│   ├── repository/
│   └── config/
│
├── Dockerfile
├── build.gradle
└── README.md

📌 Erfüllte Projektanforderungen

✅ Web-App auf Render deployed (öffentlich erreichbar)

✅ Frontend & Backend getrennt

✅ REST-API mit Spring Boot

✅ Persistente Speicherung in PostgreSQL

✅ Erstellung von Entitäten über Web-Oberfläche

✅ Automatisierte Frontend-Tests

✅ Keine Klartext-Credentials im Repository

✅ Umsetzung der ursprünglich präsentierten Projektidee

👨‍💻 Autor

Fourat Nouairia
HTW Berlin – Webtechnologien
