package ru.practice.server.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SpringdocOpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                                .title("Information Service for Train")
                                .version("1.0.0")
                                .description("Интегрированный информационный сервис с многомерной функциональностью для управления вагонным составом на железнодорожных путях.")
                );
    }
}
