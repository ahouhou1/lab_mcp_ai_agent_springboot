package fr.efrei.agent.config;

import fr.efrei.agent.tools.AgentTool;
import dev.langchain4j.model.anthropic.AnthropicChatModel;
import dev.langchain4j.model.chat.ChatModel; //
import dev.langchain4j.service.AiServices;
import fr.efrei.agent.BacklogAgent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile; //

import java.time.Duration;
import java.util.List;

@Configuration
public class LangChainConfig {

    @Bean
    @Profile("!ci") //
    public AnthropicChatModel anthropicChatModel(
            @Value("${anthropic.api-key}") String apiKey,
            @Value("${anthropic.model}") String model,
            @Value("${anthropic.max-tokens:800}") Integer maxTokens,
            @Value("${anthropic.timeout-seconds:60}") Integer timeoutSeconds
    ) {
        return AnthropicChatModel.builder()
                .apiKey(apiKey)
                .modelName(model)
                .maxTokens(maxTokens)
                .timeout(Duration.ofSeconds(timeoutSeconds))
                .build();
    }

    @Bean
    public BacklogAgent backlogAgent(ChatModel model, List<AgentTool> tools) { //
        var builder = AiServices.builder(BacklogAgent.class)
                .chatModel(model);

        if (tools != null && !tools.isEmpty()) {
            builder.tools(tools.toArray(new Object[0]));
        }

        return builder.build();
    }
}