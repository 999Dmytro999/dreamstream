# Homepage Implementation Plan

## Purpose

This plan breaks the DreamStream homepage redesign into small, safe tasks so Codex work can stay focused, verifiable, and low risk.

## Phase 1: Frontend Analysis

- Analyze the current Angular frontend structure.
- Identify the homepage component, shared layout files, header/navigation files, and any existing homepage styles.
- Confirm where dynamic homepage data is sourced and rendered.
- Do not change code yet.

## Phase 2: Style Foundation

- Create or update `home-page.component.scss`.
- Add design tokens as SCSS variables if appropriate for the current frontend structure.
- Keep the build passing after any style file changes.
- Avoid broad refactors outside the homepage scope.

## Phase 3: Header and Navigation Redesign

- Redesign the top navigation and header styling.
- Preserve existing routing behavior.
- Preserve current user state handling.
- Preserve logout behavior.
- Keep auth-aware conditions intact.

## Phase 4: Hero Section Redesign

- Redesign the homepage hero section.
- Add CTA cards for key actions.
- Use a placeholder CSS illustration if final art assets are not yet available.
- Keep the section implemented in semantic HTML and SCSS.

## Phase 5: Stats Card Redesign

- Redesign the homepage stats cards.
- Keep all existing dynamic values.
- Improve hierarchy, spacing, and responsiveness without hardcoding data.

## Phase 6: Action Panels and Recent Requests

- Redesign the “What You Can Do” area.
- Redesign the Recent Requests panel.
- Preserve `loading` behavior.
- Preserve the empty state.
- Preserve dynamic `recentRequests` rendering.
- Preserve request status styling through existing logic such as `statusClass(request.status)`.

## Phase 7: Responsive Layout

- Add responsive mobile and tablet behavior.
- Ensure the homepage works comfortably across small, medium, and desktop breakpoints.
- Re-check navigation, hero layout, cards, and request rows at each size.

## Phase 8: Validation and Handoff

- Run `npm run build`.
- Fix TypeScript, Angular, and SCSS errors.
- Confirm no existing functionality was removed.
- Provide a summary of changed files and what each change accomplished.

## Working Style Notes for Future Codex Tasks

- Keep each implementation task narrow and verifiable.
- Prefer one phase per task when practical.
- Include validation steps and expected outcomes in the prompt.
- Avoid having multiple Codex tasks edit the same homepage files at the same time.
