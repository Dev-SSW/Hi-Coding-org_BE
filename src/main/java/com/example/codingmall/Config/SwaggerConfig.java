package com.example.codingmall.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;

@Configuration
public class SwaggerConfig {
    //http://${IP or Domain}/swagger-ui/index.html
    @Bean
    public OpenAPI openAPI() {
        //String ngrokUrl = getNgrokUrl();
        return new OpenAPI()
                .info(new Info().title("Swagger Test")
                        .version("1.0.0")
                        .description("<h3>Swagger test</h3>"))
                .servers(List.of(
                        //new Server().url(ngrokUrl).description("ngrok 환경")
                        new Server().url("https://leoan.p-e.kr").description("배포 환경"),
                        new Server().url("http://localhost:8081").description("테스트 환경")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(
                        new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
