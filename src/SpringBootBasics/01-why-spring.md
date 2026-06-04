# Chapter 01 - Why Spring Was Created

## Introduction

Before understanding Spring Framework or Spring Boot, it is important to understand the problems Java developers faced before Spring existed.

Many developers start learning Spring by memorizing annotations such as `@Component`, `@Service`, `@Autowired`, and `@Bean`.

However, these annotations only make sense when we understand the problems they were designed to solve.

Spring was not created because developers wanted a new framework.

Spring was created because enterprise Java development had become difficult, complex, and hard to maintain.

---

# The World Before Spring

Around the early 2000s, Java was already one of the most popular programming languages for enterprise software development.

Companies were building:

* Banking Systems
* Insurance Applications
* E-Commerce Platforms
* ERP Systems
* Internal Enterprise Tools

A typical application contained hundreds of classes spread across multiple modules.

Example:

```text
User Module
Product Module
Order Module
Payment Module
Notification Module
```

Each module contained many classes:

```text
Controller
Service
Repository
Utility Classes
Validators
External Integrations
```

As applications grew larger, managing these classes became increasingly difficult.

---

# Problem 1: Manual Object Creation

Consider the following classes:

```java
public class UserRepository {

}
```

```java
public class EmailService {

}
```

```java
public class UserService {

    private UserRepository repository =
            new UserRepository();

    private EmailService emailService =
            new EmailService();
}
```

In this design, `UserService` is responsible for creating its own dependencies.

At first this seems simple.

However, real-world applications contain hundreds of services and repositories.

A dependency graph often looks like this:

```text
UserController
      |
      v
UserService
      |
      +----> UserRepository
      |
      +----> EmailService
      |
      +----> AuditService
      |
      +----> ValidationService
```

Now imagine hundreds of such classes throughout the application.

Developers must manually create and manage every object.

This quickly becomes difficult to maintain.

---

# Problem 2: Tight Coupling

Consider:

```java
public class UserService {

    private MySqlUserRepository repository =
            new MySqlUserRepository();
}
```

The service now directly depends on a specific implementation.

Dependency:

```text
UserService
      |
      v
MySqlUserRepository
```

Suppose the company migrates from MySQL to PostgreSQL.

A new implementation is introduced:

```java
public class PostgresUserRepository {
}
```

Now the service must be modified.

Business logic becomes tightly coupled to implementation details.

This violates one of the most important software engineering principles:

```text
High Cohesion
Low Coupling
```

The more tightly coupled an application becomes, the harder it is to maintain and extend.

---

# Problem 3: Difficult Unit Testing

Testing becomes difficult when classes create their own dependencies.

Example:

```java
public class UserService {

    private EmailService emailService =
            new EmailService();
}
```

During testing, developers often want to replace real dependencies with fake implementations.

Example:

```java
FakeEmailService
```

However, because `UserService` creates the dependency internally, replacing it becomes difficult.

Developers were forced to write extra code and workarounds to test their applications.

This made testing expensive and time-consuming.

---

# Problem 4: Configuration Management

Enterprise applications require configuration values such as:

```text
Database URL
Database Username
Database Password
SMTP Host
SMTP Port
API Keys
```

Before Spring, every project handled configuration differently.

Some projects used:

```text
XML Files
```

Others used:

```text
Properties Files
```

Some projects even hardcoded values directly in source code.

There was no consistent approach.

As applications grew larger, configuration management became messy and error-prone.

---

# Problem 5: Transaction Management

Consider a banking transaction.

A customer transfers ₹1000 from Account A to Account B.

Steps:

```text
1. Deduct money from Account A
2. Add money to Account B
```

What happens if step 1 succeeds but step 2 fails?

Money disappears from the system.

To avoid this, developers manually managed transactions.

Example:

```java
connection.setAutoCommit(false);

try {

    // business logic

    connection.commit();

} catch (Exception e) {

    connection.rollback();
}
```

This code was repeated throughout enterprise applications.

Developers spent significant time writing infrastructure code instead of business logic.

---

# Problem 6: Complexity of EJB

The popular enterprise solution before Spring was Enterprise JavaBeans (EJB).

EJB attempted to solve enterprise problems but introduced significant complexity.

Even simple applications required:

* Interfaces
* XML Configuration
* Deployment Descriptors
* Containers
* Remote Objects

A small amount of business logic often required a large amount of framework-related code.

Developers found EJB difficult to learn, difficult to maintain, and overly complicated.

---

# The Need for a Better Solution

Developers wanted a framework that would:

* Reduce boilerplate code
* Simplify enterprise development
* Improve testability
* Promote loose coupling
* Manage infrastructure concerns automatically

This need led to the creation of Spring Framework.

---

# Spring's Core Idea

Spring introduced a revolutionary idea:

> Developers should focus on business logic, while the framework manages object creation and infrastructure concerns.

Instead of writing:

```java
UserRepository repository =
        new UserRepository();
```

Spring takes responsibility for creating and managing objects.

Developers simply declare what they need.

Spring provides it automatically.

---

# What Spring Solves

Spring primarily solves the following problems:

## 1. Object Creation

Spring creates and manages application objects.

## 2. Dependency Management

Spring automatically connects dependent objects together.

## 3. Configuration Management

Spring centralizes application configuration.

## 4. Transaction Management

Spring simplifies transaction handling.

## 5. Testability

Spring makes it easier to replace dependencies and write unit tests.

## 6. Enterprise Development

Spring provides production-ready solutions for common enterprise requirements.

---

# Core Philosophy of Spring

Everything in Spring revolves around one fundamental principle:

```text
Do not create and manage objects yourself.

Let Spring manage them.
```

This philosophy becomes the foundation for:

* IoC (Inversion of Control)
* Dependency Injection
* Beans
* Application Context
* Spring Boot

Understanding this principle is essential before moving forward.

---

# Key Takeaways

1. Enterprise Java development was difficult before Spring.
2. Object creation was handled manually.
3. Applications became tightly coupled.
4. Testing was difficult.
5. Transaction management required boilerplate code.
6. EJB introduced significant complexity.
7. Spring was created to simplify enterprise Java development.
8. Spring's primary goal is to allow developers to focus on business logic rather than infrastructure code.
9. Spring manages object creation, dependency wiring, configuration, and enterprise concerns.
10. The foundation of Spring is: "Let Spring manage objects."
