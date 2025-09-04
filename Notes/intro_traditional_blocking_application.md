# Introduction to Traditional (Blocking) Applications

Traditional applications often use a blocking I/O model, where each request is handled by a dedicated thread. The thread waits (or "blocks") for operations like database access, file I/O, or network calls to complete before proceeding. This approach is simple to implement and reason about, but it can lead to scalability issues as the number of concurrent users increases.

**Key Characteristics:**
- Each request is assigned a separate thread.
- Threads remain idle while waiting for I/O operations.
- Limited scalability due to thread resource constraints.
- Easier to debug and maintain for simple use cases.

**Common Use Cases:**
- Monolithic web applications
- Legacy enterprise systems
- Applications with low to moderate concurrency requirements

Understanding the limitations of blocking applications is essential before exploring non-blocking or reactive alternatives.

# Thread Per Request Architecture

The **Thread Per Request** architecture is a common server-side design pattern where each incoming client request is handled by a dedicated thread. This approach is widely used in traditional web servers and application servers.

## How It Works

1. **Client sends a request** to the server.
2. **Server allocates a new thread** (or reuses one from a thread pool) to handle the request.
3. **Thread processes the request**, performs necessary operations (I/O, computation, etc.).
4. **Response is sent** back to the client.
5. **Thread is released** (or returned to the pool) after the request is completed.

## Advantages

- **Simplicity:** Easy to understand and implement.
- **Isolation:** Each request is isolated in its own thread, reducing interference.

## Disadvantages

- **Resource Intensive:** Each thread consumes memory and CPU resources.
- **Scalability Issues:** Limited by the maximum number of threads the server can handle.
- **Thread Overhead:** Context switching and synchronization can degrade performance under high load.

## Use Cases

- Suitable for applications with a moderate number of concurrent users.
- Not ideal for highly concurrent or I/O-bound applications.

## Alternatives

- Event-driven (reactive) architectures
- Asynchronous/non-blocking I/O models


### Thread per request architecture diagram
![alt text](/images/thread_per_request.png "Thread per request architecture")
------------------------------------------------------------------------------

# Traditional (Blocking) Spring MVC REST Application

A traditional Spring MVC REST application uses a servlet-based, thread-per-request model. Each HTTP request is handled by a dedicated thread, which blocks while waiting for I/O operations (like database calls or external API requests) to complete.

## Key Characteristics

- **Servlet API based**: Uses `DispatcherServlet` and servlet containers (e.g., Tomcat).
- **Thread-per-request**: Each request is assigned a thread from the server's thread pool.
- **Blocking I/O**: Threads wait (block) for I/O operations to finish.
- **Synchronous processing**: Code executes sequentially, making it easier to reason about.

## Example Controller

```java
@RestController
@RequestMapping("/api")
public class SampleController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
```

## Pros and Cons

**Pros:**
- Simple programming model
- Mature ecosystem and wide adoption

**Cons:**
- Not suitable for high concurrency with limited resources
- Threads are blocked during I/O, leading to scalability issues

## When to Use

- Applications with moderate traffic
- When simplicity and familiarity are priorities
- When most operations are CPU-bound or fast I/O

### Traditional (Blocking) Spring MVC REST Diagram
![alt text](/images/thread_per_request.png "Traditional (Blocking) Spring MVC REST Diagram")

