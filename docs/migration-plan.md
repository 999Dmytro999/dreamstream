# DreamStream Migration Plan

## Goal

Migrate the old HelpEachOther .NET MVC/Razor application into a new DreamStream application built with Spring Boot and Angular.

## Old Project

Name:

```text
HelpEachOther
```

Technology:

- .NET
- MVC/Razor views
- server-rendered pages

Location in repository:

```text
legacy-dotnet/HelpEachOther/
```

The old project should be used only as a reference.

## New Project

Name:

```text
DreamStream
```

Technology:

- Spring Boot backend
- Angular frontend
- PostgreSQL database
- REST API
- Liquibase migrations

## Migration Strategy

Do not rewrite everything at once.

First, analyze the old project.

Then build the new DreamStream application feature by feature.

## Phase 1: Legacy Analysis

Inspect the HelpEachOther project and document:

- controllers
- models
- views
- database entities
- forms
- validation rules
- user flows
- existing features
- missing or broken functionality

Output should be added to this file.

## Phase 2: Backend Foundation

Create Spring Boot backend foundation inside:

```text
backend/
```

Include:

- Maven setup
- PostgreSQL config
- Liquibase config
- global exception handler
- CORS config for Angular
- basic health endpoint
- feature-based package structure

## Phase 3: Core Domain Models

Create the first domain models:

- User
- HelpRequest
- HelpOffer

Recommended Java package modules:

```text
auth
users
helprequests
offers
common
config
```

## Phase 4: Help Request API

Create REST endpoints:

```text
GET    /api/requests
POST   /api/requests
GET    /api/requests/{id}
PUT    /api/requests/{id}
DELETE /api/requests/{id}
GET    /api/my-requests
```

For the first version, a temporary mocked current user service is acceptable.

## Phase 5: Angular Foundation

Create Angular frontend inside:

```text
frontend/
```

Add:

- routing
- layout
- header/navbar
- API service
- request list page
- request details page
- create request page

## Phase 6: Authentication

Add:

- registration
- login
- password hashing
- JWT authentication
- authenticated current user endpoint
- route guards in Angular

Endpoints:

```text
POST /api/auth/register
POST /api/auth/login
GET  /api/me
```

## Phase 7: Offer Help Flow

Add ability for users to offer help.

Endpoints:

```text
POST /api/requests/{id}/offers
GET  /api/my-offers
PUT  /api/offers/{id}/accept
PUT  /api/offers/{id}/decline
```

## Phase 8: Polish MVP

Improve:

- validation messages
- empty states
- loading states
- error handling
- responsive layout
- user profile page
- request status badges

## Phase 9: Future Features

Later features:

- soul points
- notifications
- messaging
- admin dashboard
- request verification
- donations
- microservices
- AI matching

## MVP Implementation Order

Recommended order:

1. Set up monorepo structure.
2. Add documentation files.
3. Add legacy HelpEachOther project under `legacy-dotnet/HelpEachOther/`.
4. Ask Codex to analyze the legacy project.
5. Ask Codex to update this migration plan with real findings from the legacy code.
6. Build Spring Boot backend foundation.
7. Build first HelpRequest API.
8. Build Angular frontend foundation.
9. Connect Angular to backend.
10. Add authentication.
11. Add offer help flow.

## First Codex Task

Use this prompt:

```text
Review this repository and prepare a migration summary.

Context:
This is a monorepo for DreamStream.
- backend/ contains the new Spring Boot Maven backend.
- frontend/ will contain the Angular frontend.
- legacy-dotnet/HelpEachOther/ contains the old .NET MVC/Razor application and should be used only as a reference.
- docs/ contains project documentation.

Task:
1. Inspect the legacy-dotnet/HelpEachOther application.
2. Identify the main features, pages, models, controllers, and database entities.
3. Update docs/migration-plan.md with:
   - current legacy features
   - target Spring Boot modules
   - target Angular pages
   - suggested REST API endpoints
   - recommended MVP implementation order
4. Do not modify backend or frontend code yet.
5. Do not modify legacy-dotnet/HelpEachOther.
```

## Notes for Codex

When analyzing the legacy HelpEachOther project:

- Do not rewrite code immediately.
- Do not change files in `legacy-dotnet/HelpEachOther/`.
- First document what already exists.
- Identify which features are useful for DreamStream.
- Identify which features should be redesigned.
- Keep DreamStream clean and simple.
- Prioritize MVP functionality before advanced features.