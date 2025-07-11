# Rinha de Backend 2025 - Spring Boot

Esta é uma implementação da [Rinha de Backend 2025](https://github.com/zanfranceschi/rinha-de-backend-2025) utilizando Spring Boot (Work in progress). A intenção é usar bibliotecas e design patterns conhecidos e não velocidade, por isso é provável que o projeto não seja submetido no repo oficial.

## Tecnologias

### Bibliotecas

- **Spring Boot:** Framework principal para a construção da aplicação.
- **Spring Boot Starter Validation:** Para validação de dados de entrada.
- **Spring Data JPA:** Simplifica o acesso a dados utilizando a abstração de repositórios.
- **Spring Web:** Utilizado para a construção da API REST.
- **Spring Cloud OpenFeign:** Cliente REST declarativo para a comunicação com serviços externos.
- **Spring Cloud Circuit Breaker (Resilience4j):** Implementa o padrão Circuit Breaker para tolerância a falhas.
- **Flyway:** Ferramenta para versionamento e migração de banco de dados.

### Padrões de Projeto

- **Outbox Pattern:** Garante a consistência dos dados entre o banco de dados da aplicação e sistemas externos.
- **Strategy Pattern:** Utilizado no padrão Outbox para permitir diferentes lógicas de processamento.
- **Circuit Breaker Pattern:** Utilizado com o Resilience4j para evitar que falhas em serviços externos se propaguem pela aplicação.

## Como Executar

1.  **Clone o repositório:**

    ```bash
    git clone https://github.com/rkaraujo/rka-springboot-rinha-backend-2025.git
    cd rka-springboot-rinha-backend-2025
    ```

2.  **Configure o banco de dados:**

    - Certifique-se de que você tem um banco de dados PostgreSQL em execução.
    - Altere as configurações de conexão no arquivo `src/main/resources/application.yml` se necessário.

3.  **Execute a aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```

A aplicação estará disponível em `http://localhost:9999`.

## Faltando e melhorias

- Endpoint payments-summary
- Docker
- Configurar timeouts, circuit breaker, db connection pool
- Testes :(
- Deixar o POST payments idempotente [talvez][melhoria]

---

# Rinha de Backend 2025 - Spring Boot (English version)

This is an implementation of the [Rinha de Backend 2025](https://github.com/zanfranceschi/rinha-de-backend-2025) using Spring Boot (Work in progress). The intention is to use well-known libraries and design patterns rather than focusing on speed, so it is unlikely that the project will be submitted to the official repo.

## Technologies

### Libraries

- **Spring Boot:** The main framework for building the application.
- **Spring Boot Starter Validation:** For input data validation.
- **Spring Data JPA:** Simplifies data access using the repository abstraction.
- **Spring Web:** Used for building the REST API.
- **Spring Cloud OpenFeign:** Declarative REST client for communicating with external services.
- **Spring Cloud Circuit Breaker (Resilience4j):** Implements the Circuit Breaker pattern for fault tolerance.
- **Flyway:** Tool for database versioning and migration.

### Design Patterns

- **Outbox Pattern:** Ensures data consistency between the application's database and external systems.
- **Strategy Pattern:** Used in the Outbox pattern to allow for different processing logic.
- **Circuit Breaker Pattern:** Used with Resilience4j to prevent failures in external services from propagating through the application.

## How to Run

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/rkaraujo/rka-springboot-rinha-backend-2025.git
    cd rka-springboot-rinha-backend-2025
    ```

2.  **Configure the database:**

    - Make sure you have a PostgreSQL database running.
    - Change the connection settings in the `src/main/resources/application.yml` file if necessary.

3.  **Run the application:**
    ```bash
    ./mvnw spring-boot:run
    ```

The application will be available at `http://localhost:9999`.

## Missing Features and Improvements

- `payments-summary` endpoint
- Docker
- Configure timeouts, circuit breaker, db connection pool
- Tests :(
- Make the POST payments idempotent [maybe][improvement]
