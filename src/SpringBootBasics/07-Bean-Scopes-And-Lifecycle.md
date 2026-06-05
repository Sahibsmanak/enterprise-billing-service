# Chapter 07 - Bean Scopes & Bean Lifecycle

## Introduction

Until now we learned:

* IoC Container
* Beans
* Dependency Injection
* Component Scanning
* Bean Creation

A new question arises:

```text
How many objects does Spring create for a Bean?

One object?
One per request?
One per user?
A new object every time?
```

The answer is provided by Bean Scopes.

---

# What is Bean Scope?

Definition:

```text
Bean Scope defines how many instances
of a Bean Spring should create and
manage.
```

---

# Singleton Scope

Default Spring Scope.

Example:

```java
@Service
public class UserService {
}
```

Equivalent:

```java
@Service
@Scope("singleton")
public class UserService {
}
```

---

## How Singleton Works

When application starts:

```java
SpringApplication.run(...)
```

Spring creates:

```java
new UserService()
```

once and stores it inside ApplicationContext.

Visual:

```text
ApplicationContext
       |
       |
UserService@123
```

---

## Request Flow

```text
Request 1 ---> UserService@123

Request 2 ---> UserService@123

Request 3 ---> UserService@123
```

Same object reused everywhere.

---

## Advantages

```text
Less Memory Usage

Better Performance

Fast Startup

Reuse Existing Objects
```

---

## Important

Singleton Beans are shared across multiple threads.

Example:

```text
Thread-1
Thread-2
Thread-3
```

may execute methods on the same object simultaneously.

Therefore:

```text
Singleton Beans should be Stateless.
```

---

## Stateless Service Example

```java
@Service
public class UserService {

    public String getUser() {
        return "Sahib";
    }
}
```

Safe because no mutable shared state exists.

---

# Prototype Scope

Definition:

```text
A new object is created every time
Spring is asked for the Bean.
```

Example:

```java
@Component
@Scope("prototype")
public class ReportGenerator {
}
```

---

## How Prototype Works

Application Startup:

```text
Bean Definition Stored
Object Not Created Yet
```

Object created only when requested:

```java
context.getBean(ReportGenerator.class);
```

---

## Example

```java
ReportGenerator r1 =
    context.getBean(ReportGenerator.class);

ReportGenerator r2 =
    context.getBean(ReportGenerator.class);
```

Result:

```text
r1 != r2
```

Different objects.

---

## Use Cases

```text
PDF Generator

Report Builder

Temporary Processing Object

Job Context
```

---

# Request Scope

Definition:

```text
One Bean Instance
Per HTTP Request
```

Example:

```java
@Component
@RequestScope
public class RequestTracker {
}
```

---

## Request Flow

```text
Request 1
    |
RequestTracker@111

Request 2
    |
RequestTracker@222

Request 3
    |
RequestTracker@333
```

Every request receives a fresh object.

---

## Use Cases

```text
Request Metadata

Correlation IDs

Request Context

Tracing Information
```

---

# Session Scope

Definition:

```text
One Bean Instance
Per User Session
```

Example:

```java
@Component
@SessionScope
public class ShoppingCart {
}
```

---

## Session Flow

```text
User A
    |
ShoppingCart-A

User B
    |
ShoppingCart-B

User C
    |
ShoppingCart-C
```

Each user gets a separate object.

---

## Use Cases

```text
Shopping Cart

Logged-in User Data

Multi-Step Forms

User Session Information
```

---

# Application Scope

Definition:

```text
One Bean Instance
Per Web Application
```

Example:

```java
@Component
@ApplicationScope
public class GlobalSettings {
}
```

---

## Flow

```text
User A
      |
User B ----> GlobalSettings@123
      |
User C
```

All users share the same object.

---

## Use Cases

```text
Global Configuration

Application Statistics

Feature Flags

Shared Metadata
```

---

# Singleton vs Application Scope

## Singleton

```text
One Bean Instance
Per Spring ApplicationContext
```

---

## Application Scope

```text
One Bean Instance
Per ServletContext (Web Application)
```

---

## Practical Reality

In most Spring Boot applications:

```text
One Application
      |
One ApplicationContext
      |
One ServletContext
```

Therefore:

```text
Singleton Scope
≈
Application Scope
```

The difference only becomes relevant in advanced multi-context applications.

---

# Scope Summary

