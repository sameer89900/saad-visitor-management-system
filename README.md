# Real Time Smart Visitor Management System using QR

**Capstone Project-II (IE402) | MCA Data Science**  
**Aurora Higher Education and Research Academy, Hyderabad**  
**Student:** Shaik Sameer | Roll No: 242P4R2014

---

## Project Structure

```
VMS_Project/
├── backend/                  ← Spring Boot REST API
│   ├── pom.xml
│   └── src/main/java/com/vms/
│       ├── VmsApplication.java
│       ├── model/            ← Entities
│       ├── dto/              ← Data Transfer Objects
│       ├── repository/       ← JPA Repositories
│       ├── service/          ← Business Logic
│       ├── controller/       ← REST Controllers
│       └── config/           ← Security & Config
└── frontend/                 ← React.js UI
    ├── package.json
    └── src/
        ├── index.js
        └── App.js
```

---

## Prerequisites

- Java JDK 17+
- Maven 3.x
- Node.js 18+ and npm
- PostgreSQL 14+

---

## Setup Instructions

### Step 1 — Create PostgreSQL Database

Open pgAdmin or psql and run:
```sql
CREATE DATABASE visitor_management;
```

### Step 2 — Start Backend

Open the `backend` folder in VS Code, find `VmsApplication.java` and click **Run ▶**

Or from terminal:
```bash
cd backend
mvn spring-boot:run
```

Backend runs at: `http://localhost:8080/api`

On first run, these are auto-created:
- Admin user: `admin` / `admin123`
- Security user: `security` / `security123`
- Sample departments and employees

### Step 3 — Start Frontend

```powershell
cd frontend
npm install
npm start
```

Frontend runs at: `http://localhost:3000`

---

## Login Credentials

| Role | Username | Password |
|------|----------|----------|
| Admin | `admin` | `admin123` |
| Security Guard | `security` | `security123` |

---

## API Endpoints

| Module | Base URL |
|--------|----------|
| Authentication | `/api/auth/login` |
| Visitors | `/api/visitors` |
| Employees | `/api/employees` |
| Departments | `/api/departments` |
| Visit Requests | `/api/visit-requests` |
| Entry Logs | `/api/entry-logs` |
| Blacklist | `/api/blacklist` |
| Dashboard | `/api/dashboard/stats` |

---

## Tech Stack

- **Backend:** Java 17, Spring Boot 3, Spring Security, JWT, Spring Data JPA, Hibernate, Maven
- **Frontend:** React.js 18, JavaScript ES6+, CSS-in-JS, Fetch API
- **Database:** PostgreSQL 14+
- **External API:** qrserver.com (QR code generation)
