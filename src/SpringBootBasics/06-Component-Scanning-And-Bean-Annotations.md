# Chapter 06 - Component Scanning & Bean Creation Annotations

## Introduction

In the previous chapters, we learned:

* IoC (Inversion of Control)
* ApplicationContext
* Beans
* Dependency Injection

A major question still remained:

```text
How does Spring know which classes
should become Beans?
```

We never wrote:

```java
new UserService();

new UserController();

new MySqlUserRepository();
```

Yet Spring automatically created and managed these objects.

The answer is:

```text
Component Scanning
```

---

# What is Component Scanning?

Definition:

```text
Component Scanning is the process by which
Spring automatically scans packages,
finds eligible classes, and registers
them as Beans inside the IoC Container.
```

---

# Why Component Scanning?

Without Component Scanning:

Every Bean must be registered manually.

Example:

```java
@Configuration
public class AppConfig {

    @Bean
    public UserRepository userRepository() {
        return new MySqlUserRepository();
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }
}
```

For small applications this is manageable.

For large applications containing hundreds of classes, this becomes difficult.

Spring solves this through automatic discovery.

---

# How Component Scanning Works

Application Startup:

```java
@SpringBootApplication
public class BillingServiceApplication {
}
```

Spring starts scanning from:

```text
Package of Main Class
```

and all its subpackages.

Example:

```text
com.example.demo
```

Spring scans:

```text
com.example.demo
com.example.demo.controller
com.example.demo.service
com.example.demo.repository
com.example.demo.config
```

and every subpackage beneath it.

---

# Component Scan Flow

```text
Application Start
       |
Create ApplicationContext
       |
Start Component Scan
       |
Find Bean Candidates
       |
Create Bean Definitions
       |
Create Beans
       |
Inject Dependencies
       |
Application Ready
```

---

# Important Rule

Spring scans:

```text
Main Class Package
+
All Subpackages
```

Example:

```text
com.example.demo
       |
       +--- controller
       |
       +--- service
       |
       +--- repository
```

All classes under these packages are eligible for scanning.

---

# What Happens If Class Is Outside Scan Path?

Example:

Main Class:

```text
com.example.demo
```

Repository:

```text
com.company.repository
```

Spring will NOT discover it automatically.

Result:

```text
NoSuchBeanDefinitionException

or

UnsatisfiedDependencyException
```

during startup.

---

# Bean Creation Annotations

Spring identifies Bean candidates using special annotations.

These are called:

```text
Stereotype Annotations
```

---

# @Component

Base Bean annotation.

Example:

```java
@Component
public class EmailService {
}
```

Meaning:

```text
Generic Spring Bean
```

Bean Created?

```text
YES
```

---

# @Service

Example:

```java
@Service
public class UserService {
}
```

Meaning:

```text
Business Logic Layer
```

Bean Created?

```text
YES
```

---

# @Repository

Example:

```java
@Repository
public class UserRepository {
}
```

Meaning:

```text
Persistence / Database Layer
```

Bean Created?

```text
YES
```

Additional Benefit:

```text
Database exception translation
```

provided by Spring.

---

# @Controller

Example:

```java
@Controller
public class HomeController {
}
```

Meaning:

```text
MVC Controller
```

Bean Created?

```text
YES
```

Used primarily for:

```text
JSP
Thymeleaf
HTML Views
```

---

# @RestController

Example:

```java
@RestController
public class UserController {
}
```

Meaning:

```text
REST API Controller
```

Bean Created?

```text
YES
```

---

# Difference Between @Controller and @RestController

## @Controller

Returns:

```text
View Name
```

Example:

```java
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
```

Spring searches for:

```text
home.html
```

---

## @RestController

Returns:

```text
Data
```

Example:

```java
@RestController
public class UserController {

    @GetMapping("/user")
    public String getUser() {
        return "Sahib";
    }
}
```

Response:

```text
Sahib
```

directly in HTTP response body.

---

# Internal Relationship

```text
@Component
      |
      +---- @Service
      |
      +---- @Repository
      |
      +---- @Controller
      |
      +---- @RestController
```

These annotations are specialized forms of:

```java
@Component
```

---

# @ResponseBody

Purpose:

```text
Return data directly in HTTP response.
```

Example:

```java
@Controller
@ResponseBody
public class UserController {
}
```

Equivalent to:

```java
@RestController
public class UserController {
}
```

---

# @Configuration

Definition:

```text
Class containing Bean definitions.
```

Example:

```java
@Configuration
public class AppConfig {

}
```

Think:

```text
Configuration Class
=
Bean Factory Class
```

---

# @Bean

Definition:

```text
Method whose returned object
becomes a Spring Bean.
```

Example:

```java
@Bean
public EmailService emailService() {

    return new EmailService();
}
```

Spring takes the returned object and stores it inside the IoC Container.

---

# Why Use @Bean?

Used when:

```text
Class cannot be modified.
```

Examples:

```text
RedisClient
KafkaProducer
ObjectMapper
RestTemplate
AWS Clients
Third Party Libraries
```

We cannot add:

```java
@Component
```

to their source code.

Therefore:

```java
@Configuration
public class AppConfig {

    @Bean
    public RedisClient redisClient() {

        return new RedisClient(
                "localhost",
                6379
        );
    }
}
```

---

# Difference Between @Component and @Bean

## @Component

```text
Spring creates object.
```

Example:

```java
@Service
public class UserService {
}
```

Spring internally creates:

```java
new UserService();
```

---

## @Bean

```text
Developer creates object.
Spring manages object.
```

Example:

```java
@Bean
public UserService userService() {

    return new UserService();
}
```

Developer controls object creation.

---

# Difference Between @Bean and @Configuration

## @Bean

```text
Creates Bean
```

Example:

```java
@Bean
public UserService userService()
```

---

## @Configuration

```text
Contains Bean definitions
```

Example:

```java
@Configuration
public class AppConfig
```

---

Relationship:

```text
@Configuration
       |
       +--- @Bean
       +--- @Bean
       +--- @Bean
```

---

# Real Project Example

Current Project:

```text
UserController
      |
UserService
      |
MySqlUserRepository
```

Annotations Used:

```java
@RestController
```

```java
@Service
```

```java
@Repository
```

Spring discovers these through Component Scanning and automatically registers them as Beans.

---

# Interview Questions

## What is Component Scanning?

```text
Component Scanning is the process by which Spring automatically discovers classes annotated with stereotype annotations and registers them as Beans.
```

---

## From Where Does Component Scanning Start?

```text
Package of Main Class
+
All Subpackages
```

---

## Difference Between @Component and @Service?

```text
Bean creation is identical.

@Service provides semantic meaning
for business logic classes.
```

---

## Difference Between @Controller and @RestController?

```text
@Controller returns Views.

@RestController returns Data.

@RestController
=
@Controller + @ResponseBody
```

---

## What is @Bean?

```text
Marks a method whose return value
should become a Spring Bean.
```

---

## What is @Configuration?

```text
Class used to define Bean creation methods.
```

---

# Key Takeaways

1. Spring automatically discovers Beans using Component Scanning.
2. Scanning starts from the package containing the Main Class.
3. @Component is the base stereotype annotation.
4. @Service, @Repository, @Controller, and @RestController are specialized forms of @Component.
5. @RestController is used for REST APIs.
6. @Controller is used for MVC/View-based applications.
7. @Bean allows manual Bean creation.
8. @Configuration groups Bean definitions.
9. Component-based Beans are created automatically.
10. @Bean-based Beans are created manually and managed by Spring.
