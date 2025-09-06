# Spring Boot Reactive Project with H2 Database

## Introduction

This project demonstrates a simple Spring Boot reactive application using WebFlux and H2 in-memory database. The application showcases reactive programming principles with non-blocking I/O operations.

## Project Overview

- **Framework**: Spring Boot 3.x
- **Reactive Stack**: Spring WebFlux
- **Database**: H2 (In-memory)
- **Data Access**: Spring Data R2DBC
- **Build Tool**: Maven/Gradle

## Key Features

- Reactive REST APIs
- Non-blocking database operations
- Reactive streams with Mono and Flux
- H2 console for database inspection
- JSON-based data exchange

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/reactive/
│   │       ├── UserApplication.java
│   │       ├── controller/
│   │       ├── model/
│   │       ├── repository/
│   │       └── service/
│   └── resources/
│       ├── application.yml
│       └── schema.sql
└── test/
```

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+ or Gradle 7+
- IDE (IntelliJ IDEA, VS Code, etc.)

### Dependencies

Key dependencies to include:
- `spring-boot-starter-webflux`
- `spring-boot-starter-data-r2dbc`
- `r2dbc-h2`
- `h2`

## Next Steps

1. Set up project dependencies
2. Configure H2 database connection
3. Create entity models
4. Implement repositories
5. Build reactive controllers
6. Add error handling
7. Swagger


### Step 1: Set up project dependencies

Add the following dependencies to your `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-r2dbc</artifactId>
    </dependency>
    <dependency>
        <groupId>io.r2dbc</groupId>
        <artifactId>r2dbc-h2</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

### Step 2: Configure H2 database connection

Configure `application.yml`:

```yaml
spring:
  r2dbc:
    url: r2dbc:h2:mem:///testdb
    username: sa
    password: 
  h2:
    console:
      enabled: true
```

### Step 3: Create entity models

```java
@Table("users")
public class User {
    @Id
    private Long id;
    private String name;
    private String email;
    
    // constructors, getters, setters
}
```

### Step 4: Implement repositories

```java
@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Flux<User> findByName(String name);
}
```

### Step 5: Build reactive controllers

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping
    public Flux<User> getAllUsers() {
        return userService.findAll();
    }
    
    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userService.save(user);
    }
}
```

### Step 6: Add error handling

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.badRequest().body("Data integrity violation");
    }
}
```

### Step 6: Swagger
[Swagger link](http://localhost:8080/swagger-ui/index.htm)

## Source code
[Source code](/src/users/)

