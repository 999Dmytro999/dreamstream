# ADR 0001: Start With a Modular Monolith

## Status
Accepted

## Context

DreamStream is being rebuilt from the older HelpEachOther application. The MVP needs to stay simple, easy to validate, and easy to evolve while the domain is still taking shape.

## Decision

We will build DreamStream as a modular monolith in Spring Boot.

The backend will stay in one deployable application, with modules separated by package boundaries such as:

- `auth`
- `users`
- `helprequests`
- `offers`
- `points`
- `notifications`
- `admin`
- `common`

We will not introduce microservices at this stage.

## Consequences

- The MVP stays easier to reason about and test.
- Shared code stays in one place, which keeps the first release moving.
- Architectural boundaries still matter, so modules should not become a single flat package.
- If the system later needs service decomposition, the package structure should make that migration easier.
