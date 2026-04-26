# DreamStream Migration Plan

## Goal

Migrate the legacy `HelpEachOther` ASP.NET MVC/Razor application (`legacy-dotnet/HelpEachOther/`) into DreamStream with:

- Spring Boot backend (`backend/`)
- Angular frontend (`frontend/`)
- PostgreSQL + Liquibase

This document captures the **current legacy implementation** and a concrete migration target for MVP.

---

## Current Legacy Features (from code inspection)

1. **Authentication (ASP.NET Core Identity)**
   - Register, login, logout flows via Identity Razor Pages.
   - Auto sign-in after successful registration.
   - No confirmed-account requirement.

2. **Help request lifecycle**
   - Public list of help requests with optional status filter (`Open`, `InProgress`, `Completed`).
   - Public request details page.
   - Authenticated users can create requests.
   - Owners can edit/delete only when request is `Open`.
   - Non-owner authenticated users can volunteer for an `Open` request.
   - Owner can mark `InProgress` request as completed.

3. **Soul Points rule**
   - When owner completes an in-progress request, assigned helper gains **10 Soul Points**.
   - Rule is centralized in `Services/HelpRequestService.cs`.

4. **Profile and personal activity**
   - Profile summary includes username/display name, soul points, request/help stats.
   - “My Requests” page (owner perspective, optional status filter).
   - “My Helping” page (requests user volunteered for).

5. **Home dashboard metrics**
   - Landing page shows open and completed request counts.

6. **Seed/demo behavior**
   - On startup: applies migrations and seeds demo user + sample requests if none exist.

---

## Current Legacy Controllers

### MVC Controllers

- `Controllers/HomeController.cs`
  - `Index()` → homepage stats (open/completed counts).

- `Controllers/HelpRequestsController.cs`
  - `Index(HelpRequestStatus? status)`
  - `Details(int id)`
  - `Create()` (GET)
  - `Create(CreateHelpRequestViewModel model)` (POST)
  - `Edit(int id)` (GET)
  - `Edit(int id, EditHelpRequestViewModel model)` (POST)
  - `Delete(int id)` (GET)
  - `DeleteConfirmed(int id)` (POST)
  - `Volunteer(int id)` (POST)
  - `Complete(int id)` (POST)

- `Controllers/ProfileController.cs` (authorized)
  - `Index()`
  - `MyRequests(HelpRequestStatus? status)`
  - `MyHelping()`

### Identity Razor Page Model

- `Areas/Identity/Pages/Account/Register.cshtml.cs`
  - `OnGet()`
  - `OnPostAsync()`

> Login/logout functionality is provided by ASP.NET Identity UI, but only Register page is explicitly scaffolded in this repo.

---

## Current Legacy Models / Entities

### Domain + identity models

- `Models/ApplicationUser.cs`
  - Extends `IdentityUser`
  - Fields: `DisplayName`, `SoulPoints`, `CreatedAt`
  - Navigation: `CreatedRequests`, `AcceptedRequests`

- `Models/HelpRequest.cs`
  - Fields: `Id`, `Title`, `Description`, `Category`, `City`, `Status`, `CreatedAt`, `CompletedAt`, `OwnerId`, `HelperId`
  - Navigation: `Owner`, `Helper`

- `Models/HelpRequestStatus.cs` (enum)
  - `Open`, `InProgress`, `Completed`

- `Models/HelpCategories.cs`
  - Static categories list: Groceries, Transport, Moving, Household, Emotional Support, Other

### View models

- `ViewModels/CreateHelpRequestViewModel.cs`
- `ViewModels/EditHelpRequestViewModel.cs`
- `ViewModels/ProfileViewModel.cs`

### EF Core context and schema

- `Data/ApplicationDbContext.cs`
  - `DbSet<HelpRequest>`
  - Owner and Helper relations to `ApplicationUser` (restrict delete)

- `Data/Migrations/20260403053555_InitialCreate.cs`
  - Tables: `HelpRequests` + ASP.NET Identity tables (`AspNetUsers`, `AspNetRoles`, etc.)

---

## Current Legacy Pages / Views

### MVC Views

- Home:
  - `Views/Home/Index.cshtml`

- Help requests:
  - `Views/HelpRequests/Index.cshtml`
  - `Views/HelpRequests/Details.cshtml`
  - `Views/HelpRequests/Create.cshtml`
  - `Views/HelpRequests/Edit.cshtml`
  - `Views/HelpRequests/Delete.cshtml`

