# DreamStream Architecture

## Overview

DreamStream is a migration and rebuild of the old HelpEachOther .NET MVC/Razor project.

The new application will use:

- Spring Boot backend
- Angular frontend
- PostgreSQL database
- REST API communication
- Liquibase database migrations

## Repository Structure

```text
DreamStream/
├── backend/
├── frontend/
├── legacy-dotnet/
│   └── HelpEachOther/
├── docs/
├── AGENTS.md
├── README.md
└── .gitignore
```

## Backend Architecture

The backend should be built as a modular monolith.

This means the application is one Spring Boot application, but the code is separated by business modules.

Recommended structure:

```text
backend/src/main/java/com/dreamstream/
├── DreamStreamApplication.java
├── auth/
├── users/
├── helprequests/
├── offers/
├── points/
├── notifications/
├── admin/
├── common/
└── config/
```

## Why Modular Monolith First

Microservices are not needed at the beginning.

A modular monolith is better for the MVP because it is:

- easier to build
- easier to debug
- easier to deploy
- easier to understand
- better for early product changes

The project can be split into microservices later if needed.

## Possible Future Microservices

If DreamStream grows, these modules could become separate services:

- user-service
- request-service
- offer-service
- notification-service
- points-service
- admin-service
- matching-service

Do not create these services now.

## Backend Layers per Feature

Each feature should follow this general pattern:

```text
helprequests/
├── HelpRequestController.java
├── HelpRequestService.java
├── HelpRequestRepository.java
├── HelpRequestEntity.java
├── dto/
│   ├── CreateHelpRequestRequest.java
│   ├── UpdateHelpRequestRequest.java
│   └── HelpRequestResponse.java
└── mapper/
    └── HelpRequestMapper.java
```

## API Rules

All backend endpoints should start with:

```text
/api
```

Example endpoints:

```text
POST   /api/auth/register
POST   /api/auth/login
GET    /api/me

GET    /api/requests
POST   /api/requests
GET    /api/requests/{id}
PUT    /api/requests/{id}
DELETE /api/requests/{id}

GET    /api/my-requests

POST   /api/requests/{id}/offers
GET    /api/my-offers
PUT    /api/offers/{id}/accept
PUT    /api/offers/{id}/decline
```

## Error Handling

Use a global exception handler.

Standard error response:

```json
{
  "code": "validation_error",
  "message": "Request validation failed",
  "details": {
    "title": "Title is required"
  },
  "traceId": "optional-trace-id"
}
```

Common error codes:

- validation_error
- not_found
- unauthorized
- forbidden
- conflict
- internal_error

## Database

Use PostgreSQL.

Use Liquibase for schema changes.

Recommended database tables for MVP:

- users
- help_requests
- help_offers

Later tables:

- points_transactions
- notifications
- messages
- admin_actions

## Security

Authentication can be added after the first basic request flow works.

Recommended final approach:

- Spring Security
- JWT access token
- BCrypt password hashing
- role-based authorization

MVP temporary approach is allowed:

- simple demo user
- mocked current user service
- no full security until core features are working

## Frontend Architecture

Angular should be organized by features.

Recommended structure:

```text
frontend/src/app/
├── core/
├── shared/
├── auth/
├── requests/
├── profile/
├── offers/
├── admin/
└── layout/
```

## Frontend Pages

MVP pages:

- `/`
- `/login`
- `/register`
- `/requests`
- `/requests/new`
- `/requests/:id`
- `/requests/:id/edit`
- `/profile`
- `/my-requests`
- `/my-offers`

## Development Flow

Recommended order:

1. Backend project setup
2. PostgreSQL connection
3. Liquibase initial schema
4. User entity
5. HelpRequest entity
6. Help request REST API
7. Angular project setup
8. Angular request list page
9. Angular create request page
10. Authentication
11. Offer help flow
12. Admin tools

## Migration Rule

The old HelpEachOther .NET project is a reference only.

New code should be written cleanly for DreamStream instead of directly copying old .NET structure.