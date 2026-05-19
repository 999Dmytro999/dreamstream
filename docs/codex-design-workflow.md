# Codex Design Workflow

## Purpose

This document defines how future Codex tasks should be written for DreamStream frontend design and UI implementation work.

## Workflow Rules

- Keep tasks small.
- Ask Codex to plan first when the task is large.
- Always preserve existing functionality.
- Always run the build after changes.
- Do not let multiple Codex tasks modify the same files at the same time.
- Use the design docs as the source of truth.
- If implementing from Figma, use the selected frame URL and preserve dynamic Angular behavior.
- Prefer componentized, maintainable Angular and SCSS over pixel-perfect hacks.

## Recommended Task Structure

When writing a frontend task for Codex, include:

- the target component or page
- the specific phase or scope of work
- functionality that must remain unchanged
- required routes, bindings, and auth behavior to preserve
- validation steps such as `npm run build`
- a request for a summary of changed files

## Example Guidance

- Good task: redesign only the homepage hero and CTA cards while preserving bindings, routes, and build health.
- Better large-task approach: first ask Codex to inspect the homepage structure and produce a short plan, then implement one phase at a time.

## Figma-Specific Expectations

- Reference the exact selected frame URL when implementing from Figma.
- Treat Figma as layout and style guidance, not as a static screenshot replacement.
- Preserve Angular template logic, real routing, dynamic lists, and auth-aware behavior.
- Reconcile the design with the project design-system and asset rules before coding.