- Profile:
  - `Views/Profile/Index.cshtml`
  - `Views/Profile/MyRequests.cshtml`
  - `Views/Profile/MyHelping.cshtml`

- Shared/layout:
  - `Views/Shared/_Layout.cshtml`
  - `Views/Shared/_LoginPartial.cshtml`
  - `Views/Shared/Error.cshtml`

### Identity Razor Pages

- `Areas/Identity/Pages/Account/Register.cshtml`
- (plus default Identity pages not explicitly scaffolded in this repository)

---

## Target Spring Boot Modules (Modular Monolith)

Recommended modules aligned with AGENTS.md architecture direction and legacy behavior:

1. `auth`
   - Registration/login, token issuance, current-user context.

2. `users`
   - User profile data (display name, soul points, user summary).

3. `helprequests`
   - CRUD + lifecycle state transitions for help requests.

4. `offers`
   - Volunteer/offer workflow (assign helper, list my helping).

5. `points`
   - Soul Points awarding rules and audit-ready point transactions (optional lightweight table now, extensible later).

6. `common`
   - Shared error model, validation handling, base response patterns, utilities.

7. `notifications` (deferred)
   - Keep as placeholder module for later event notifications.

8. `admin` (deferred)
   - Placeholder for moderation/admin functionality post-MVP.

---

## Target Angular Pages

1. Public
   - Home dashboard
   - Help request list (with status filter)
   - Help request details

2. Authentication
   - Register
   - Login

3. Authenticated request management
   - Create help request
   - Edit own help request (open only)
   - Delete confirmation flow

4. Profile area
   - My profile (stats, soul points)
   - My requests (created by me, with status filter)
   - My helping (accepted/assigned requests)

5. Shared UX
   - Header/nav with auth state
   - Toast/alert components for success/error messages

---

## Suggested REST API Endpoints (MVP)

All endpoints under `/api`.

### Auth

- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/logout` (if session/cookie approach)
- `GET /api/auth/me`

### Users / profile

- `GET /api/users/me`
- `PATCH /api/users/me` (display name/profile edits)
- `GET /api/users/me/stats`

### Help requests

- `GET /api/help-requests?status=OPEN|IN_PROGRESS|COMPLETED&owner=me&helper=me`
- `POST /api/help-requests`
- `GET /api/help-requests/{id}`
- `PUT /api/help-requests/{id}` (owner only, open only)
- `DELETE /api/help-requests/{id}` (owner only, open only)

### Offer/help flow

- `POST /api/help-requests/{id}/volunteer` (assign current user as helper)
- `POST /api/help-requests/{id}/complete` (owner completes; awards points)

### Optional compatibility/readability endpoints

- `GET /api/me/requests` (maps to filtered help-requests)
- `GET /api/me/helping` (maps to filtered help-requests)

---

## Recommended MVP Implementation Order

1. **Domain and schema foundation**
   - Define `users`, `help_requests`, and optional `point_transactions` tables with Liquibase.
   - Use UUID primary keys in new system (even though legacy used int/string identity IDs).

2. **Auth module**
   - Implement register/login/current-user endpoints.
   - Wire security config and request authentication.

3. **Help requests: read side**
   - Implement list + details endpoints with status filtering.
   - Build Angular list/details pages.

4. **Help requests: write side**
   - Implement create + edit + delete rules (owner-only, open-only).
   - Build Angular create/edit/delete flows.

5. **Volunteer/offer flow**
   - Implement volunteer assignment endpoint and in-progress transition.
   - Build Angular action on details page.

6. **Completion + points rule**
   - Implement owner completion endpoint.
   - Award 10 points to helper (single atomic transaction).

7. **Profile and activity pages**
   - Implement `/me` stats and filtered “my requests / my helping” queries.
   - Build Angular profile pages.

8. **MVP hardening**
   - Validation + global exception handler + consistent error responses.
   - Authorization tests for ownership/state transitions.
   - Improve UX states (loading/error/empty).

---

## Legacy-to-New Mapping Notes

- Legacy concept **HelpRequest + Volunteer** can be modeled either as:
  - direct `helperId` on request (legacy-compatible), or
  - separate `offer` records with one accepted offer (more extensible).
- For rapid MVP parity, keep direct assignment semantics while implementing within `offers` module boundaries.
- Legacy ASP.NET Identity schema should not be copied directly; only business-relevant fields/flows should be ported.
