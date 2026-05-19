# DreamStream Design System

## Purpose

This document defines the visual design direction for DreamStream so future frontend work stays consistent across the homepage and the wider application.

## Brand Personality

DreamStream should feel:

- warm
- joyful
- trustworthy
- human-centered
- nonprofit/community focused
- professional without feeling corporate or cold

The UI should communicate care, dignity, optimism, and practical usefulness. Visual choices should feel inviting to people asking for help and reassuring to people offering it.

## Color Palette

Use a soft, uplifting palette anchored by accessible contrast.

| Token | Role | Suggested Color |
| --- | --- | --- |
| `primary-blue` | primary brand color, major CTAs, navigation emphasis | `#2F6FED` |
| `teal` | supporting accents, icons, secondary highlights | `#1FA7A0` |
| `coral-orange` | warmth, emphasis, selective CTA moments | `#F47C57` |
| `warm-cream` | hero backgrounds, soft card fills, section warmth | `#FFF6EC` |
| `mint` | positive states, soft callouts, friendly highlights | `#DDF4EA` |
| `navy-text` | primary text color | `#1F2A44` |
| `soft-border` | inputs, card outlines, dividers | `#D9E2EC` |
| `light-background` | app background, large page surfaces | `#F8FBFD` |

### Color Usage Guidance

- Keep `navy-text` as the primary reading color.
- Use `primary-blue` for the strongest actions and important links.
- Use `teal` and `mint` to reinforce community, calm, and positivity.
- Use `coral-orange` sparingly for warmth and high-importance emphasis.
- Use `warm-cream` and `light-background` to avoid a stark white, overly clinical feel.
- Borders should stay soft and understated rather than heavy or dark.

## Typography

- Use `Inter` if available, or the existing project font if it is already established and close in character.
- Headings should feel clear, friendly, and confident rather than formal or compressed.
- Body text should prioritize readability and comfortable spacing.

### Heading Scale

- `h1`: 40-48px, weight 700, line-height 1.1-1.2
- `h2`: 32-36px, weight 700, line-height 1.15-1.25
- `h3`: 24-28px, weight 600-700, line-height 1.2-1.3
- `h4`: 20-22px, weight 600, line-height 1.3

### Body Scale

- Large body: 18px, line-height 1.6
- Default body: 16px, line-height 1.5-1.7
- Small/supporting text: 14px, line-height 1.4-1.6
- Caption/meta text: 12-13px, line-height 1.4

## Border Radius Rules

- Small interactive elements: 10px
- Inputs and buttons: 12px
- Standard cards and panels: 16px
- Large hero cards or feature blocks: 20-24px
- Fully pill-shaped badges and compact chips: `999px`

Rounded corners should feel soft and modern, but still disciplined and consistent.

## Shadow Rules

Use subtle, layered shadows. Avoid heavy, dramatic elevation.

- Level 1: soft hover or resting card shadow
- Level 2: stronger panel or dropdown shadow
- Level 3: featured CTA card or hero support card shadow

Suggested direction:

- low blur, low opacity, cool-neutral shadow color
- pair shadows with soft borders instead of relying on shadow alone
- increase shadow slightly on hover, never sharply

## Spacing Rules

Use a consistent spacing scale built around 4px increments.

- `4px`: tight icon/text adjustments
- `8px`: small gaps
- `12px`: compact component spacing
- `16px`: standard internal spacing
- `24px`: card padding or grouped layout spacing
- `32px`: section internals
- `48px`: desktop section spacing
- `64px` and `80px`: large page sections and hero spacing

Layout rhythm should feel airy and calm, especially on the homepage.

## Button Styles

### Primary Button

- Background: `primary-blue`
- Text: white
- Radius: 12px
- Weight: 600
- Hover: slightly darker blue or stronger shadow
- Use for main calls to action such as creating a request or getting started

### Secondary Button

- Background: white or `warm-cream`
- Text: `navy-text` or `primary-blue`
- Border: `1px solid soft-border`
- Radius: 12px
- Hover: soft tinted background, slightly elevated border/shadow treatment

### Ghost / Link Button

- Background: transparent
- Text: `primary-blue` or `navy-text`
- Minimal chrome
- Clear hover and focus states
- Use for low-emphasis actions in navigation or cards

### CTA Card Button

- Larger visual weight than standard buttons
- Can use `coral-orange`, `teal`, or `primary-blue` depending on card context
- Designed to sit comfortably inside promotional or task-oriented cards
- Must still meet contrast requirements

## Card Styles

### Stat Cards

- Friendly, concise summary cards
- Rounded corners: 16-20px
- Light surface or soft tinted background
- Minimal clutter
- Large number, short label, optional icon/accent shape

### Content Panels

- Used for grouped homepage sections and dashboard-style containers
- White or very light tinted background
- Soft border and subtle shadow
- Generous padding and clear section headings

### Request List Rows

- Clean, readable, scan-friendly
- Strong hierarchy for title, status, and metadata
- Hover state should suggest clickability without becoming noisy
- Use soft separators or card rows depending on page context

## Badge Styles

All badges should be compact, high-contrast, and easy to scan.

### `OPEN`

- Background: very light blue or mint tint
- Text: darker teal or blue
- Tone: active, hopeful, available

### `COMPLETED`

- Background: soft mint
- Text: deep teal or green
- Tone: positive, resolved, reassuring

### `PENDING`

- Background: soft warm cream or pale coral tint
- Text: darker coral, amber, or navy
- Tone: in progress, waiting, attention needed

## Accessibility Rules

- Use semantic HTML for landmarks, headings, lists, buttons, and links.
- Maintain accessible text contrast for all primary interactions and status labels.
- Provide visible focus states for keyboard users on links, buttons, form fields, and custom controls.
- Do not remove outlines unless a stronger accessible replacement is provided.
- Add alt text for meaningful images.
- Decorative images should use empty alt text or CSS backgrounds.
- Ensure all links and buttons are keyboard friendly and have clear hit areas.
- Avoid conveying meaning by color alone; pair color with text, icons, or labels.
- Preserve responsive readability with adequate font sizes and spacing on small screens.
