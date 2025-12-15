package com.example.conductor;

import com.netflix.conductor.client.http.MetadataClient;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.http.WorkflowClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import com.netflix.conductor.client.http.ConductorClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConductorConfig {

    @Value("${orkes.conductor.server.url}")
    private String rootUri;

    @Bean
    public ConductorClient conductorClient() {
        return new ConductorClient(rootUri);
    }

    @Bean
    public TaskClient taskClient() {
        return new TaskClient(conductorClient());
    }

    @Bean
    public WorkflowClient workflowClient() {
        return new WorkflowClient(conductorClient());
    }

    @Bean
    public MetadataClient metadataClient() {
        return new MetadataClient(conductorClient());
    }

}
