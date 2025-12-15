package com.example.conductor;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @PostMapping("/register")
    public String register() {
        workflowService.registerWorkflow();
        return "Workflow registered successfully";
    }

    @PostMapping("/run")
    public String run(@RequestParam String message) {
        return "Started Workflow ID: " + workflowService.executeWorkflow(message);
    }
}
