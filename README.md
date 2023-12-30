# School System APIs

This repository contains two distinct school system APIs, each leveraging a different database technology:

1. **[schoolsystem-mysql](./schoolsystem-mysql/)**: A School System API implemented using MySQL.
2. **[schoolsystem-mongo](./schoolsystem-mongo/)**: A School System API implemented using MongoDB.

Both applications provide similar functionalities tailored for managing a school system, but they utilize different database technologies based on specific requirements, scalability needs, and data modeling preferences.

---

## Directory Structure


---

## Setup and Installation

### 1. schoolsystem-mysql:

1. Navigate to the [schoolsystem-mysql](./schoolsystem-mysql/) directory.
2. Configure the MySQL database connection properties in `application.properties`.
3. Run the Spring Boot application using Maven or your preferred IDE.

### 2. schoolsystem-mongo:

1. Navigate to the [schoolsystem-mongo](./schoolsystem-mongo/) directory.
2. Ensure MongoDB is installed and running.
3. Run the Spring Boot application using Maven or your preferred IDE.

---

## API Endpoints

Both `schoolsystem-mysql` and `schoolsystem-mongo` applications offer RESTful endpoints for managing various entities such as:

- **Students**: CRUD operations for student records.
- **Classrooms**: Operations related to course management.
- **Enrollments**: Enroll or unregister students in courses, etc.

For comprehensive API documentation, including endpoint descriptions, request/response examples, and usage guidelines, please refer to the respective application directories ([schoolsystem-mysql](./schoolsystem-mysql/) and [schoolsystem-mongo](./schoolsystem-mongo/)).

---
