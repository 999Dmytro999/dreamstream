# AGENTS.md

## Project Overview

DreamStream is a web platform where people can create help requests and other people can offer help.

The project is being rebuilt from an older .NET MVC/Razor application called HelpEachOther.

## Repository Structure

- `backend/` - new Spring Boot backend
- `frontend/` - new Angular frontend
- `legacy-dotnet/HelpEachOther/` - old .NET MVC/Razor project, used only as a reference
- `docs/` - project documentation

## Important Rules

- Do not modify `legacy-dotnet/HelpEachOther/` unless explicitly asked.
- Use the legacy .NET project only to understand old features, models, pages, and business logic.
- New backend code must go into `backend/`.
- New frontend code must go into `frontend/`.
- Documentation must go into `docs/`.

## Target Tech Stack

Backend:
- Java 21
- Spring Boot
- Maven
- Spring Web
- Spring Data JPA
- PostgreSQL
- Liquibase
- Validation
- Spring Security later

Frontend:
- Angular
- TypeScript
- REST API integration
- Responsive UI

Database:
- PostgreSQL
- Liquibase migrations

## Architecture Direction

Start with a modular monolith.

Do not introduce microservices yet.

Recommended backend modules:
- auth
- users
- helprequests
- offers
- points
- notifications
- admin
- common

## Coding Rules

- Keep code clean and simple.
- Do not over-engineer the MVP.
- Use DTOs in controllers.
- Do not expose JPA entities directly from REST controllers.
- Use validation annotations for request DTOs.
- Use a global exception handler.
- Use meaningful error responses.
- Keep REST endpoints under `/api`.
- Prefer UUID IDs.
- Use createdAt and updatedAt fields for main entities.

## Migration Goal

Rebuild HelpEachOther as DreamStream using Spring Boot and Angular.

The first MVP should include:

- user registration
- login
- user profile
- create help request
- list help requests
- view help request details
- edit own help request
- close or complete help request
- basic offer/help response flow