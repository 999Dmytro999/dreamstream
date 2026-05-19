# Frontend Design Handoff

## Purpose

This document explains how future DreamStream designs should be translated into Angular code safely and maintainably.

## Core Implementation Principles

- Do not implement the homepage or any other screen as one static image.
- UI must be built with real HTML and SCSS.
- Illustrations, decorative backgrounds, and supporting artwork may be added as PNG or SVG assets.
- Dynamic data must remain dynamic and must continue to come from Angular bindings and component state.
- Prefer maintainable Angular templates and reusable styling over one-off visual hacks.

## Angular Behavior That Must Be Preserved

The following bindings must remain functional when redesigning the homepage:

- `openCount`
- `completedCount`
- `requests.length`
- `recentRequests`
- `loading`
- `statusClass(request.status)`

Future redesign work should restyle or reorganize these outputs without converting them into hardcoded text.

## Routing That Must Be Preserved

The following router links must continue working as part of the redesign:

- `/requests`
- `/requests/new`
- `/register`
- `/login`
- `/requests/:id`

Design changes may update layout, labels, or visual treatment, but not the route destinations or their Angular usage.

## Header and Auth Requirements

- The header must keep the current user state behavior.
- Logout behavior must remain intact.
- Auth-aware navigation should still respond correctly to signed-in and signed-out states.
- Do not simplify the header into a static mock that loses conditional rendering.

## Asset Usage

Use local assets from:

- `frontend/src/assets/images/`
- `frontend/src/assets/icons/`

Do not hardcode remote image URLs into the application.

## Figma and Reference Translation Guidance

When implementing from a Figma file or reference design:

- treat the design as structure and styling guidance, not as a screenshot to reproduce literally
- extract layout sections, spacing, colors, typography, cards, badges, and interaction patterns
- map those patterns onto Angular components, templates, and SCSS
- preserve all dynamic bindings, router links, auth conditions, and loading states
- use reusable classes, variables, and component structure where appropriate

## Safe Delivery Expectations

- Keep templates readable.
- Keep styles organized by section and intent.
- Preserve existing component logic unless a design task explicitly requires behavior changes.
- Validate that the resulting UI still builds and behaves correctly after styling work.
