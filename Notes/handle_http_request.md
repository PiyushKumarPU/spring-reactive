
# Handling HTTP Requests in Spring WebFlux

This document covers the essential concepts for building a reactive REST API using Spring WebFlux. We'll explore how to create controllers, handle different HTTP methods, validate requests, and work with reactive types.

## Introduction to Presentation Layer

The presentation layer in Spring WebFlux is responsible for handling HTTP requests and responses. It acts as the interface between your application and external clients, processing incoming requests and returning appropriate responses in a reactive, non-blocking manner.

## @RestController: Creating and Configure the RestController Class

The `@RestController` annotation combines `@Controller` and `@ResponseBody`, indicating that the class handles HTTP requests and returns data directly in the response body.

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    // Controller methods here
}
```

## @PostMapping: Handle HTTP Post Request

`@PostMapping` maps HTTP POST requests to specific handler methods, typically used for creating new resources.

```java
@PostMapping
public Mono<User> createUser(@RequestBody User user) {
    return userService.createUser(user);
}
```

## @RequestBody: Reading HTTP Request Body

`@RequestBody` annotation binds the HTTP request body to a method parameter, automatically deserializing JSON/XML to Java objects.

```java
@PostMapping("/users")
public Mono<User> addUser(@RequestBody User user) {
    // Process the user object from request body
    return userService.save(user);
}
```

## @Validated: Validating HTTP Request Body

`@Validated` enables validation on request bodies using Bean Validation annotations like `@NotNull`, `@Size`, etc.

```java
@PostMapping("/users")
public Mono<User> createUser(@Validated @RequestBody User user) {
    return userService.createUser(user);
}
```

## Trying if Validation Works

Test validation by sending invalid data and observing error responses. Spring WebFlux automatically returns validation errors with appropriate HTTP status codes.

## Mono: Returning a Single String Value in HTTP Response Body

`Mono<String>` represents a reactive stream that emits at most one string value.

```java
@GetMapping("/welcome")
public Mono<String> welcome() {
    return Mono.just("Welcome to Spring WebFlux!");
}
```

## Mono: Returning a User Object in HTTP Response Body

`Mono<User>` returns a single User object asynchronously.

```java
@GetMapping("/users/{id}")
public Mono<User> getUser(@PathVariable String id) {
    return userService.findById(id);
}
```

## ResponseEntity: Returning Custom HTTP Status Code

`ResponseEntity` allows you to customize the HTTP response status, headers, and body.

```java
@PostMapping("/users")
public Mono<ResponseEntity<User>> createUser(@RequestBody User user) {
    return userService.createUser(user)
        .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body(savedUser));
}
```

## @GetMapping: Handle HTTP GET Request

`@GetMapping` maps HTTP GET requests to handler methods, typically used for retrieving resources.

```java
@GetMapping("/users/{id}")
public Mono<User> getUser(@PathVariable String id) {
    return userService.findById(id);
}
```

## Flux: Returning Multiple Objects in HTTP Response Body

`Flux<User>` represents a reactive stream that can emit zero to many User objects.

```java
@GetMapping("/users")
public Flux<User> getAllUsers() {
    return userService.findAll();
}
```

## @RequestParam: Reading URL Query String Parameters

`@RequestParam` extracts query parameters from the URL.

```java
@GetMapping("/users")
public Flux<User> getUsers(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size) {
    return userService.findUsers(page, size);
}
```

## WebFlux vs WebMVC: Key Similarities

While Spring WebFlux introduces reactive programming concepts, the request handling patterns remain largely familiar to WebMVC developers:

- **Annotations**: Same annotations (`@RestController`, `@GetMapping`, `@PostMapping`, `@RequestBody`, `@RequestParam`) work in both frameworks
- **Request Mapping**: URL mapping and parameter extraction follow identical patterns
- **Validation**: Bean validation using `@Validated` works the same way
- **Response Handling**: `ResponseEntity` usage is consistent across both frameworks

The primary difference lies in the return types - WebFlux uses `Mono` and `Flux` for reactive streams, while WebMVC returns objects directly or wrapped in `ResponseEntity`.

## Source code
[Source Code](/src/request-handling/)