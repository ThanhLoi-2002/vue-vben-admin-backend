package com.example.demo.common.doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                // Thêm yêu cầu bảo mật chung cho toàn bộ Swagger UI
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                // Định nghĩa cấu trúc của Token (JWT)
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .info(new Info()
                        .title("Chat App Sticker API - Documentation")
                        .version("1.0.0")
                        .description("Tài liệu hệ thống API tích hợp Sticker cho ứng dụng chat Vue + Capacitor")
                        .contact(new Contact()
                                .name("Developer Support")
                                .email("your-email@example.com")));
    }
}
