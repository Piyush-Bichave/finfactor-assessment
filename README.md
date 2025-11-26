# 🌌 NASA APOD Explorer - FinFactor Assessment

A clean and modern full-stack application to view NASA's **Astronomy Picture of the Day (APOD)**.  
This project includes a **Spring Boot backend** that integrates with the NASA APOD API and a **Bootstrap + JavaScript frontend** with a beautiful and responsive UI.

---

## 🚀 Features

### 🔹 Frontend
- Dashboard showing **Today’s APOD**
- **Date picker** for selecting past APOD images
- **Recent APOD Gallery**
- **Popup detail view** with title, description, and copyright
- Responsive and clean UI built with Bootstrap

### 🔹 Backend
- Java 17 + Spring Boot
- Integrates with **NASA APOD Public API**
- REST APIs using `RestTemplate`
- **Caffeine Cache** for performance
- Global response wrapper
- CORS enabled

---


---

## 🛠 Technologies Used

| Component | Tech |
|-----------|------|
| Backend | Java 17, Spring Boot, RestTemplate, Caffeine Cache |
| Frontend | HTML, CSS, Bootstrap 5, Vanilla JavaScript |
| Build | Maven |
| External API | NASA APOD Public API |
| Tools | Git & GitHub |

---

## 🌐 Backend API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/health` | Health Check |
| GET | `/api/apod/today` | Fetch Today’s APOD |
| GET | `/api/apod?date=YYYY-MM-DD` | Fetch APOD by date |
| GET | `/api/apod/recent?count=10` | Fetch recent APOD list |

---

## 🏁 Getting Started

### Backend Setup
```bash
cd backend
mvn clean install
mvn spring-boot:run


Backend will run at: http://localhost:8080

Frontend runs at: http://127.0.0.1:5500


