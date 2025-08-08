# 📚 Mini Library Management System (MiniLMS)

MiniLMS is a Spring Boot-based application designed to manage a small library system.  
It supports **JWT-based authentication**, **role-based access control (RBAC)**, and CRUD operations for **books, users, authors, and borrowings**.  
Built with **Spring Boot**, **Spring Security**, and **H2 Database** for rapid development and testing.

---

## 🚀 Features

- **JWT Authentication** (Login/Register)
- **Role-based Authorization** (`ADMIN`, `USER`)
- **Book Management**: Add, search, view
- **Borrowing Management**: Borrow and return books
- **Author Management**
- **H2 in-memory database**
- **Swagger API Documentation**
- **Unit & Integration Tests**
- Optional: Caching, Scheduling, Dockerization

---

## 🛠 Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security + JWT**
- **Spring Data JPA**
- **H2 Database**
- **Maven**
- **Lombok**
- **Swagger (Springdoc OpenAPI)**

---

## 📦 Installation & Setup

### 1️⃣ Clone the repository
```bash
git clone https://github.com/yourusername/minilms.git
cd minilms
```

### 2️⃣ Build & Run
```bash
mvn spring-boot:run
```

### 3️⃣ Access the App
- **API Base URL**: `http://localhost:8080/api`
- **Swagger Docs**: `http://localhost:8080/swagger-ui.html`
- **H2 Console**: `http://localhost:8080/h2-console`

---

## 🔑 Authentication

### Register
```bash
curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d '{
  "username": "john",
  "password": "password123",
  "role": "USER"
}'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{
  "username": "john",
  "password": "password123"
}'
```
You will receive a **JWT token**. Use it in subsequent requests:
```bash
-H "Authorization: Bearer <TOKEN>"
```

---

## 📚 Book Search Example
```bash
curl -X GET "http://localhost:8080/api/books/search?title=Java&author=Smith&isbn=1234567890" -H "Authorization: Bearer <TOKEN>"
```


## 📬 API Testing with Postman

Para probar los endpoints de la API, se incluye el archivo:

**`Mini Library Management System (LMS).postman_collection.json`**

### Instrucciones de uso:
1. Abre Postman.
2. Haz clic en **Import**.
3. Selecciona el archivo `Mini Library Management System (LMS).postman_collection.json` que encontrarás en la carpeta `postman/` de este repositorio.
4. Configura la variable de entorno `BASE_URL` (por defecto: `http://localhost:8080`).
5. Ejecuta las peticiones para probar las funciones de la API (CRUD de libros, autenticación, préstamos, etc.).

💡 *Esta colección contiene ejemplos listos para ejecutar, incluyendo autenticación con JWT y pruebas de búsqueda de libros.*

---

## 🧪 Running Tests
```bash
mvn test
```

---

## 🗄 Sample Data (Seed)
You can add seed data via `data.sql` in `src/main/resources`:
```sql
INSERT INTO authors (id, name) VALUES (1, 'J.K. Rowling');
INSERT INTO books (id, title, isbn, author_id) VALUES (1, 'Harry Potter', '9780747532743', 1);
```

---

## 📝 License
This project is licensed under the MIT License.
