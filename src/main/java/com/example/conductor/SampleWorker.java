package com.example.conductor;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SampleWorker implements Worker {

    private final RabbitMQPublisher rabbitMQPublisher;

    public SampleWorker(RabbitMQPublisher rabbitMQPublisher) {
        this.rabbitMQPublisher = rabbitMQPublisher;
    }

    @Override
    public String getTaskDefName() {
        return "sample_worker_task";
    }

    @Override
    public TaskResult execute(Task task) {
        String workflowId = task.getWorkflowInstanceId();
        String message = (String) task.getInputData().get("message");

        System.out.println("Processing task for workflow ID: " + workflowId);
        System.out.println("Message: " + message);


        TaskResult result = new TaskResult(task);
        result.setStatus(TaskResult.Status.COMPLETED);
        result.getOutputData().put("result", "Processed for workflow: " + workflowId);

        return result;
    }

//    @WorkerTask("sample_worker_task")
//    public Map<String, Object> handleTask(Map<String, Object> input) {
//        String message = (String) input.get("message");
//
//        System.out.println("Received message: " + message);
//        Map<String, Object> output = new HashMap<>();
//        output.put("result", "Processed: " + message);
//        output.put("timestamp", System.currentTimeMillis());
//
//        MessageEvent messageEvent = new MessageEvent("1", message, "Worker", System.currentTimeMillis());
//        rabbitMQPublisher.publishMessage(messageEvent);
//
//        return output;
//    }
}
