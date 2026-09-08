# 💬 Real-Time Chat & Collaborative Editor

A full-stack **real-time communication and collaboration platform** built with **Spring Boot and React**.

The application combines **private messaging, group chat, and collaborative document editing** using WebSockets, allowing multiple users to communicate and work together in real time.

---

## 🚀 Features

### 💬 Real-Time Chat

* Private one-to-one messaging
* Group chat
* Real-time message delivery using WebSockets
* Online user tracking
* Multiple users can join the same room
* Session-aware user management

### 📝 Collaborative Editor

* Real-time collaborative text editing
* Multiple users can edit the same document
* Live cursor tracking
* Displays active collaborators
* User-specific cursor identification
* Synchronization of document changes through WebSockets

### 📁 File Handling

* Upload files
* Download files
* Share files through the application

### 🔐 Authentication & Security

* User authentication
* JWT-based authorization
* Protected REST endpoints
* WebSocket connection handling and authorization

### ⚡ Real-Time Architecture

* WebSocket communication using STOMP
* Redis Pub/Sub for scalable real-time communication
* Event-based handling of user connections and disconnections

---

## 🏗️ Tech Stack

### Frontend

* React
* JavaScript
* Vite
* Tailwind CSS
* React Router
* STOMP.js
* SockJS

### Backend

* Java
* Spring Boot
* Spring WebSocket
* Spring Security
* JWT
* REST APIs
* Maven

### Database & Infrastructure

* PostgreSQL
* Redis
* Docker
* Docker Compose

---

## 🧩 Architecture

```text
                    ┌──────────────────────┐
                    │      React Client    │
                    │                      │
                    │  Chat + Collaboration│
                    └──────────┬───────────┘
                               │
                    REST API   │   WebSocket
                               │
                               ▼
                    ┌──────────────────────┐
                    │    Spring Boot API   │
                    │                      │
                    │ REST + WebSocket     │
                    │ Security + JWT       │
                    └───────┬───────┬──────┘
                            │       │
                    ┌───────▼───┐ ┌─▼─────────┐
                    │ PostgreSQL│ │   Redis   │
                    │           │ │           │
                    │ Persistent│ │  caching  │
                    │   Data    │ │           │
                    └───────────┘ └───────────┘
```

---

## 🔄 Real-Time Communication

The application uses **WebSockets with STOMP** for real-time communication.

Instead of repeatedly polling the server for new messages:

```text
Client A
   │
   │ WebSocket
   ▼
Spring Boot
   │
   │ STOMP
   ▼
Room / Topic
   │
   ├──────────────► Client B
   ├──────────────► Client C
   └──────────────► Client D
```

When a user sends a message or document change, the server broadcasts the update to the appropriate users.

---

## 📝 Collaborative Editing

The collaborative editor maintains a shared document between connected users.

A simplified flow:

```text
User A edits document
        │
        ▼
   React Client
        │
        │ WebSocket
        ▼
   Spring Boot
        │
        ▼
  Collaboration Room
        │
        ├──────────────► User B
        ├──────────────► User C
        └──────────────► User D
```

The application also tracks **cursor positions** so collaborators can see where other users are currently editing.

The architecture is designed with real-time synchronization in mind and can be extended with advanced conflict-resolution techniques such as **Operational Transformation (OT)** or **CRDTs**.

---

## 👥 Online User Management

Users are tracked using their WebSocket sessions.

A user can have multiple WebSocket sessions, for example:

```text
User
 ├── Browser Tab 1 → Session A
 ├── Browser Tab 2 → Session B
 └── Browser Tab 3 → Session C
```

This allows the backend to distinguish between individual connections rather than treating every connection from the same user as one session.

When a session disconnects, only that specific session is removed from the room.

---

## 🔌 API Overview

### Authentication

```text
POST /api/auth/register
POST /api/auth/login
```

### Chat

```text
WebSocket: /ws

Private messages:
 /user/queue/private

Group messages:
 /topic/group/{roomId}
```

### Files

```text
POST /api/files/upload
GET  /api/files/{id}
```

> Endpoints may differ depending on the current implementation.

---

## 🐳 Running the Project

### 1. Clone the repository

```bash
git clone <https://github.com/RishabhSolanki21/WebChat>

cd <WebChat>
```

### 2. Start infrastructure

If using Docker Compose:

```bash
docker compose up -d
```

This starts the required services such as PostgreSQL and Redis.

### 3. Configure environment variables

Create your environment configuration and provide values for:

```env
DATABASE_URL=
DATABASE_USERNAME=
DATABASE_PASSWORD=

REDIS_HOST=
REDIS_PORT=

```

### 4. Start the backend

```bash
./mvnw spring-boot:run
```

Or on Windows:

```bash
mvnw.cmd spring-boot:run
```

### 5. Start the frontend

```bash
cd frontend
npm install
npm run dev
```

The frontend will normally be available at:

```text
http://localhost:5173
```

---

## 📂 Project Structure

```text
project/
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       └── resources/
│   │
│   ├── pom.xml
│   └── Dockerfile
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── ...
│   │
│   ├── package.json
│   └── vite.config.js
│
├── docker-compose.yml
└── README.md
```

---

## 🧠 Technical Concepts Demonstrated

This project demonstrates practical implementation of:

* REST API design
* WebSocket communication
* STOMP messaging
* Real-time group communication
* Private messaging
* JWT authentication
* Spring Security
* Redis Pub/Sub
* PostgreSQL
* Database relationships
* Session management
* Concurrent users
* Real-time state synchronization
* Cursor tracking
* React state management
* React refs
* Controlled/uncontrolled inputs
* Docker & Docker Compose
* Backend/frontend integration

---

## 🔮 Future Improvements

* [ ] Operational Transformation (OT)
* [ ] CRDT-based collaboration
* [ ] Persistent document version history
* [ ] Read receipts
* [ ] Typing indicators
* [ ] Better conflict resolution
* [ ] Image/file previews
* [ ] Notifications
* [ ] End-to-end encryption
* [ ] Horizontal backend scaling
* [ ] Production deployment
* [ ] Automated testing and CI/CD

---

## 🎯 Project Goal

The goal of this project is to build a practical **real-time collaboration system** while exploring how modern applications handle:

**communication → concurrency → synchronization → distributed systems**

Rather than relying only on traditional HTTP request/response communication, the application uses persistent WebSocket connections to synchronize users in real time.

---

## 👨‍💻 Author

**Rishabh**

Built as a full-stack learning project focused on:

**Java • Spring Boot • React • WebSockets • Redis • PostgreSQL • Docker**

---

## ⭐ Future Vision

The project can evolve from a simple chat application into a complete collaborative workspace similar in concept to applications such as collaborative editors and team communication platforms.

The long-term focus is on learning and implementing:

```text
Real-time communication
        ↓
Concurrency
        ↓
Distributed systems
        ↓
Conflict resolution
        ↓
Scalable collaboration
```

If you find the project interesting, consider giving it a ⭐.
