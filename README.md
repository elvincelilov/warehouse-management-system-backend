# Warehouse Management System — Backend

Spring Boot 3 · Java 17 · MySQL 8 · Docker · Docker Compose · AWS EC2 · JWT Security · Swagger

A production-ready Warehouse Management backend system built with Spring Boot and deployed using Docker containers locally or on AWS EC2.

This project demonstrates real-world warehouse inventory management with secure authentication, transactional stock control, and containerized deployment.

---

##  What This Project Demonstrates

- Stateless JWT authentication (Access Token)
- Clean layered architecture
- Role-based authorization (ADMIN / WAREHOUSE_MANAGER / SALES_MANAGER)
- Real inventory stock control (IN / OUT transaction logic)
- Dockerized MySQL + Spring Boot setup
- Environment-based configuration (12-factor style)
- AWS EC2 deployment readiness
- Swagger-documented REST API
- Postman-tested endpoints

---

## Production Architecture (Docker-Based)

### Runtime Structure
Host (EC2 or Local)
│
├── mysql_db (MySQL 8.0)
│ ├── warehouse_db
│ └── Persistent volume (mysql_data)
│
└── spring_app (Spring Boot 3)
└── Runs fat JAR (Java 17 JRE)

Internal Docker connection:
- jdbc:mysql://mysql:3306/warehouse_db

External access:
- http://<HOST_IP>:9099

---

##  Docker Configuration

### docker-compose.yml

- MySQL container: `mysql_db`
- App container: `spring_app`
- Database: `warehouse_db`
- Persistent MySQL volume
- Environment variables passed securely

### Port Mapping

| Service | Internal Port | External Port |
|----------|--------------|---------------|
| MySQL    | 3306        | 3308          |
| App      | 9099        | 9099          |

---

##  Dockerfile (Multi-Stage Build)

### Stage 1 — Build

- Maven 3.9 + Temurin 17
- Dependency caching
- Fat JAR packaging

### Stage 2 — Runtime

- Lightweight JRE image
- Copies compiled JAR
- Exposes port 9099
- Runs:

java -jar app.jar 

Benefits:

- Smaller image size
- Faster startup
- Clean separation of build/runtime

---

##  Environment-Based Configuration

- Application uses environment variables:
- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USERNAME
- SPRING_DATASOURCE_PASSWORD
- SERVER_PORT

`application.properties`: 

- spring.datasource.url=${SPRING_DATASOURCE_URL}
- spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
- spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
- server.port=${SERVER_PORT}

Advantages:

- No hardcoded credentials
- Easy EC2 deployment
- Clean 12-factor configuration style

---

##  Security Model

### Authentication

- JWT-based stateless authentication
- Access token issued on login
- Required for all protected endpoints
- Sent via:

Authorization: Bearer <access_token> 

(No refresh token yet — planned improvement.)

---

##  System Roles

###  ADMIN
- Full system access
- Manage users
- Access reports
- Unrestricted endpoints

###  WAREHOUSE_MANAGER
- Manage products
- Perform IN transactions
- Manage suppliers

###  SALES_MANAGER
- Manage customers
- Perform OUT transactions
- View sales data

Role restrictions enforced via Spring Security configuration.

---

##  Core Business Logic

### Product Management

- Create / Update / Delete
- Pagination & filtering
- Real-time stock tracking

---

###  Transaction System (Critical Logic)

####  IN Transaction
- Increases stock
- Records transaction
- Linked to responsible user

####  OUT Transaction
- Decreases stock
- Validates available stock
- Prevents negative inventory
- Records transaction

**Business Rule:**

Stock cannot go below zero.

All stock operations are handled inside database transactions to ensure atomicity and consistency.

---

##  Reporting

- Transaction listing
- Aggregated reporting endpoints
- Role-based visibility control

---

##  API Documentation

Swagger UI (Local): http://localhost:9099/swagger-ui/index.html 

On EC2:  http://<EC2_PUBLIC_IP>:9099/swagger-ui/index.html

---

##  How to Run (Local)

### Clone repository


git clone <repo_url>

cd warehouse_management


###  Run with Docker Compose

docker-compose up --build


###  Access API


http://localhost:9099


Stop:  docker-compose down


---

##  Deploying to AWS EC2

### ️ Install Docker


sudo apt update
sudo apt install docker.io docker-compose


###  Upload project


scp -r warehouse_management ubuntu@<EC2_IP>:~


###  Run


docker-compose up --build -d


###  Verify


docker ps
curl -i http://<EC2_IP>:9099


`401 Unauthorized` → expected (JWT protection active).

---

##  Database Details

- MySQL 8.0
- Persistent volume: `mysql_data`
- Hibernate: `ddl-auto=update`
- SQL logging enabled
- Automatic schema generation

---

##  API Testing

- Swagger UI
- Postman

Documentation folder will include:

/docs/swagger/
/docs/postman/

Screenshots:
- Login flow
- Role-based restrictions
- IN transaction
- OUT transaction
- Reporting endpoints

---

##  Branch Strategy

- `main` → Stable & production-ready branch
- `development` → Active development branch

---

##  Future Improvements

- Refresh token implementation
- HTTPS via NGINX reverse proxy
- CI/CD pipeline
- Redis caching
- Flyway migrations
- Cloud-based secret management

---

##  License

Apache License 2.0