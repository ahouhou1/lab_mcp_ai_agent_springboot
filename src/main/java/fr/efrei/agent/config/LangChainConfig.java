package fr.efrei.agent.config;

import dev.langchain4j.model.anthropic.AnthropicChatModel;
import dev.langchain4j.service.AiServices;
import fr.efrei.agent.BacklogAgent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class LangChainConfig {

    @Bean
    public AnthropicChatModel anthropicChatModel(
            @Value("${anthropic.api-key}") String apiKey,
            @Value("${anthropic.model}") String model,
            @Value("${anthropic.timeout-seconds:60}") Integer timeoutSeconds
    ) {
        return AnthropicChatModel.builder()
                .apiKey(apiKey)
                .modelName(model)
                .timeout(Duration.ofSeconds(timeoutSeconds))
                .build();
    }

    @Bean
    public BacklogAgent backlogAgent(AnthropicChatModel model) {
        // Correction : .chatModel() au lieu de .chatLanguageModel()
        return AiServices.builder(BacklogAgent.class)
                .chatModel(model)
                .build();
    }
}