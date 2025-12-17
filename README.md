# Todo-It JDBC Refactor

This project is a refactored version of a Todo application where in-memory data storage has been replaced with a MySQL database using **JDBC (Java Database Connectivity)**.

The purpose of the project is to practice **database integration, JDBC usage, and clean separation of concerns** according to a given UML specification.

---

## 📌 Assignment Overview

The assignment focuses on:

- Refactoring an existing Todo application
- Integrating a MySQL database using JDBC
- Aligning Java models with database tables
- Implementing DAO-style interfaces based on a UML diagram
- Persisting and retrieving data from a relational database

> Note: JDBC itself is **not unit-tested** as per assignment requirements.

---

## 🗄️ Database Structure

The application uses a MySQL database named `todoit` with the following tables:

### `person`
- `person_id` (Primary Key)
- `first_name`
- `last_name`

### `todo_item`
- `todo_id` (Primary Key)
- `title`
- `description`
- `deadline`
- `done`
- `assignee_id` (Foreign Key → `person.person_id`)

Relationship:
- One `Person` can be assigned to multiple `TodoItems`
- A `TodoItem` may be unassigned

The database is created using the provided SQL script.

---

## 🧩 Application Structure

The application follows a layered structure:

- **Model layer**  
  Represents database entities (`Person`, `TodoItem`)

- **DAO / Data layer (JDBC)**  
  Handles all database interactions using JDBC

- **Interfaces**  
  Define required operations for managing people and todo items

Implementations are responsible for translating:
- SQL rows ↔ Java objects

---

## 📐 UML-Based Interfaces

### People
Supports operations such as:
- Create person
- Find all persons
- Find by ID
- Find by name
- Update person
- Delete person

### TodoItems
Supports operations such as:
- Create todo
- Find all todos
- Find by ID
- Find by status (done / not done)
- Find by assignee
- Find unassigned todos
- Update todo
- Delete todo

All operations are backed by the database.

---

## ⚙️ Technologies Used

- Java
- JDBC
- MySQL
- MySQL Workbench
- Maven / Gradle (depending on setup)

---

## 🚀 How to Run

1. Create the database using the provided SQL script (`todoit.sql`)
2. Ensure MySQL is running
3. Configure database connection settings
4. Run the application from your IDE

---

## 🎯 Learning Outcomes

Through this project, the following concepts are practiced:

- JDBC database connectivity
- SQL ↔ Java object mapping
- Refactoring from in-memory storage to persistent storage
- Interface-driven design
- Relational database modeling

---

## 📄 Notes

- This project is part of a learning assignment
- Focus is on structure and correctness, not optimization
- No automated JDBC tests are required

---

## 👤 Author

Roman Vanoyan
