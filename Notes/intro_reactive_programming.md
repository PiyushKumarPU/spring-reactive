# Introduction to Reactive Programming

Reactive Programming is a programming paradigm focused on building asynchronous, non-blocking, and event-driven applications. It enables systems to handle a large number of concurrent operations efficiently by using observable data streams and propagating changes automatically.

## Spring WebFlux Overview

Spring WebFlux is a reactive web framework introduced in Spring 5. It is designed to support the development of fully asynchronous and non-blocking web applications using the reactive programming model. WebFlux leverages Project Reactor's `Mono` and `Flux` types to handle data streams and enables efficient resource utilization, making it suitable for high-concurrency scenarios. It can run on traditional servlet containers as well as reactive runtimes like Netty.

## Key Concepts

- **Asynchronous Data Streams:** Data is processed as it arrives, without blocking threads.
- **Event-driven Architecture:** Components react to events or data changes.
- **Backpressure:** Mechanism to handle situations where data producers outpace consumers.
- **Observables and Subscribers:** Core building blocks for emitting and consuming data.
- **Mono and Flux:** In Project Reactor (Java), `Mono` represents a stream that emits zero or one item, while `Flux` represents a stream that emits zero or more items. These abstractions help model different types of data flows in reactive systems.

## Benefits

- Improved scalability and resource utilization
- Enhanced responsiveness and resilience
- Simplified handling of complex asynchronous workflows

## Common Libraries

- **Java:** Project Reactor (`Mono`, `Flux`), RxJava
- **JavaScript:** RxJS

Reactive Programming is widely used in modern web applications, microservices, and systems requiring high throughput and low latency.

# Spring Framework and Reactive Specification

## Spring Framework

- A comprehensive framework for building Java applications.
- Supports dependency injection, aspect-oriented programming, data access, transaction management, and more.
- Widely used for building enterprise-level applications.

## Reactive Specification

- Defines a standard for asynchronous stream processing with non-blocking back pressure.
- Enables building scalable, resilient, and responsive applications.
- Core interfaces: `Publisher`, `Subscriber`, `Subscription`, and `Processor`.

## Spring and Reactive

- Spring 5 introduced reactive programming support via the `spring-webflux` module.
- Enables building non-blocking, event-driven applications using Project Reactor.
- Integrates with the Reactive Streams API.

## References

- [Spring Framework Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/)
- [Reactive Streams Specification](https://www.reactive-streams.org/)
- [Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)

### Spring Reactive Spec
![alt text](/images/spring_reactive_spec.png "Spring Reactive Spec")


## Key Features of Reactive Programming

- Enables developers to build **non-blocking applications** that can handle both asynchronous and synchronous operations.
- Focuses on **data streams** and the **propagation of change**.
- Useful for applications that need to handle a **large number of concurrent users** or **data streams efficiently**.
- Typically employs a **functional programming style** rather than an imperative one.

## Common Concepts and Tools

- **Reactive Streams** to handle data flow.
- **Lambda functions** for concise code.
- Operators like `map()` and `filter()` to process data.


## Introduction to Imperative Programming Style

Imperative programming is a programming paradigm that uses statements to change a program's state. In this style, you explicitly describe the steps that the computer must take to achieve a desired outcome. Most traditional programming languages like Java, C, and Python support imperative programming. It contrasts with declarative programming, where you describe what you want to achieve without explicitly listing commands or steps.

## Spring MVC REST Controller Example

Spring MVC allows you to build RESTful web services easily using annotations.

### Example: Simple REST Controller

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}
```

**Explanation:**  
- `@RestController` marks the class as a REST controller.
- `@GetMapping("/hello")` maps HTTP GET requests to the `sayHello` method.
- The method returns a simple string response.
----------

# Functional Programming Style

Functional programming is a paradigm where computation is treated as the evaluation of mathematical functions and avoids changing state or mutable data.

## Key Principles

- **Immutability:** Data objects are not modified after creation.
- **First-class functions:** Functions are treated as values and can be passed as arguments.
- **Pure functions:** Functions have no side effects and return the same output for the same input.
- **Higher-order functions:** Functions that take other functions as arguments or return them.
- **Declarative code:** Focus on what to do, not how to do it.
## Functional Programming Style in Spring Controllers

Spring supports both the traditional annotation-based controllers and a more functional style using `RouterFunction` and `HandlerFunction`. The functional style allows you to define routes and handlers in a more declarative and composable way.

### Example

```java
// Handler class
@Component
public class GreetingHandler {
    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().bodyValue("Hello, functional Spring!");
    }
}

// Router configuration
@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler handler) {
        return RouterFunctions
            .route(RequestPredicates.GET("/hello"), handler::hello);
    }
}
```

This approach emphasizes immutability and composition, which are key principles of functional programming.
--
# Data Streams in Reactive Applications

Reactive applications process data as streams, enabling asynchronous, non-blocking, and event-driven architectures. Data streams represent sequences of events or data items that can be observed and manipulated over time.

## Key Concepts

- **Publisher**: Emits data items (events) to subscribers.
- **Subscriber**: Consumes data items from the publisher.
- **Backpressure**: Mechanism to handle situations where data is produced faster than it can be consumed.
- **Operators**: Functions to transform, filter, or combine streams.

## Benefits

- Improved scalability and resource utilization.
- Responsive and resilient systems.
- Simplified asynchronous programming.

## Example (Pseudocode)

```java
Flux<Integer> numbers = Flux.range(1, 5)
    .map(n -> n * 2)
    .filter(n -> n > 5);

numbers.subscribe(System.out::println);
```