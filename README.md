# 🎨 WEBTECH AI GALLERY

[![CI/CD Pipeline](https://github.com/fouratno/webtech-ai-gallery/actions/workflows/ci.yml/badge.svg)](https://github.com/fouratno/webtech-ai-gallery/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A modern full-stack web application for managing AI-generated interior design concepts. Built with **Spring Boot** backend, **Vue 3 + TypeScript** frontend, and **PostgreSQL** database.

## 🚀 Live Demo

- **Frontend**: [https://webtech-ai-fornt.onrender.com](https://webtech-ai-fornt.onrender.com)
- **Backend API**: [https://webtech-ai-gallery.onrender.com](https://webtech-ai-gallery.onrender.com)

## 🏗️ Architecture

```
Vue 3 Frontend (Render)
        |
        |  HTTPS (GET / POST)
        v
Spring Boot REST API (Render)
        |
        v
PostgreSQL Database (Render)
```

## ✨ Core Features

### 🎯 User Capabilities
- **View** AI interior design concepts (GET)
- **Create** new concepts via a form (POST)
- **Persist** concepts in a database
- **View** persisted concepts immediately in the UI

### 🛠️ Technical Features
- **REST API** with full CRUD operations
- **TypeScript** frontend with Vue 3 Composition API
- **JPA/Hibernate** database integration
- **Environment-based** configuration
- **Docker** containerization
- **CI/CD** with GitHub Actions
- **Responsive** design with modern UI

## 📋 Tech Stack

| Component | Technology |
|-----------|------------|
| **Backend** | Spring Boot 3.2, Java 21 |
| **Frontend** | Vue 3, TypeScript, Vite |
| **Database** | PostgreSQL |
| **Build** | Gradle, npm |
| **Container** | Docker |
| **Deployment** | Render |
| **CI/CD** | GitHub Actions |
| **Testing** | JUnit 5, Vitest |

## 🚀 Quick Start

### Prerequisites
- **Java 21+**
- **Node.js 20+**
- **Docker** (optional)

### Local Development (H2 Database)

1. **Clone the repository**
   ```bash
   git clone https://github.com/fouratno/webtech-ai-gallery.git
   cd webtech-ai-gallery
   ```

2. **Start Backend** (uses H2 in-memory database)
   ```bash
   cd backend
   chmod +x gradlew
   ./gradlew bootRun
   ```
   Backend runs at: `http://localhost:8080`

3. **Start Frontend**
   ```bash
   cd frontend
   npm install
   npm run dev
   ```
   Frontend runs at: `http://localhost:5173`

4. **Open Application**
   Navigate to `http://localhost:5173` and start creating concepts!

## 📁 Project Structure

```
webtech-ai-gallery/
├── backend/                          # Spring Boot Backend
│   ├── src/main/java/com/aiinteriorgallery/aiinteriorgallery/
│   │   ├── controller/
│   │   │   └── ConceptController.java # REST API endpoints
│   │   ├── model/
│   │   │   └── Concept.java          # JPA Entity
│   │   ├── repository/
│   │   │   └── ConceptRepository.java # Data access layer
│   │   ├── config/
│   │   │   └── CorsConfig.java       # CORS configuration
│   │   └── AiinteriorgalleryApplication.java
│   ├── src/main/resources/
│   │   ├── application.properties     # Local config (H2)
│   │   └── application-prod.properties # Production config (PostgreSQL)
│   ├── src/test/java/                # Backend tests
│   ├── build.gradle                  # Dependencies & build config
│   └── Dockerfile                    # Container configuration
├── frontend/                         # Vue 3 + TypeScript Frontend
│   ├── src/
│   │   ├── components/
│   │   │   └── ConceptList.vue       # Main component
│   │   ├── types/
│   │   │   └── Concept.ts            # TypeScript interfaces
│   │   └── App.vue                   # Root component
│   ├── .env.development              # Local API URL
│   ├── .env.production               # Production API URL
│   ├── package.json                  # Dependencies & scripts
│   ├── tsconfig.json                 # TypeScript config
│   └── vite.config.ts                # Build configuration
├── .github/workflows/
│   └── ci.yml                        # CI/CD pipeline
└── README.md
```

## 🌐 API Documentation

### Base URL
- **Local**: `http://localhost:8080`
- **Production**: `https://webtech-ai-gallery.onrender.com`

### Endpoints

| Method | Endpoint | Description | Example |
|--------|----------|-------------|---------|
| `GET` | `/concepts` | Get all concepts | `curl http://localhost:8080/concepts` |
| `POST` | `/concepts` | Create new concept | See example below |
| `GET` | `/concepts/{id}` | Get concept by ID | `curl http://localhost:8080/concepts/1` |
| `PUT` | `/concepts/{id}` | Update concept | `curl -X PUT http://localhost:8080/concepts/1 -d '{...}'` |
| `DELETE` | `/concepts/{id}` | Delete concept | `curl -X DELETE http://localhost:8080/concepts/1` |
| `GET` | `/concepts/search?title=modern` | Search concepts | `curl http://localhost:8080/concepts/search?title=modern` |

### Example: Create Concept
```bash
curl -X POST http://localhost:8080/concepts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Modern Living Room",
    "promptArtist": "John Doe",
    "aiTool": "DALL-E",
    "imageUrl": "https://example.com/image.jpg"
  }'
```

**Response:**
```json
{
  "id": 1,
  "title": "Modern Living Room",
  "promptArtist": "John Doe",
  "aiTool": "DALL-E",
  "imageUrl": "https://example.com/image.jpg"
}
```

## 🐳 Docker Deployment

### Backend
```bash
cd backend
docker build -t ai-interior-gallery-backend .
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DATABASE_URL=your_postgres_url \
  -e DATABASE_USERNAME=your_username \
  -e DATABASE_PASSWORD=your_password \
  ai-interior-gallery-backend
```

### Frontend
```bash
cd frontend
npm run build
# Serve dist/ folder with any static file server
```

## 🚀 Render Deployment

### Backend Service
1. **Create Web Service** on Render
2. **Connect GitHub repository**
3. **Set Root Directory**: `backend`
4. **Set Environment Variables**:
   ```
   SPRING_PROFILES_ACTIVE=prod
   DATABASE_URL=<your_postgres_connection_string>
   DATABASE_USERNAME=<your_db_username>
   DATABASE_PASSWORD=<your_db_password>
   ```
5. **Deploy** - Render will build using Dockerfile

### Frontend Service
1. **Create Web Service** on Render
2. **Set Root Directory**: `frontend`
3. **Set Build Command**: `npm install && npm run build`
4. **Set Start Command**: `npm run preview -- --host 0.0.0.0 --port 4173`
5. **Set Environment Variable**:
   ```
   VITE_API_BASE_URL=https://webtech-ai-gallery.onrender.com
   ```

## 🧪 Testing

### Backend Tests
```bash
cd backend
./gradlew test
```

### Frontend Tests
```bash
cd frontend
npm run test
```

### CI/CD Pipeline
- **Automated testing** on every push
- **Docker build** verification
- **Security scanning** with Trivy
- **Multi-environment** support

## 🔧 Configuration

### Environment Variables

#### Backend
| Variable | Description | Example |
|----------|-------------|---------|
| `DATABASE_URL` | PostgreSQL connection string | `jdbc:postgresql://localhost:5432/ai_gallery` |
| `DATABASE_USERNAME` | Database username | `gallery_user` |
| `DATABASE_PASSWORD` | Database password | `your_password` |
| `SPRING_PROFILES_ACTIVE` | Active profile | `prod` |

#### Frontend
| Variable | Description | Example |
|----------|-------------|---------|
| `VITE_API_BASE_URL` | Backend API URL | `https://webtech-ai-gallery.onrender.com` |

## 🎯 Demo Flow

1. **Open Frontend** → `https://webtech-ai-fornt.onrender.com`
2. **Create Concept** → Fill form and submit
3. **Refresh Page** → See persisted data
4. **Verify Database** → Data persists across sessions

## 🏆 Grade Boosters Implemented

- ✅ **Clean package structure** (proper separation of concerns)
- ✅ **TypeScript frontend** (type safety and better DX)
- ✅ **Modern CSS styling** (responsive design with gradients)
- ✅ **Error & loading states** (comprehensive UX)
- ✅ **Proper HTTP status codes** (201, 400, 404, 409, etc.)
- ✅ **Comprehensive testing** (backend + frontend)
- ✅ **CI/CD pipeline** (automated testing and deployment)
- ✅ **Docker containerization** (production-ready deployment)
- ✅ **Security scanning** (vulnerability detection)

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📞 Support

- 🐛 **Issues**: [GitHub Issues](https://github.com/fouratno/webtech-ai-gallery/issues)
- 📧 **Contact**: Create an issue for questions
- 📖 **Documentation**: This README and inline code comments

---

**Built with ❤️ for WEBTECH Course**