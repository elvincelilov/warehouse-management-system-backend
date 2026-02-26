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

##  Authorization Matrix (Role-Based Access Control)

The system implements strict role-based authorization using Spring Security.

All endpoints are protected and accessible only according to assigned roles.

---

###  ADMIN

Full system authority.

Permissions:

- Create new users (no public registration available)
- Create suppliers
- View supplier list
- Create products
- View product list
- View product by ID
- Update products
- Delete products
- Create customers
- Update customers
- Delete customers
- View customer list
- View customer by ID
- Perform IN transactions
- Perform OUT transactions
- Generate bills
- Access transaction reports

---

###  WAREHOUSE_MANAGER

Inventory and supplier management role.

Permissions:

- View suppliers
- Create products
- View product list
- View product by ID
- Update products
- Delete products
- Update customers
- Delete customers
- View customer list
- View customer by ID
- Perform IN transactions
- Perform OUT transactions
- Generate bills
- Access transaction reports

---

###  SALES_MANAGER

Sales-focused role.

Permissions:

- View product list
- View product by ID
- Update products
- Delete products
- Perform OUT transactions
- Perform IN transactions
- Generate bills

(No access to supplier creation, customer creation, or transaction reports.)

---

###  USER Password Management

All authenticated users can:

- Change their own password securely.

---

##  Detailed Business Capabilities

###  User Management

- No public registration endpoint.
- Users are created only by ADMIN.
- Role is assigned during user creation.
- JWT-based authentication is enforced.

---

###  Supplier Management

- ADMIN can create suppliers.
- ADMIN and WAREHOUSE_MANAGER can view suppliers.
- Fully secured via role-based access control.

---

###  Product Management

- ADMIN creates products.
- ADMIN, WAREHOUSE_MANAGER, SALES_MANAGER can:
    - View all products
    - View product by ID
    - Update products
    - Delete products

Product stock is automatically updated during transaction operations.

---

###  Customer Management

- Only ADMIN can create customers.
- ADMIN and WAREHOUSE_MANAGER can:
    - Update customers
    - Delete customers
    - View customer list
    - View customer by ID

---

###  Transaction System (IN / OUT)

Supported by:

- ADMIN
- WAREHOUSE_MANAGER
- SALES_MANAGER

Capabilities:

- Perform IN (stock increase)
- Perform OUT (stock decrease)
- Generate bill number automatically
- Prevent negative inventory
- Calculate total, tax, paid amount, and balance
- Store transaction items
- Maintain atomic database consistency

---

###  Transaction Reports

Accessible only by:

- ADMIN
- WAREHOUSE_MANAGER

Reports include:

- Full transaction listing
- Aggregated financial overview
- Inventory movement tracking

---

##  Billing & Transaction Flow (Financial Engine)

The system includes a structured transaction engine with bill generation capability.

All transaction operations produce a detailed bill response containing:

- Bill number
- Transaction date
- Party name (Supplier or Customer)
- Item list
- Total amount
- Tax
- Grand total

---

###  Bill Response Structure

Each successful IN or OUT transaction returns a structured bill object:

Example Response:

{
"billNumber": "BILL-1708965212458",
"date": "2026-02-26T14:33:21",
"partyName": "ABC Supplier",
"items": [
{
"productName": "Laptop",
"quantity": 2,
"unitPrice": 500.00,
"lineTotal": 1000.00
}
],
"totalAmount": 1000.00,
"tax": 50.00,
"grandTotal": 1050.00
}

---

###  Transaction Types

The system supports two transaction types:

IN  → Stock Increase  
OUT → Stock Decrease

Both operations:

- Update inventory automatically
- Calculate financial totals
- Generate unique bill numbers
- Store transaction items
- Maintain balance tracking

---

###  Database Structure (Transaction Layer)

Core Tables:

- transactions
- transaction_items
- products
- suppliers
- customers

Each transaction:

- Has multiple transaction_items
- Is linked to either supplier (IN) or customer (OUT)
- Stores tax, paid amount, and balance
- Records transaction date and bill number

---

###  Financial Calculation Logic

The system follows strict financial consistency rules:

lineTotal = unitPrice × quantity

totalAmount = sum(lineTotals)

grandTotal = totalAmount + tax

balance = (totalAmount + tax) - paid

---

###  Transaction Report System

Accessible by:

- ADMIN
- WAREHOUSE_MANAGER

Report capabilities:

- List all transactions
- Filter by type (IN / OUT)
- Financial summary overview
- Inventory movement tracking
- Balance monitoring

---

###  Consistency & Atomicity

All transaction operations are wrapped in database transactions.

If any of the following occurs:

- Product not found
- Supplier not found
- Customer not found
- Insufficient stock

The entire operation is rolled back automatically.

This ensures:

- No partial stock updates
- No inconsistent financial records
- No negative inventory

##  Security Guarantees

