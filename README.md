# Spring Reactive

#### A comprehensive explanation of why we need a reactive tech stack even though we have a widespread traditional tech stack.

---

## Why Reactive?

Before diving into why reactive programming is needed, it's essential to understand the problems it resolves that exist in the traditional servlet-based tech stack.

---

## Major Problems Spring Reactive Solves Compared to Traditional Spring Boot Applications

---

### 1. Thread Blocking and Resource Inefficiency
- Traditional Spring Boot apps use a **one-thread-per-request model** where each incoming HTTP request occupies a dedicated thread until completion.
- This leads to thread blocking when waiting on slow operations (e.g., I/O, database calls), which wastes resources and limits scalability.
- Reactive Spring uses a **non-blocking, event-loop model** with fewer threads that can handle many concurrent requests without blocking.
- This reduces memory and CPU overhead and improves the ability to handle a large number of simultaneous users with limited threads.

---

### 2. Scalability and Concurrency
- Traditional blocking I/O models struggle with scaling to high loads because threads become saturated waiting on blocking calls.
- Reactive programming enables **asynchronous, event-driven processing**, increasing throughput and better utilizing CPU cores.
- It can scale with smaller hardware resources due to its efficient concurrency model.

---

### 3. Responsiveness and Latency
- Reactive systems can reduce latency by freeing up threads earlier and enabling more timely responses under load.
- Asynchronous processing better supports real-time streaming and backpressure management, useful for reactive data streams and live updates.

---

### 4. Backpressure and Stream Handling
- Traditional Spring MVC doesn't have built-in support for backpressure.
- Spring Reactive (WebFlux) supports backpressure, allowing consumer-driven control of data flow to prevent overwhelming clients or services.

---

### 5. Memory Footprint and CPU Utilization Tradeoff
- Reactive apps use fewer threads, thus smaller memory footprint, but often utilize more CPU due to context switching and event loop management.
- This tradeoff lets applications handle many more requests concurrently than traditional thread-blocking applications.

---

## Summary

Reactive programming in Spring addresses critical shortcomings of the traditional Spring Boot servlet stack by enabling non-blocking, asynchronous, and event-driven architectures. This modern approach improves scalability, resource efficiency, and responsiveness, especially for high-concurrency and real-time applications.

Understanding these differences helps in choosing the right technology stack for your application needs and future-proofs your solution for modern software demands.


### Traditional vs reactive stack
![alt text](/images/traditional_vs_reactive.png "Traditional vs Reactive stack")

### Solution for traditional blocking IO
* Configure tomcat to allow more which eventually required more system resources
* Vertical scaling, add more cpu ram memory to exisiting server
* Horizontal scaling, add more server to handle more request
* Virtual thread, configure tomcat to use virtual thread(allowed in tomcat 10.x or later)
* Reactive programming or non blocking IO

## Table of Contents

- [Introduction to traditional(Blocking) Applications](/Notes/intro_traditional_blocking_application.md)
- [Introduction to Reactive Programming](/Notes/intro_reactive_programming.md)
- [Getting Started](#getting-started)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [FAQ](#faq)
- [Contact](#contact)

---