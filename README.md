# Base Project Back End Template

This project serves as a foundation for starting any new Spring Boot project, incorporating best practices and a well-structured design. The aim is to provide a flexible and efficient base to build upon, helping developers focus on business logic without worrying about the initial setup and configuration.

## Features

- **Modular Design**: Structured codebase with clear separation of concerns, making it easy to maintain and extend.
- **Error Handling**: Custom exceptions and error response handling to ensure clear communication of issues via the API.
- **Validation**: Input validation using `@Validated` annotations to ensure data integrity.
- **Service Layer**: Business logic separated from controllers, promoting a clean and testable architecture.
- **Persistence Layer**: Designed to easily switch between different data storage methods (e.g., file-based, database).
- **Configuration**: Externalized configuration using `application.properties` to handle environment-specific settings.
- **Testing**: Unit and integration tests using JUnit 5 to ensure code quality.

## Technologies

- **Java 17**: Leverages the latest features of the JDK 17.
- **Spring Boot 3.3.2**: Provides a robust framework for building microservices and web applications with minimal setup.

## Getting Started

### Prerequisites

- **JDK 17**: Make sure to have JDK 17 installed. You can download it from [AdoptOpenJDK](https://adoptopenjdk.net/).
- **Maven**: Ensure Maven is installed for dependency management.

### Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/p-velasquez/be-template.git
   cd be-template
   
