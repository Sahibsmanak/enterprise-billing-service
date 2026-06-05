# Hands-On 01 - Understanding IoC, Beans and Dependency Injection Through a Real Spring Boot Project

## Objective

The goal of this hands-on exercise was to understand:

* Why Dependency Injection exists
* How Spring creates objects
* How Spring wires dependencies
* How Beans are created
* How the IoC Container works
* How a request travels through the application

Instead of only learning theory, we built a small Spring Boot application and compared it with traditional Java object creation.

---

# Project Architecture

```text
Browser
   |
GET /user
   |
UserController
   |
UserService
   |
UserRepository
```

Application Layers:

```text
Controller Layer
        |
Service Layer
        |
Repository Layer
```

---

# Step 1 - Traditional Java Approach

Initially, the service created its own dependency.

```java
public class UserService {

    private UserRepository repository =
            new MySqlUserRepository();

    public String getUser() {
        return repository.getUser();
    }
}
```

Problems:

```text
UserService
      |
Creates Dependency
      |
MySqlUserRepository
```

Issues:

1. Tight Coupling
2. Difficult Testing
3. Difficult Maintenance
4. Business Logic Depends On Implementation

---

# Problem Demonstration

Current implementation:

```java
private UserRepository repository =
        new MySqlUserRepository();
```

Suppose company migrates:

```text
MySQL
   ↓
PostgreSQL
```

Code must change:

```java
private UserRepository repository =
        new PostgresUserRepository();
```

Business class changed.

This violates the principle of loose coupling.

---

# Step 2 - Manual Dependency Injection

Service modified to:

```java
public class UserService {

    private UserRepository repository;

    public UserService(
            UserRepository repository) {

        this.repository = repository;
    }

    public String getUser() {
        return repository.getUser();
    }
}
```

Main class:

```java
UserRepository repository =
        new MySqlUserRepository();

UserService service =
        new UserService(repository);
```

Dependency creation moved outside.

---

# What Changed?

Before:

```text
UserService
      |
Creates Repository
```

After:

```text
External Source
      |
Provides Repository
      |
UserService
```

This is Dependency Injection.

---

# Important Realization

Dependency Injection is NOT a Spring feature.

Dependency Injection is a design pattern.

Spring simply automates it.

Manual DI:

```java
UserRepository repository =
        new MySqlUserRepository();

UserService service =
        new UserService(repository);
```

is already Dependency Injection.

---

# Step 3 - Spring Version

Repository:

```java
@Repository
public class MySqlUserRepository
        implements UserRepository {

    @Override
    public String getUser() {
        return "From MySQL";
    }
}
```

---

Service:

```java
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(
            UserRepository repository) {

        this.repository = repository;
    }

    public String getUser() {
        return repository.getUser();
    }
}
```

---

Controller:

```java
@RestController
public class UserController {

    private final UserService service;

    public UserController(
            UserService service) {

        this.service = service;
    }

    @GetMapping("/user")
    public String printUser() {

        return service.getUser();
    }
}
```

---

Application Class

```java
@SpringBootApplication
public class BillingServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(
            BillingServiceApplication.class,
            args
        );
    }
}
```

---

# What Happens During Startup?

When application starts:

```java
SpringApplication.run(...)
```

Spring creates:

```text
ApplicationContext
```

which is the IoC Container.

---

# Component Scan Begins

Spring scans:

```text
com.example.demo
```

and sub-packages.

Discovers:

```text
MySqlUserRepository
UserService
UserController
```

because of:

```java
@Repository
@Service
@RestController
```

---

# Bean Creation Flow

Step 1

```text
Create MySqlUserRepository Bean
```

Equivalent:

```java
new MySqlUserRepository()
```

---

Step 2

Spring sees:

```java
public UserService(
        UserRepository repository)
```

Needs:

```text
UserRepository
```

Already available.

Creates:

```java
new UserService(
        mysqlRepositoryBean
)
```

---

Step 3

Spring sees:

```java
public UserController(
        UserService service)
```

Needs:

```text
UserService
```

Already available.

Creates:

```java
new UserController(
        userServiceBean
)
```

---

# Final Container State

Conceptually:

```text
ApplicationContext

|
+-- MySqlUserRepository Bean
|
+-- UserService Bean
|
+-- UserController Bean
```

---

# Request Flow

Browser Request:

```text
GET /user
```

---

Spring receives request:

```text
DispatcherServlet
      |
UserController
```

---

Controller executes:

```java
service.getUser()
```

---

Service executes:

```java
repository.getUser()
```

---

Repository returns:

```text
From MySQL
```

---

Response Flow

```text
Repository
    |
Service
    |
Controller
    |
DispatcherServlet
    |
Browser
```

Output:

```text
From MySQL
```

---

# IoC Demonstrated

Question:

Who created UserController?

Answer:

```text
Spring Container
```

---

Who created UserService?

```text
Spring Container
```

---

Who created Repository?

```text
Spring Container
```

---

This is:

```text
Inversion Of Control
```

because control moved from application code to Spring.

---

# Dependency Injection Demonstrated

Question:

How did UserService receive UserRepository?

Answer:

```text
Constructor Injection
```

performed by Spring.

---

Question:

How did UserController receive UserService?

Answer:

```text
Constructor Injection
```

performed by Spring.

---

# Why Constructor Injection Was Used

Benefits:

```text
Mandatory Dependencies
Immutability
Easy Testing
Clear Dependencies
Better Design
```

Example:

```java
private final UserRepository repository;
```

The dependency can never be reassigned.

---

# Comparison - Manual vs Spring

Manual DI

```java
UserRepository repo =
        new MySqlUserRepository();

UserService service =
        new UserService(repo);

UserController controller =
        new UserController(service);
```

Developer performs wiring.

---

Spring DI

```text
Container
     |
Creates Beans
     |
Resolves Dependencies
     |
Injects Dependencies
```

Spring performs wiring.

---

# Key Learnings

1. Dependency Injection is a design pattern, not a Spring feature.
2. Spring automates Dependency Injection.
3. Beans are objects managed by Spring.
4. ApplicationContext acts as the IoC Container.
5. Constructor Injection is the preferred injection style.
6. Business logic should depend on abstractions, not implementations.
7. Spring replaces the manual object creation code traditionally written in main methods.
8. IoC and DI are now demonstrated practically through a running Spring Boot application.

---

# Interview Summary

## What is Dependency Injection?

Dependency Injection is a design pattern where dependencies are supplied from an external source rather than being created inside the class.

---

## What is IoC?

Inversion of Control is the principle where object creation and dependency management are handled by the framework instead of application code.

---

## How does Spring achieve IoC?

Spring achieves IoC primarily through Dependency Injection.

---

## Why Constructor Injection?

* Mandatory dependencies
* Immutability
* Better testing
* Better maintainability

---

## Difference Between IoC and DI

```text
IoC = Principle

DI = Technique
```

Spring uses DI to achieve IoC.
    