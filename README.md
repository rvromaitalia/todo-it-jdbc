# Todo-It JDBC Refactor

This project is a refactored version of a Todo application where in-memory data storage has been replaced with a MySQL database using **JDBC (Java Database Connectivity)**.

The purpose of the project is to practice **database integration, JDBC usage, and clean separation of concerns** according to a given UML specification.

---

## 📌 Assignment Overview

The assignment focuses on:

* Refactoring an existing Todo application
* Integrating a MySQL database using JDBC
* Aligning Java models with database tables
* Implementing DAO-style interfaces based on a UML class diagram
* Persisting and retrieving data from a relational database

> **Note:** JDBC itself is **not unit-tested** as per assignment requirements.

---

## 🗄️ Database Structure

The application uses a MySQL database named `todoit` with the following tables:

### `person`

* `person_id` (Primary Key)
* `first_name`
* `last_name`

### `todo_item`

* `todo_id` (Primary Key)
* `title`
* `description`
* `deadline`
* `done`
* `assignee_id` (Foreign Key → `person.person_id`)

**Relationships:**

* One `Person` can be assigned to multiple `TodoItem` entries
* A `TodoItem` may be unassigned

The database schema is created using the provided SQL script.

---

## 🧩 Application Structure

The application follows a layered architecture:

* **Model layer**
  Represents database entities (`Person`, `TodoItem`)

* **DAO / Data layer (JDBC)**
  Handles all database interactions using JDBC

* **Interfaces & Implementations**
  Interfaces define required operations, while implementations contain the JDBC logic

Implementations are responsible for mapping:

* SQL rows ↔ Java objects

---

## 📐 UML-Based Interfaces

The interface names and responsibilities **strictly follow the provided UML class diagram**.

### People

Supports operations such as:

* Create person
* Find all persons
* Find by ID
* Find by name
* Update person
* Delete person

### TodoItems

Supports operations such as:

* Create todo item
* Find all todo items
* Find by ID
* Find by completion status (done / not done)
* Find by assignee
* Find unassigned todo items
* Update todo item
* Delete todo item

All operations are backed by the database using JDBC.

> **Design note:**
> The interface names (`People`, `TodoItems`) follow the UML specification provided in the assignment.
> In production Java systems, singular DAO naming (e.g. `PersonDAO`) is more common, but the UML-defined names are intentionally preserved here to ensure full compliance with the assignment requirements.

---

## ⚙️ Technologies Used

* Java
* JDBC
* MySQL
* MySQL Workbench
* Maven

---

## 🚀 How to Run

1. Create the database using the provided SQL script (`03_todoit.sql`)
2. Ensure MySQL is running
3. Configure database connection settings
4. Run the application from your IDE

---

## 🎯 Learning Outcomes

Through this project, the following concepts are practiced:

* JDBC database connectivity
* SQL ↔ Java object mapping
* Refactoring from in-memory storage to persistent storage
* Interface-driven design
* Relational database modeling
* Separation of concerns using DAO pattern

---

## 📄 Notes

* This project is part of a learning assignment
* Focus is on correctness, structure, and clarity
* Performance optimization is out of scope
* No automated JDBC tests are required

---

## 👤 Author

**Roman Vanoyan**
