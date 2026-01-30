# Finance Manager (Console Application)

A Java-based console application for managing personal finances.
The application supports role-based access (Admin / User), secure authentication,
and persistent storage using JDBC and MySQL.

---

## Features

- Role-based login (Admin and User)
- Secure password hashing
- Session-based authorization
- Admin:
  - Create user accounts
  - List own created users
  - Delete user accounts
- User:
  - Add income and expense transactions
  - View and delete own transactions
  - Manage categories (add / delete / list)
- Input validation and custom exception handling
- Unit tests with JUnit 5 and Mockito

---


## Project Structure

```text

finance-manager/
├─ db/
│  ├─ schema.sql
│  └─ seed.sql
│
├─ src/main/java/io/github/arintasoglu/finance_manager/
│  ├─ databaseconnection/
│  ├─ exception/
│  ├─ model/
│  ├─ repository/
│  ├─ service/
│  ├─ ui/
│  ├─ util/
│  └─ App.java
│
├─ src/test/java/io/github/arintasoglu/finance_manager/
│  └─ (unit tests)
│
├─ .gitignore
├─ pom.xml
└─ README.md

```
---

## Database Setup

1. Open MySQL Workbench
2. Execute the following files in order:

    - `db/schema.sql`
    - `db/seed.sql`

The application expects a local MySQL database:

URL: `jdbc:mysql://localhost:3306/finance`

Username: `root`

Password: `root`

These credentials are intended for local development only and can be changed in the DatabaseConnection class.

You can change the database credentials in:
`src/main/java/io/github/arintasoglu/finance_manager/databaseconnection/DatabaseConnection.java`

---
## Demo Accounts
After running seed.sql, you can use the following demo accounts:

Admin

- `Username: admin`

- `Password: 123`

User

- `Username: user`

- `Password: 123`

---

## How to Run
You can run the application by executing the `main` method in
`io.github.arintasoglu.finance_manager.App`.
