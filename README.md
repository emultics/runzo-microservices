# Runzo Microservices

A distributed microservices architecture built with Spring Boot and deployed using Docker, demonstrating modern microservices patterns including service discovery, polyglot persistence, event-driven communication, and AI-powered recommendations.

## Overview

This project implements a complete microservices ecosystem with:

- **User Service**: Spring Boot application for user management and authentication [1](#3-0)
- **Activity Service**: MongoDB-based service for activity tracking and analytics [2](#3-1)
- **AI Service**: WebFlux-based service for AI recommendations and machine learning capabilities [3](#3-2)
- **Eureka Server**: Service discovery and registration
- **Common Module**: Shared utilities and configurations [4](#3-3)

### Architecture Components

| Component | Technology | Port | Purpose |  
|-----------|------------|------|---------|  
| User Service | Spring Boot + PostgreSQL | 8081 | User management and authentication |  
| Activity Service | Spring Boot + MongoDB | Dynamic | Activity tracking and analytics |  
| AI Service | Spring Boot WebFlux + MongoDB | Dynamic | AI recommendations and ML processing |  
| Eureka Server | Spring Cloud Eureka | 8761 | Service discovery and registration |  
| Common Module | Shared Library | N/A | Cross-cutting concerns and utilities |  

## Prerequisites [5](#3-4)

- PowerShell Core (pwsh) installed
- Internet connection for Docker installation
- `.env` file present in project root with required keys
- `setup-compose.yml` file in project root

## Quick Start

### 1. Clone and Setup [6](#3-5)

```bash  
git clone <your-repo-url>  
cd <your-repo>
```
### 2. Run Setup Script README.md:20-27
```declarative
# Using PowerShell Core  [header-2](#header-2)
pwsh ./setup-docker.ps1

# On Windows (PowerShell 5.1)  [header-3](#header-3)
powershell -ExecutionPolicy Bypass -File .\setup-docker.ps1
```
### 3. Verify Installation README.md:32-36
```declarative
docker ps
```
All containers should show "Up" status.

### Services
#### User Service
The user service handles user registration, authentication, and profile management. `UserService.java:8-14`

#### Key Features:

- User registration and authentication
- Profile retrieval by email, ID, or phone `UserController.java:34-37`
- User existence validation `UserController.java:60-62`
- Standardized API responses using common module `UserController.java:3-4`

### REST Endpoints:

- `POST /api/user` - User registration
- `GET /api/user/search` - User lookup by id, email, or phone
- `GET /api/user/{userId}/validate` - User existence check

### Activity Service
The activity service manages activity tracking and analytics using MongoDB 
for data persistence.`ActivityHandlerService.java:14-21`

#### Key Features:

- Activity tracking and logging
- Activity retrieval by ID `ActivityHandlerService.java:23-29`
- MongoDB integration for scalable data storage
- Standardized API responses

### AI Service
The AI service provides intelligent recommendations and machine learning 
capabilities using reactive programming patterns. `pom.xml:16`
#### Key Features:

- Reactive WebFlux architecture for high-performance processing
- MongoDB integration for AI model data storage
- Eureka client for service discovery
- Shared common module integration

### Infrastructure
#### Database Layer
- `PostgreSQL:` User data and ALService data `setup-compose.yml:41-51`
- `MongoDB:` Activity tracking data `setup-compose.yml:53-63`
- `Redis:` Caching layer `setup-compose.yml:110-114`

### Message Infrastructure
- `Kafka`: Event streaming `setup-compose.yml:13-28`
- `Zookeeper`: Kafka coordination `setup-compose.yml:4-11`

### Administrative Interfaces
- `PgAdmin`: PostgreSQL administration `setup-compose.yml:96-108`
- `Mongo Express`: MongoDB administration `setup-compose.yml:65-82`
- `Redis Insight`: Redis monitoring `setup-compose.yml:116-124`
- `Kafka UI:` Kafka cluster management `setup-compose.yml:30-39`

### Configuration
### Environment Variables
The system uses environment variables defined in the .env file: `.env:1-37`

### Key Configuration Categories:

- `Kafka`: Ports and cluster settings `.env:3-6`
- `PostgreSQL`: Database credentials and ports `.env:8-12`
- `MongoDB`: Activity database settings `.env:14-22`
- `Redis`: Caching configuration `.env:35-37`
- `Admin Interfaces`: Access credentials `.env:30-33`

### Logging Configuration
The system uses centralized logging with Logback configuration. `logback-spring.xml:16-17`

#### Features:

- Structured logging with trace/span/request IDs
- Rolling file appenders with size and time-based policies `logback-spring.xml:30-35`
- Separate error log files `logback-spring.xml:43-58`
- Async logging for performance `logback-spring.xml:61-71`

### Data Persistence
All databases use Docker volumes for data persistence: `setup-compose.yml:126-129`

- `runzo_user_data:` PostgreSQL user database
- `runzo_activity_data`: MongoDB activity database
- `runzo_alservice_data`: PostgreSQL ALService database

### Data persists across container restarts and updates.
  #### Administrative Access
| Service       | URL                         | Credentials                 |
|---------------|-----------------------------|-----------------------------|
| PgAdmin       | http://localhost:9002       | admin@pgadmin.com / admin   |
| Mongo Express | http://localhost:9004       | user / pass                 |
| Redis Insight | http://localhost:9003       | No authentication           |
| Kafka UI      | http://localhost:9001       | No authentication           |


### Logging and Monitoring README.md:28-30
- Script logs appear in real-time in your terminal
- Full log is saved in setup.log
- Application logs are configured with rolling policies and async processing

### Troubleshooting `README.md:37-40`
#### Common Issues
- Docker didn't start: Make sure virtualization is enabled (Hyper-V/WSL2 for Windows, Docker Engine for Linux)
- Permission issues on Linux: Run script with sudo pwsh
- Missing variables: Check .env file has all required keys
- Port conflicts: Verify ports 2181, 5433, 5435, 6379, 9001-9004, 27017 are available

### Log Analysis
Check the following for debugging:

- Real-time terminal output during setup
- `setup.log` file for complete setup logs
- Individual service logs in the `logs/ directory`

### Development
#### Module Structure
The project follows a multi-module Maven structure: pom.xml:12-17

- `common/`: Shared utilities and configurations
- `userservice/`: User management microservice
- `activityservice/`: Activity tracking microservice
- `aiservice/`: AI recommendations and ML processing microservice


### Technology Stack pom.xml:19-25
- **Java:** Version 21
- **Spring Boot:** Version 3.3.12
- **Spring Cloud:** Version 2023.0.6
- **Maven:** Multi-module project structure
- **Lombok:** Code generation

### Configuration Management
- Environment variables in `.env` file
- Service-specific configurations in each module
- Shared logging configuration in common module
- Docker Compose orchestration


### Contributing
- Fork the repository
- Create a feature branch
- Make your changes
- Test with the setup script
- Submit a pull request

### License
This project is licensed under the Apache License 2.0.
## Notes 

This comprehensive README.md includes all four services (User, Activity, AI, and Eureka) along with the complete infrastructure setup. [37](#3-36)  The AI service is properly integrated into the architecture documentation, showing its reactive WebFlux nature and MongoDB integration. The README maintains the existing setup workflow while providing detailed information about each service's capabilities, the shared common module, and the complete Docker-based infrastructure with administrative interfaces.

Wiki pages you might want to explore:
- [Infrastructure Setup (emultics/runzo-microservices)](https://deepwiki.com/emultics/runzo-microservices)
- [User Service (emultics/runzo-microservices)](https://deepwiki.com/emultics/runzo-microservices)





