# DreamStream Backend

Spring Boot backend foundation for DreamStream.

## Prerequisites

- Java 21
- Maven 3.9+
- PostgreSQL 14+

## Local run

1. Create a PostgreSQL database and user (example values below):
   - Database: `dreamstream`
   - Username: `dreamstream`
   - Password: `dreamstream`
2. Configure environment variables (or use defaults from `application.yml`):

```bash
export DB_URL=jdbc:postgresql://localhost:5432/dreamstream
export DB_USERNAME=dreamstream
export DB_PASSWORD=dreamstream
```

3. Start the app:

```bash
cd backend
mvn spring-boot:run
```

The service runs on `http://localhost:8080` by default.

## Included foundation

- Feature package layout: `auth`, `users`, `helprequests`, `offers`, `common`, `config`
- Liquibase migration setup (`src/main/resources/db/changelog/db.changelog-master.yaml`)
- PostgreSQL datasource configuration in `application.yml`
- CORS enabled for Angular dev server (`http://localhost:4200`) on `/api/**`
- Global REST exception handling and standard `ErrorResponse`
- Health endpoint: `GET /api/health`
