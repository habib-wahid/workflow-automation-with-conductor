# Conductor Workflow Example

Spring Boot application demonstrating Netflix Conductor workflow orchestration with external event handling and third-party service integration.

## Features

- Programmatic workflow definition and registration
- External event integration with WAIT tasks
- Custom worker implementations
- Third-party callback handling
- RabbitMQ integration for event publishing

## Prerequisites

- Java 21
- Gradle 7.x+
- Netflix Conductor Server (default: http://localhost:8080)
- RabbitMQ Server (optional, default: localhost:5672)

## Getting Started

### 1. Start Conductor Server

```bash
docker run -p 8080:8080 -p 5000:5000 conductor/conductor-standalone:latest
```

### 2. Start RabbitMQ (Optional)

```bash
docker run -d -p 5672:5672 -p 15672:15672 rabbitmq:management
```

### 3. Run the Application

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8081`

## Configuration

Update `src/main/resources/application.properties`:

```properties
orkes.conductor.server.url=http://localhost:8080/api
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
```

## API Endpoints

### Workflow Management

**Start Workflow**
```bash
POST http://localhost:8081/api/workflow/start
Content-Type: application/json

{
  "message": "Hello Conductor"
}
```

**Get Workflow Status**
```bash
GET http://localhost:8081/api/workflow/{workflowId}
```

### External Callback

**Complete Wait Task** (for third-party responses)
```bash
POST http://localhost:8081/api/callback/complete-wait
Content-Type: application/json

{
  "taskId": "task-id-from-worker",
  "responseData": "external-response-data"
}
```

### RabbitMQ

**Publish Message**
```bash
POST http://localhost:8081/api/rabbitmq/publish
Content-Type: application/json

{
  "content": "Test message",
  "sender": "user"
}
```

## Workflow Architecture

The workflow implements a pause-and-resume pattern for external event handling:

1. **Task 1**: Worker publishes event to third-party service and returns task ID
2. **WAIT Task**: Workflow pauses until external callback is received
3. **Task 2**: Processes the third-party response data

```
[Start] → [Task1: Publish Event] → [WAIT] → [Task2: Process Response] → [End]
                                       ↑
                                       |
                                [External Callback]
```

## Project Structure

```
src/main/java/com/example/conductor/
├── ConductorApplication.java       # Main application
├── ConductorConfig.java            # Conductor client configuration
├── RabbitMQConfig.java             # RabbitMQ configuration
├── WorkflowController.java         # Workflow API endpoints
├── WorkflowService.java            # Workflow business logic
├── CallbackController.java         # External callback handler
├── SampleWorker.java               # Task worker implementation
├── RabbitMQPublisher.java          # Message publisher
└── RabbitMQConsumer.java           # Message consumer
```

## Key Components

### Workers
Workers implement task execution logic and can access workflow context:
- `task.getWorkflowInstanceId()` - Get workflow ID
- `task.getTaskId()` - Get task ID for callbacks
- `task.getInputData()` - Access input parameters

### Wait Tasks
Used to pause workflow execution until external events complete. The workflow resumes when `TaskClient.updateTask()` is called with the task ID.

## Use Cases

- Payment processing with gateway confirmations
- Document approval workflows
- External API integration with callbacks
- Microservice orchestration
- Long-running asynchronous processes

## Dependencies

- Spring Boot 3.4.12
- Netflix Conductor Client 4.1.0
- Spring AMQP (RabbitMQ)

## License

MIT

