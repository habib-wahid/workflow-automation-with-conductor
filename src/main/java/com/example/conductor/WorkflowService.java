package com.example.conductor;


import com.netflix.conductor.client.http.MetadataClient;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkflowService {
    private final WorkflowClient workflowClient;
    private final MetadataClient metadataClient;

    public WorkflowService(WorkflowClient workflowClient, MetadataClient metadataClient) {
        this.workflowClient = workflowClient;
        this.metadataClient = metadataClient;
    }

    // Programmatically define and register a workflow
    public void registerWorkflow() {
        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName("programmatic_workflow");
        workflowDef.setVersion(1);

        // Define a simple task within the workflow
        WorkflowTask task = new WorkflowTask();
        task.setName("sample_worker_task");
        task.setTaskReferenceName("task_1");
        task.setType("SIMPLE");

        WorkflowTask task2 = new WorkflowTask();
        task.setName("sample_worker_task_2");
        task.setTaskReferenceName("task_2");
        task.setType("SIMPLE");

        workflowDef.setTasks(List.of(task, task2));
        metadataClient.registerWorkflowDef(workflowDef);
    }

    // Programmatically start a workflow execution
    public String executeWorkflow(String inputMessage) {
        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName("programmatic_workflow");
        request.setVersion(1);

        Map<String, Object> input = new HashMap<>();
        input.put("message", inputMessage);
        request.setInput(input);


        //workflowClient.execute(request);
        return workflowClient.startWorkflow(request); // Returns Workflow ID
    }
}