| Scope       | Object Count                    |
| ----------- | ------------------------------- |
| Singleton   | One per ApplicationContext      |
| Prototype   | New object every Bean retrieval |
| Request     | One per HTTP request            |
| Session     | One per User Session            |
| Application | One per Web Application         |

---

# What is ApplicationContext.getBean()?

ApplicationContext is the Spring Container.

Example:

```java
ApplicationContext context =
        SpringApplication.run(...);
```

To retrieve a Bean:

```java
UserService service =
        context.getBean(UserService.class);
```

Spring returns the Bean stored in the container.

---

# Singleton vs Prototype Creation

## Singleton

Application startup:

```text
Application Starts
       |
Bean Created Immediately
```

Default behavior:

```text
Eager Initialization
```

---

## Prototype

Application startup:

```text
Application Starts
       |
Only Bean Definition Stored
```

Object created later when requested.

---

# Bean Lifecycle

Definition:

```text
Lifecycle of a Bean from creation
until destruction.
```

---

# Lifecycle Flow

```text
Bean Creation
      |
Dependency Injection
      |
@PostConstruct
      |
Bean Ready
      |
Application Running
      |
@PreDestroy
      |
Bean Destroyed
```

---

# Step 1 - Bean Creation

Spring executes:

```java
new UserService()
```

Constructor runs.

Example:

```java
public UserService() {

    System.out.println("Constructor");
}
```

---

# Step 2 - Dependency Injection

Spring injects dependencies.

Example:

```java
public UserService(
        UserRepository repository)
```

Spring supplies the required Bean.

---

# Step 3 - Initialization

Using:

```java
@PostConstruct
```

Example:

```java
@PostConstruct
public void init() {

    System.out.println(
        "Bean Initialized"
    );
}
```

Runs:

```text
After Bean Creation
After Dependency Injection
Before First Usage
```

---

## Common Uses

```text
Load Cache

Connect Kafka

Initialize Resources

Preload Data
```

---

# Step 4 - Bean Ready

Bean can now serve requests.

---

# Step 5 - Destruction

Using:

```java
@PreDestroy
```

Example:

```java
@PreDestroy
public void cleanup() {

    System.out.println(
        "Bean Destroyed"
    );
}
```

Runs when application shuts down.

---

## Common Uses

```text
Close Connections

Stop Threads

Release Resources

Cleanup Tasks
```

---

# Complete Example

```java
@Service
public class UserService {

    public UserService() {

        System.out.println(
            "Constructor"
        );
    }

    @PostConstruct
    public void init() {

        System.out.println(
            "PostConstruct"
        );
    }

    @PreDestroy
    public void destroy() {

        System.out.println(
            "PreDestroy"
        );
    }
}
```

---

## Output

Application Startup:

```text
Constructor

PostConstruct
```

Application Shutdown:

```text
PreDestroy
```

---

# Prototype Lifecycle Difference

Singleton Beans:

```text
Creation
Initialization
Destruction
```

managed by Spring.

---

Prototype Beans:

```text
Creation
Initialization
```

managed by Spring.

---

```text
Destruction
```

NOT managed by Spring.

Reason:

```text
Spring does not know
when you are done using
the prototype object.
```

---

# Interview Questions

## What is the default Bean Scope?

```text
Singleton
```

---

## Are Singleton Beans Thread Safe?

```text
Not automatically.

They are shared across threads.

They are safe when stateless.
```

---

## Difference Between Singleton and Prototype?

```text
Singleton:
One Object Per ApplicationContext

Prototype:
New Object Every Bean Retrieval
```

---

## Difference Between Session and Application Scope?

```text
Session:
One Object Per User Session

Application:
One Object Shared By Entire Application
```

---

## When Does @PostConstruct Execute?

```text
After Bean Creation
After Dependency Injection
Before Bean Usage
```

---

## When Does @PreDestroy Execute?

```text
Before Bean Destruction
During Application Shutdown
```

---

# Key Takeaways

1. Singleton is the default scope.
2. Singleton Beans should be stateless.
3. Prototype creates a new object every retrieval.
4. Request Scope creates a Bean per HTTP request.
5. Session Scope creates a Bean per user session.
6. Application Scope creates a Bean per web application.
7. Bean Lifecycle consists of Creation → DI → Initialization → Usage → Destruction.
8. @PostConstruct is used for initialization.
9. @PreDestroy is used for cleanup.
10. Spring manages destruction only for Singleton Beans.
