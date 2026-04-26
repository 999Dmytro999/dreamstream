# DreamStream Product Requirements

## Product Name

DreamStream

## Legacy Project Name

HelpEachOther

## Product Vision

DreamStream helps people ask for help and allows others to offer support.

The platform is focused on real human help, community, and giving people a simple way to move closer to their goals.

## Core Problem

Many people need help, but they do not know where to ask.

At the same time, many people are willing to help but do not have a simple platform where they can find real requests.

DreamStream connects these two sides.

## Target Users

### People Requesting Help

Users who need help with things like:

- education
- technology
- job search
- transportation
- food
- housing
- documents
- advice
- small personal goals

### People Offering Help

Users who want to:

- help others
- volunteer
- share skills
- support people in their community

### Admins

Admins manage:

- users
- requests
- inappropriate content
- completed requests
- platform safety

## MVP Scope

### Must Have

- User registration
- User login
- User profile
- Create help request
- View list of help requests
- View help request details
- Edit own help request
- Mark own request as completed or closed
- Allow another user to offer help
- Basic request statuses

### Should Have

- Categories for help requests
- Location field
- Basic search or filtering
- My requests page
- My offers page
- Basic admin view

### Later

- Soul points or good deed points
- Notifications
- Messaging
- Donations
- Public profiles
- Request verification
- Organization accounts
- Matching algorithm
- Microservices

## Main Entities

### User

A user should have:

- id
- firstName
- lastName
- email
- passwordHash
- role
- createdAt
- updatedAt

### Help Request

A help request should have:

- id
- title
- description
- category
- location
- status
- createdBy
- createdAt
- updatedAt

Possible categories:

- EDUCATION
- TECHNOLOGY
- FOOD
- HOUSING
- TRANSPORTATION
- JOB_SEARCH
- DOCUMENTS
- OTHER

Possible statuses:

- OPEN
- IN_PROGRESS
- COMPLETED
- CANCELLED

### Help Offer

A help offer should have:

- id
- helpRequest
- offeredBy
- message
- status
- createdAt
- updatedAt

Possible statuses:

- PENDING
- ACCEPTED
- DECLINED
- CANCELLED

## Main Pages

### Public Pages

- Home page
- About page
- Help requests list
- Help request details
- Login
- Register

### Authenticated User Pages

- Profile
- Create request
- My requests
- My offers
- Edit request

### Admin Pages

- Admin dashboard
- Manage users
- Manage requests

## Success Criteria for MVP

The MVP is successful when a user can:

1. Register an account.
2. Log in.
3. Create a help request.
4. See their request in the public list.
5. Open the request details page.
6. Another user can offer help.
7. The request owner can mark the request as completed.

## Non-Goals for First MVP

Do not build these in the first version:

- payment processing
- complex chat
- microservices
- mobile app
- AI matching
- advanced admin analytics