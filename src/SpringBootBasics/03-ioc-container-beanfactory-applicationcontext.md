# Chapter 03 - IoC Container, BeanFactory and ApplicationContext

## Introduction

In the previous chapter, we learned about Inversion of Control (IoC).

IoC is the principle that transfers the responsibility of object creation and dependency management from application code to the framework.

A natural question follows:

> Who actually performs this responsibility?

The answer is:

```text
Spring IoC Container
```

The IoC Container is one of the most important components in the Spring Framework.

Without it, concepts such as Beans, Dependency Injection, and Spring Boot cannot exist.

This chapter explains how the IoC Container works and introduces BeanFactory and ApplicationContext.

---

# What Is an IoC Container?

An IoC Container is the core component of Spring responsible for managing application objects.

Its responsibilities include:

* Creating objects
* Storing objects
* Managing object lifecycle
* Injecting dependencies
* Destroying objects when required

Conceptually:

```text
+------------------------+
|     IoC Container      |
+------------------------+
| UserController         |
| UserService            |
| UserRepository         |
| EmailService           |
| PaymentService         |
+------------------------+
```

Every Spring-managed object resides inside the container.

---

# Why Do We Need a Container?

Consider a simple application.

```text
UserController
      |
      v
UserService
      |
      v
UserRepository
```

Without Spring:

```java
UserRepository repository =
        new UserRepository();

UserService service =
        new UserService(repository);

UserController controller =
        new UserController(service);
```

The developer is responsible for:

* Creating objects
* Wiring dependencies
* Managing object lifecycle

As applications grow larger, this becomes difficult to maintain.

The IoC Container centralizes these responsibilities.

---

# Responsibilities of the IoC Container

The IoC Container performs five major tasks.

## 1. Bean Creation

The container creates application objects.

Example:

```java
@Service
public class UserService {

}
```

The container creates an instance of UserService.

---

## 2. Bean Storage

Created objects are stored inside the container.

Conceptually:

```text
Bean Name          Object

userService   -> UserService Instance
userRepo      -> UserRepository Instance
emailService  -> EmailService Instance
```

Think of it as an internal registry of managed objects.

---

## 3. Dependency Injection

When one bean depends on another bean, the container provides the dependency automatically.

Example:

```java
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

The container creates UserRepository and injects it into UserService.

---

## 4. Lifecycle Management

The container manages:

```text
Creation
Initialization
Usage
Destruction
```

of beans.

---

## 5. Bean Retrieval

The container allows beans to be requested when required.

Example:

```java
UserService service =
    context.getBean(UserService.class);
```

---

# BeanFactory

The first IoC container introduced by Spring was:

```text
BeanFactory
```

BeanFactory provides the fundamental IoC capabilities.

Responsibilities:

* Create Beans
* Store Beans
* Retrieve Beans

Think of BeanFactory as:

```text
Basic IoC Container
```

It focuses only on core bean management functionality.

---

# Internal Mental Model of BeanFactory

Conceptually, BeanFactory behaves similarly to:

```java
Map<String, Object>
```

Example:

```text
userService      -> UserService Object
userRepository   -> UserRepository Object
emailService     -> EmailService Object
```

Although the real implementation is more complex, this mental model is useful when learning Spring.

---

# Limitations of BeanFactory

As enterprise applications grew, developers required more functionality.

Requirements included:

* Event handling
* Internationalization
* Resource loading
* Annotation support
* Environment management
* Enterprise integrations

BeanFactory was too minimal for these needs.

Spring introduced a more powerful container.

---

# ApplicationContext

ApplicationContext is the modern IoC container used by Spring applications today.

Relationship:

```text
BeanFactory
      ↑
ApplicationContext
```

ApplicationContext extends BeanFactory.

Meaning:

```text
Everything BeanFactory provides
+
Additional Enterprise Features
```

---

# Features of ApplicationContext

## Bean Management

Everything BeanFactory can do.

---

## Event Publishing

Spring applications can publish and consume events.

Example:

```text
UserRegisteredEvent
OrderCreatedEvent
PaymentCompletedEvent
```

---

## Environment Management

ApplicationContext can access:

```text
application.properties
application.yml
Environment Variables
Profiles
```

---

## Resource Loading

The container can load:

```text
Files
Classpath Resources
URLs
```

---

## Annotation Processing

Annotations such as:

```java
@Component
@Service
@Repository
@Autowired
```

are supported through ApplicationContext.

---

# BeanFactory vs ApplicationContext

| Feature              | BeanFactory | ApplicationContext |
| -------------------- | ----------- | ------------------ |
| Bean Creation        | Yes         | Yes                |
| Dependency Injection | Yes         | Yes                |
| Event Support        | No          | Yes                |
| Resource Loading     | Limited     | Yes                |
| Annotation Support   | Limited     | Yes                |
| Environment Support  | No          | Yes                |
| Enterprise Features  | Minimal     | Extensive          |

---

# Why Spring Boot Uses ApplicationContext

Modern Spring Boot applications require:

* Annotation Processing
* Auto Configuration
* Profiles
* Events
* Resource Loading
* Property Management

Because of these requirements, Spring Boot uses ApplicationContext instead of BeanFactory.

---

# Startup Flow

When a Spring Boot application starts:

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(
                Application.class,
                args
        );
    }
}
```

The following sequence occurs:

```text
JVM Starts
      |
main()
      |
SpringApplication.run()
      |
ApplicationContext Created
      |
Class Scanning Begins
      |
Beans Identified
      |
Beans Created
      |
Dependencies Injected
      |
Application Ready
```

This startup sequence forms the foundation of Spring Boot internals.

---

# Production Relevance

Understanding the IoC Container becomes important when:

* Startup is slow
* A bean fails to initialize
* Circular dependencies occur
* Dependency injection fails
* ApplicationContext startup errors appear

A solid understanding of the container helps diagnose these issues quickly.

---

# Interview Questions

## What is an IoC Container?

An IoC Container is a Spring component responsible for creating, managing, storing, and wiring beans.

---

## What is BeanFactory?

BeanFactory is the basic IoC container that provides core bean management functionality.

---

## What is ApplicationContext?

ApplicationContext is an advanced IoC container that extends BeanFactory and provides enterprise-level features.

---

## Which container does Spring Boot use?

Spring Boot uses ApplicationContext.

---

## Difference between BeanFactory and ApplicationContext?

ApplicationContext provides all BeanFactory capabilities plus:

* Event support
* Resource loading
* Annotation support
* Environment management
* Enterprise features

---

# Key Takeaways

1. IoC Container is responsible for bean management.
2. Spring-managed objects are called Beans.
3. BeanFactory is the basic IoC container.
4. ApplicationContext extends BeanFactory.
5. Spring Boot uses ApplicationContext.
6. The container creates, stores, manages, and injects beans.
7. Understanding the container is essential for Spring internals and troubleshooting.
