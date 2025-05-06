# 🐾  Hengqin Pet Adoption Platform 

A full-stack pet adoption system with separate backend and frontend components. This project enables pet information management, user interaction, intelligent recommendations, and system monitoring through an intuitive web interface.

--- 

## 📌 Features Overview
The system is divided into two main modules: the **Admin Backend** and the **User Frontend**.

### 🔒 Admin Backend
- Manage pets: add, edit, and view pet profiles.
- Category and tag management.
- View and moderate user comments.
- System-wide message publishing.
- Real-time system monitoring (CPU, memory, server info).
- Analytics dashboard for user and pet activity.

### 🌐 User Frontend
- User registration and login.
- Browse pets by newest, hottest, or recommended.
- Intelligent recommendations on detail pages.
- Personal user center with profile and messages.
- Adoption history management.
- Fuzzy search for pets.
- Comment on pet detail pages.

---

## 📁 Project Structure

```
Hengqin-Pet_Adoption/
├── server/              # Backend (Spring Boot)
│   ├── src/
│   │   ├── main/java/com/gk/study/
│   │   │   ├── commom         # Utility classes
│   │   │   ├── controller/    # API controllers
│   │   │   ├── service/       # Business logic
│   │   │   ├── entity/        # Entity classes
│   │   │   ├── interceptor/   # Request interceptors
│   │   │   ├── mapper/        # MyBatis database mappers
│   │   │   └── utils/         # Helper functions
│   │   ├── main/resources/
│   │   │   ├── mapper/
│   │   │   ├── application.yml
│   │   │   ├── logback-spring.xml
│   └── pom.xml
├── web/                 # Frontend (Vue 3 + Vite)
│   ├── build/                 # Build configurations
│   ├── public/                # Public static files
│   ├── src/                   # Source code
│   │   ├── api/               # API request wrappers
│   │   ├── assets/            # Static resources
│   │   ├── router/            # Vue Router config
│   │   ├── store/             # Global store management
│   │   ├── views/             # Page components
│   │   ├── main.js/           # Entry + initialization
│   │   ├── settings.js/       # System config
│   │   └── App.vue            # Root component
│   ├── package.json
│   └── vite.config.js         # vue config
```

---

## 🧱 Development Environment

| Component  | Description                        |
|------------|------------------------------------|
| Backend    | Java 1.8 + Spring Boot + MyBatis   |
| Frontend   | JavaScript + Vue                   |
| Database   | MySQL 8.0                          |
| IDEs       | IntelliJ IDEA 2024, VS Code        |
| OS         | Windows / MacOS                    |

---

## 🛠 Key Technologies

### 🔧 Backend Stack

- Spring Boot, MyBatis, Maven

### 💻 Frontend Stack

- ECMAScript 6 (ES6), Vue.js, Vuex, Vue Router, Vue CLI, Axios, Ant Design Vue (AntD)

---

## 🚀 Quick Start

### 🔧 Backend Setup

1. Open the `server` folder using IntelliJ IDEA.
2. **Configure `application.yml`**, including `DB_NAME` and `BASE_LOCATION` parameters.
3. **Install MySQL 8.0**, then create a database:
```sql
CREATE DATABASE java_adopt CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
4.	Restore the database from SQL file, in MySQL CLI, execute the following commands:
```sql
mysql> use java_adopt;
mysql> source /your/path/java_adopt.sql;
```
5. Run the backend server, in IntelliJ IDEA, click the green ▶️ Run button to start the application.


### 🌐 Frontend Setup

1. Install Node.js 16+
2. Install dependencies, in your terminal, navigate to the web/ folder and run:
```bash
npm install
```
3. Start the frontend
```bash
npm run dev
```
