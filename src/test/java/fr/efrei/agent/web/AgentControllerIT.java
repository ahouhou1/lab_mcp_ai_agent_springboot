package fr.efrei.agent.web;

import fr.efrei.agent.BacklogAgent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AgentControllerIT {

    @Autowired
    WebTestClient web;

    @MockBean
    BacklogAgent backlogAgent;

    @Test
    void should_call_endpoint() {
        when(backlogAgent.handle(anyString())).thenReturn("Mocked response from Agent");

        web.post().uri("/api/agent/run")
                .header("Content-Type", "text/plain")
                .bodyValue("Create a task to add OpenTelemetry")
                .exchange()
                .expectStatus().isOk();
    }
}