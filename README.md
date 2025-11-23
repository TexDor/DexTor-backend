# DexTor Backend

Spring Boot REST API for inventory management system.

## Prerequisites

Before running this project, make sure you have the following installed:

- **Java 21** (JDK)
- **MySQL 8.0+**
- **Maven** (or use the included Maven wrapper `./mvnw`)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd DexTor-backend
```

### 2. Set Up MySQL Database

Start MySQL and create the database:

```sql
CREATE DATABASE texdor_db;
```

### 3. Configure Application Properties

Copy the example properties file and update with your credentials:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Then edit `src/main/resources/application.properties` and update:

- `spring.datasource.password` with your MySQL password

### 4. Run the Application

#### Option A: Using the start script (macOS/Linux)

```bash
./start.sh
```

#### Option B: Using Maven wrapper

```bash
./mvnw spring-boot:run
```

#### Option C: Using Maven (if installed)

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Test Endpoints

- **GET** `/api/hello` - Simple hello world endpoint

  ```bash
  curl http://localhost:8080/api/hello
  ```

- **GET** `/api/test/connection` - Test database connection

  ```bash
  curl http://localhost:8080/api/test/connection
  ```

- **POST** `/api/test/create?message=<your-message>` - Create a test record

  ```bash
  curl -X POST "http://localhost:8080/api/test/create?message=Hello"
  ```

- **GET** `/api/test/all` - Get all test records
  ```bash
  curl http://localhost:8080/api/test/all
  ```

## Stopping the Application

Press `Ctrl + C` in the terminal where the app is running, or:

```bash
lsof -ti:8080 | xargs kill -9
```

## Troubleshooting

### Port 8080 already in use

If you get "port already in use" error, stop the existing process:

```bash
lsof -ti:8080 | xargs kill -9
```

### MySQL Connection Failed

- Make sure MySQL is running
- Verify database `texdor_db` exists
- Check username/password in `application.properties`

### Java Runtime Not Found

Set JAVA_HOME environment variable:

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH
```

## Project Structure

```
src/
├── main/
│   ├── java/com/group2/inventory/
│   │   ├── controller/        # REST API controllers
│   │   ├── model/            # Entity classes
│   │   ├── repository/       # Data access layer
│   │   └── TexDorApplication.java
│   └── resources/
│       └── application.properties
└── test/
```

## Tech Stack

- **Spring Boot 3.5.7**
- **Java 21**
- **MySQL 8.0**
- **Spring Data JPA**
- **Hibernate**
- **Maven**

## Contributors

- Group 2
