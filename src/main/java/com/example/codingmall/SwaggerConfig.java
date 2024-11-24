package com.example.codingmall;

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
    //http://localhost:8080/swagger-ui/index.html
    @Bean
    public OpenAPI openAPI() {
        String ngrokUrl = getNgrokUrl();
        return new OpenAPI()
                .info(new Info().title("Swagger Test")
                        .version("1.0.0")
                        .description("<h3>Swagger test</h3>"))
                .servers(List.of(
                        new Server().url(ngrokUrl).description("ngrok 환경"),
                        new Server().url("http://localhost:8080").description("로컬 환경")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(
                        new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  //모든 경로에 CORS 허용
                        .allowedOriginPatterns("https://*.ngrok-free.app", "http://localhost:3000") // ngrok 도메인 허용, 리액트 도메인 허용
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
    private String getNgrokUrl() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String tunnelAPI = "http://127.0.0.1:4040/api/tunnels";
            Map<String, Object> response = restTemplate.getForObject(tunnelAPI, Map.class);

            List<Map<String, Object>> tunnels = (List<Map<String, Object>>) response.get("tunnels");
            if(!tunnels.isEmpty()) {
                return (String) tunnels.get(0).get("public_url");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://localhost:8080";
    }
}
