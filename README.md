# Family Link Server

Backend service for the Family Link application — a Spring Boot server providing REST APIs and WebSocket support for connecting grandparents and grandchildren through interactive activities.

## Tech Stack

| Category | Technology |
|----------|-----------|
| **Framework** | Spring Boot 3.3.7 |
| **Language** | Java 17 |
| **ORM** | MyBatis-Plus 3.5.9 |
| **Database** | MySQL 8.0 |
| **Real-time** | WebSocket (STOMP + SockJS) |
| **File Storage** | MinIO (S3-compatible) |
| **Security** | Spring Security (CORS + CSRF disabled) |
| **Build** | Maven |
| **Validation** | Spring Boot Validation (Jakarta) |

## Project Structure

```
src/main/java/com/example/linkfamily/
├── Config/              # Spring configuration
│   ├── MinioConfig.java        # MinIO client bean
│   ├── SecurityConfig.java     # CORS & security filter chain
│   └── WebSocketConfig.java    # STOMP WebSocket broker config
├── Controller/          # REST & WebSocket controllers
│   ├── FamilyMomentController.java  # Family photo CRUD
│   ├── PuzzleController.java        # Puzzle data & lock status
│   ├── PuzzleSyncController.java    # WebSocket puzzle sync
│   ├── StoryController.java         # Story CRUD & audio upload
│   └── UserController.java          # User role query
├── Exception/           # Custom exceptions & global handler
│   ├── FileException.java
│   ├── PhotoException.java
│   └── GlobalExceptionHandler.java
├── Mapper/              # MyBatis-Plus mappers
│   ├── FamilyPhotoMapper.java
│   ├── PhotoMapper.java
│   ├── StoryMapper.java
│   └── UserMapper.java
├── Model/
│   ├── Dto/             # Request DTOs & enums
│   ├── Entity/          # Database entities (Photo, Story, User)
│   └── Vo/              # Response VOs
├── Response/
│   └── Response.java    # Unified API response wrapper
├── Service/
│   ├── ServiceImpl/     # Service implementations
│   └── (interfaces)     # Service interfaces
├── Utils/               # Validation utilities
└── LinkfamilyApplication.java
```

## API Overview

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/user/selectUser/{id}` | Get user by ID |
| GET | `/story/list` | List stories (paginated) |
| POST | `/story/create` | Create a story |
| PUT | `/story/update/{id}` | Update a story |
| DELETE | `/story/delete/{id}` | Delete a story |
| GET | `/story/getById/{id}` | Get story by ID |
| GET | `/story/getByPhotoId/{photoId}` | Get story linked to a photo |
| POST | `/story/incrementListenCount/{storyId}` | Increment listen count |
| GET | `/puzzle/getAllpuzzles` | List all puzzle photos |
| POST | `/puzzle/updateIsLocked` | Update puzzle lock status |
| GET | `/family-moment/photos` | List family photos (paginated) |
| POST | `/family-moment/upload` | Upload a family photo |
| DELETE | `/family-moment/photos/{id}` | Delete a family photo |

### WebSocket Endpoints

| STOMP Destination | Description |
|-------------------|-------------|
| `/app/puzzle.join` | Join a puzzle room |
| `/app/puzzle.start` | Start a puzzle |
| `/app/puzzle.move` | Send a puzzle move |
| `/app/puzzle.complete` | Mark puzzle as complete |
| `/app/puzzle.help-request` | Request help |
| `/app/puzzle.help-accept` | Accept help (take over control) |

Broadcast topic: `/topic/puzzle/{roomId}`

## Getting Started

### Prerequisites

- JDK 17+
- Maven 3.6+
- MySQL 8.0
- MinIO Server (for file storage)

### 1. Database Setup

The `sql/` directory contains initialization scripts for MySQL. Create the database and import the tables:

```bash
# Connect to MySQL and create the database
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS family_link CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# Import table structures and seed data
mysql -u root -p family_link < sql/family_link_tb_user.sql
mysql -u root -p family_link < sql/family_link_tb_story.sql
mysql -u root -p family_link < sql/family_link_tb_photo.sql
```

#### Database Tables

| Table | Description |
|-------|-------------|
| `tb_user` | User roles (`grandson` / `grandparent`). Seed data: `Dave (grandson)`, `grandpa/grandma (grandparent)` |
| `tb_story` | Stories with audio URLs, task types/questions, lock status, listen count |
| `tb_photo` | Photos used for both puzzles (`type=1`) and family moments (`type=2`), stored in MinIO |

### 2. MinIO Setup

1. Download and start [MinIO Server](https://min.io/docs/minio/windows/index.html)
2. Create a bucket named `family-link`
3. Make the bucket publicly readable (or configure presigned URLs)

### 3. Configuration

Edit `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/family_link
    username: root
    password: your_password

minio:
  endpoint: http://localhost:9000
  accessKey: your_minio_access_key
  secretKey: your_minio_secret_key
  bucket: family-link
```

### 4. Run

```bash
# Build
mvn clean package

# Run
mvn spring-boot:run
```

The server starts at `http://localhost:8080`.

## Frontend

The frontend React application is available.