- All endpoints require JWT authentication.
- Role-based restrictions enforced at controller level.
- Unauthorized access returns 403 Forbidden.
- Invalid authentication returns 401 Unauthorized.
- All stock operations are transactional.
- No negative stock allowed.

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


##  Swagger UI Screenshots

The following screenshots demonstrate live API execution using Swagger UI.

### Authentication

#### Login
![Swagger Login](docs/swagger/warehouse%20login.png)

#### Main Swagger Page
![Swagger Main](docs/swagger/warehouse_main.png)

---

###  ADMIN Operations

#### Create Product
![Admin Create Product](docs/swagger/warehouse_admin_create_product.png)

#### Create Supplier
![Admin Create Supplier](docs/swagger/warehouse_admin_create_supplier.png)

#### Create User
![Admin Create User](docs/swagger/warehouse_admin_create_user.png)

#### Create Customer
![Admin Create Customer](docs/swagger/warehouse_admin_customerCreate.png)

#### View Suppliers
![Admin Get Suppliers](docs/swagger/warehouse_admin_getsuppliers.png)

#### View Sales Managers
![Admin See Sales Manager](docs/swagger/warehouse_admin_see_salesManager.png)

#### View Warehouse Managers
![Admin See Warehouse Manager](docs/swagger/warehouse_admin_see_warehouseManager.png)

---

###  Product Operations

#### Get Product List
![Get Product List](docs/swagger/warehouse_getproductlist.png)

#### Get Product By ID
![Get Product By ID](docs/swagger/warehouse_getproductlist_byID.png)

#### Delete Product
![Delete Product](docs/swagger/warehouse_product_delete.png)

#### Update Product
![Update Product](docs/swagger/warehouse_product_update.png)

---

###  User Operations

#### Change Password
![Change Password](docs/swagger/warehouse_user_changePassword.png)

---

###  Customer Operations

#### Delete Customer
![Delete Customer](docs/swagger/wh_deleteCustomer.png)

#### Get Customers
![Get Customers](docs/swagger/wh_getcustomers.png)

#### Get Customer By ID
![Get Customer By ID](docs/swagger/wh_getcustomers_byId.png)

#### Update Customer
![Update Customer](docs/swagger/wh_updateCustomer.png)

---

###  Transaction System

#### Transaction IN (Stock Increase)
![Transaction IN](docs/swagger/wh_transactionIn.png)

#### Transaction OUT (Stock Decrease)
![Transaction OUT](docs/swagger/wh_transactionOUT.png)

#### Get Transactions
![Get Transactions](docs/swagger/wh_getTransactions.png)

#### Generate Bill
![Bill Response](docs/swagger/wh_getBill.png)

---

##  Postman Testing Screenshots

All endpoints were additionally tested using Postman collections.

###  Authentication & JWT
![Postman Login](docs/postman/warehouse_login_p.png)

---

###  Admin Operations (Postman)

#### Create Product
![Postman Create Product](docs/postman/warehouse_admin_create_product_p.png)

#### Create Supplier
![Postman Create Supplier](docs/postman/warehouse_admin_create_supplier_p.png)

#### Create User
![Postman Create User](docs/postman/warehouse_admin_create_user_p.png)

#### Create Customer
![Postman Create Customer](docs/postman/warehouse_admin_customerCreate_p.png)

#### View Suppliers
![Postman Get Suppliers](docs/postman/warehouse_admin_getsuppliers_p.png)

#### View Sales Manager
![Postman See Sales Manager](docs/postman/warehouse_admin_see_salesManager_p.png)

#### View Warehouse Manager
![Postman See Warehouse Manager](docs/postman/warehouse_admin_see_warehouseManager_p.png)

---

###  Product Operations (Postman)

#### Get Product List
![Postman Product List](docs/postman/warehouse_getproductlist_p.png)

#### Get Product By ID
![Postman Product By ID](docs/postman/warehouse_getproductlist_byID_p.png)

#### Delete Product
![Postman Delete Product](docs/postman/warehouse_product_delete_p.png)

#### Update Product
![Postman Update Product](docs/postman/warehouse_product_update_p.png)

---

###  Customer Operations (Postman)

#### Delete Customer
![Postman Delete Customer](docs/postman/wh_deleteCustomer_p.png)

#### Get Customers
![Postman Get Customers](docs/postman/wh_getcustomers_p.png)

#### Get Customer By ID
![Postman Get Customer By ID](docs/postman/wh_getcustomers_byId_p.png)

#### Update Customer
![Postman Update Customer](docs/postman/wh_updateCustomer_p.png)

---

###  Transaction System (Postman)

#### Transaction IN
![Postman Transaction IN](docs/postman/wh_transactionIn_p.png)

#### Transaction OUT
![Postman Transaction OUT](docs/postman/wh_transactionOUT_p.png)

#### Get Transactions
![Postman Get Transactions](docs/postman/wh_getTransactions_p.png)

#### Bill Generation
![Postman Bill](docs/postman/wh_getBill_p.png)

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