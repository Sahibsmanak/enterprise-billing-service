# Chapter 04 - Beans

## Introduction

Beans are one of the most fundamental concepts in the Spring Framework.

Many developers define a Bean as:

> An object managed by Spring.

While this definition is technically correct, it does not explain why Beans exist or how they fit into the overall Spring architecture.

To understand Dependency Injection, Bean Lifecycle, AOP, Transactions, Security, and Spring Boot internals, a strong understanding of Beans is required.

---

# Why Do Beans Exist?

Before Spring, developers manually created application objects.

Example:

```java
UserService service = new UserService();
```

In this case:

* The developer creates the object.
* The developer manages the object.
* The developer controls its lifecycle.

As applications grow larger, manually managing hundreds of objects becomes difficult.

Spring solves this by taking ownership of important application objects.

Those Spring-managed objects are called Beans.

---

# Normal Java Object vs Spring Bean

## Normal Java Object

```java
UserService service =
        new UserService();
```

Created by:

```text
Developer
```

Managed by:

```text
Developer
```

Lifecycle controlled by:

```text
Developer / JVM
```

---

## Spring Bean

```java
@Service
public class UserService {

}
```

Created by:

```text
Spring Container
```

Managed by:

```text
Spring Container
```

Lifecycle controlled by:

```text
Spring Container
```

---

# Definition of a Bean

A Bean is:

> An object whose complete lifecycle is managed by the Spring Container.

Important observation:

```text
Every Bean is an Object

But

Every Object is NOT a Bean
```

Only objects managed by Spring become Beans.

---

# Real World Analogy

Imagine a company.

Official employees are registered in the HR system.

HR knows:

* Employee Name
* Department
* Role
* Reporting Manager

These employees are actively managed.

Now imagine a visitor entering the building.

The visitor exists.

However, HR does not manage that person.

Similarly:

```text
Spring Managed Object
        =
Bean
```

```text
Developer Managed Object
        =
Normal Java Object
```

---

# How Does a Class Become a Bean?

Spring does not automatically manage every class.

The developer must tell Spring:

> "Please create and manage this object."

This is done using annotations.

Examples:

```java
@Component
```

```java
@Service
```

```java
@Repository
```

```java
@Controller
```

```java
@RestController
```

When Spring discovers one of these annotations during startup, it creates a Bean.

Example:

```java
@Service
public class UserService {

}
```

Startup flow:

```text
Application Starts
       |
Component Scan
       |
@Service Found
       |
Create Object
       |
Store In Container
       |
Bean Ready
```

---

# Bean Creation Process

Suppose Spring discovers:

```java
@Service
public class UserService {

}
```

The container performs:

```text
Step 1:
Identify Bean Candidate

Step 2:
Create Object

Step 3:
Store Bean

Step 4:
Inject Dependencies

Step 5:
Initialize Bean

Step 6:
Bean Ready For Use
```

---

# Where Are Beans Stored?

Conceptually, Spring stores Beans in an internal registry.

Mental model:

```java
Map<String, Object>
```

Example:

```text
Bean Name           Bean Object

userService     -> UserService Instance

emailService    -> EmailService Instance

paymentService  -> PaymentService Instance
```

Actual implementation is more sophisticated, but this model is useful for learning.

---

# Bean Name

Every Bean has a unique name.

Example:

```java
@Service
public class UserService {

}
```

Default Bean Name:

```text
userService
```

Spring converts:

```text
UserService
```

to:

```text
userService
```

by lowercasing the first character.

---

## Custom Bean Name

Example:

```java
@Service("paymentProcessor")
public class PaymentService {

}
```

Bean Name:

```text
paymentProcessor
```

---

# Bean Metadata

Spring stores much more than just the object.

For every Bean, Spring keeps metadata.

Example:

```text
Bean Name
Bean Type
Scope
Dependencies
Lifecycle Information
Proxy Information
```

Conceptually:

```text
Bean
 |
 +--- Name
 |
 +--- Type
 |
 +--- Scope
 |
 +--- Dependencies
 |
 +--- Lifecycle Details
```

This metadata helps Spring manage the Bean throughout the application's lifecycle.

---

# Bean Lifecycle (High-Level View)

Every Bean passes through a lifecycle.

```text
Bean Definition Found
        |
Bean Created
        |
Dependencies Injected
        |
Initialization
        |
Ready For Use
        |
Destroyed
```

Later chapters will cover the lifecycle in detail.

---

# Are All Objects Beans?

No.

Example:

```java
User user = new User();
```

Bean?

```text
NO
```

Spring is not managing it.

---

Example:

```java
List<String> list =
        new ArrayList<>();
```

Bean?

```text
NO
```

Spring is not managing it.

---

Example:

```java
@Service
public class UserService {

}
```

Bean?

```text
YES
```

Spring manages it.

---

# Why Beans Are Important

Many Spring features only work on Beans.

Examples:

## Dependency Injection

```java
@Autowired
```

Requires Beans.

---

## Transactions

```java
@Transactional
```

Requires Beans.

---

## Security

```java
@PreAuthorize
```

Requires Beans.

---

## Caching

```java
@Cacheable
```

Requires Beans.

---

## AOP

```java
@Aspect
```

Requires Beans.

---

Without Beans, Spring cannot provide these capabilities.

---

# Production Relevance

Understanding Beans is important when troubleshooting issues such as:

```text
No qualifying bean found

Bean creation failed

Bean currently in creation

Unsatisfied dependency
```

Most Spring startup failures are related to Bean creation or dependency resolution.

---

# Interview Questions

## What is a Bean?

A Bean is an object whose lifecycle is managed by the Spring Container.

---

## Is every object a Bean?

No.

Only Spring-managed objects are Beans.

---

## How does a class become a Bean?

Through:

```java
@Component
@Service
@Repository
@Controller
@RestController
@Bean
```

---

## Who creates Beans?

The Spring IoC Container.

---

## Where are Beans stored?

Inside the Spring Container.

Conceptually similar to:

```java
Map<String, Object>
```

---

## Why are Beans important?

Because Dependency Injection, AOP, Transactions, Security, and many other Spring features operate on Beans.

---

# Key Takeaways

1. Beans are Spring-managed objects.
2. Every Bean is an Object, but every Object is not a Bean.
3. Spring creates, stores, manages, and destroys Beans.
4. Beans are discovered during component scanning or explicit configuration.
5. Beans are stored inside the IoC Container.
6. Many Spring features require Beans to function.
7. Understanding Beans is essential before learning Dependency Injection.
