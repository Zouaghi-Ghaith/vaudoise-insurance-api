**Backend Vaudoise - Insurance Client & Contract Management API**



Prerequisites
-------------
Java 17
Maven 
Docker Desktop (for PostgreSQL 17)
Git

QUICK START:

Step 1: Clone the Repository:
-------------------------------
Open Git Bash (or terminal) and run:
git clone https://github.com/Zouaghi-Ghaith/vaudoise-insurance-api.git

Step 2: Start PostgreSQL with Docker
-------------------------------------
First Time Setup - Create and run PostgreSQL container:

docker run --name postgres-vaudoise \
  -e POSTGRES_USER=Vaudoise \
  -e POSTGRES_PASSWORD=Vaudoise \
  -e POSTGRES_DB=postgres \
  -p 5432:5432 \
  -d postgres:17

Verify the container is running:
docker ps
You should see postgres-vaudoise in the list.


For Subsequent Uses:
Start the existing container:
docker start postgres-vaudoise

Stop the container when done:
docker stop postgres-vaudoise

(Docker is simple  and fast to use but if you don't want to use Docker, you can just install posgreSQL on your PC then create database named postgres)
Step 3: Build the Project
-------------------------
Run our project vaudoise-insurance-api that you cloned from git in an editor 
The API will start on http://localhost:8080

API Endpoints:
--------------

Client Management
*****************
Method                    Endpoint                                            Description 
------                    -------------------------------------------         ------------------------------------------------------
POST                      /api/clients                                        Create a new client (PERSON or COMPANY)
GET                       /api/clients/{id}                                   Get client details by ID
PUT                       /api/clients/{id}                                   Update client (name, phone, email only)
DELETE                    /api/clients/{id}                                   Delete client and close all contracts





Contract Management
*******************
Method                    Endpoint                                            Description 
------                    -------------------------------------------         ------------------------------------------------------
POST                      /api/clients/{clientId}/contracts                   Create a contract for a client
GET                       /api/clients/{clientId}/contracts                   Get active contracts (optional: ?updatedSince=ISO8601)
GET                       /api/clients/{clientId}/contracts/sum-active        Get sum of all active contract costs
PUT                       /api/contracts/{id}                                 Update contract details



-----> Test the API using Postman


Architecture & Design 
**********************************
* Layered Architecture: The application follows a classic 3-tier architecture with Controllers (REST API), Services (business logic), 
and Repositories (data access), ensuring clear separation of concerns and maintainability.
* DTOs for API Contract: Data Transfer Objects decouple the API layer from internal entities, preventing over-exposure of internal
structure and enabling independent evolution of API and database schemas.
* Validation Strategy: Jakarta Bean Validation annotations on DTOs ensure data integrity at the API boundary. Cross-field 
validations (e.g., date ranges) use @AssertTrue methods for complex business rules.
* Soft Delete Pattern: Client deletion sets contract endDates to current date rather than hard deleting, preserving historical 
data for audit and compliance requirements.
* Performance Optimization: Database-level aggregation (SUM query) for active contracts sum avoids loading unnecessary data. 
* Composite indexes on client_id + end_date optimize active contract lookups.
* Audit Trail: Internal updateDate timestamp tracks modifications without API exposure, supporting synchronization 
scenarios via the updatedSince filter parameter.



