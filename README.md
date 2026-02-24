
# Warehouse Management System — Backend

Backend for a production-style Warehouse Management System — built with Spring Boot 3, MySQL, JWT authentication, role-based authorization (ADMIN / WAREHOUSE_MANAGER / SALES_MANAGER), transactional stock management, Swagger/OpenAPI, and Docker support.

---

## Status

This `main` branch intentionally stays lightweight during active development.  
All source code and comprehensive documentation live in the `development` branch.

---

## Quick Links

-  Development branch: [development](../../tree/development)
-  Full documentation: see `README.md` on the `development` branch

---

## System Overview

This system manages warehouse inventory with secure role-based access control and transactional stock operations.

### User Roles

- **ADMIN** — Full system control
- **WAREHOUSE_MANAGER** — Manages inventory and stock updates
- **SALES_MANAGER** — Performs outgoing stock transactions

---

## Core Features

- JWT-based authentication (stateless security)
- Role-based endpoint restrictions
- Product management
- Stock increase transactions
- Stock decrease transactions
- Inventory tracking
- Transaction history
- Swagger API documentation
- Dockerized environment support

---

## Business Logic Highlights

- All stock operations are handled inside database transactions
- Inventory cannot go below zero
- Role-based access prevents unauthorized stock manipulation
- Clean separation of controllers, services, and repositories
- Stock updates are atomic and consistent using transactional boundaries
- Sales operations automatically validate available inventory before deduction

---

## Architecture

The system follows a layered architecture:

- Entity (domain models)
- Controllers (REST API layer)
- Services (business logic & transaction management)
- Repositories (data access layer)
- DTO & Mapper layer (data transformation)
- Security layer (JWT filter & role-based authorization)
- Global exception handling

## How to Use This Repository

1. Switch to the `development` branch to view and run the full project.
2. Follow the detailed README there for:
    - Environment configuration
    - Local run instructions
    - Docker setup
    - API endpoints & Swagger documentation
    - JWT authentication flow

Pull requests should target the `development` branch.  
Stable milestones may be merged into `main`.

---

## License

Licensed under the Apache License 2.0 — see the `LICENSE` file for details.