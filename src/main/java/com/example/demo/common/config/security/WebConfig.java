package com.example.demo.common.config.security;

import com.example.demo.common.annotation.currentUser.CurrentUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CurrentUserArgumentResolver currentUserArgumentResolver;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // Tự động thêm tiền tố "/api" cho tất cả các Controller được đánh dấu @RestController
        configurer.addPathPrefix("/api", c ->
                // 1. Phải là RestController
                c.isAnnotationPresent(RestController.class)
                        // 2. VÀ KHÔNG PHẢI là các controller hệ thống của Swagger (thuộc package springdoc)
                        && !c.getPackageName().startsWith("org.springdoc")
        );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add((HandlerMethodArgumentResolver) currentUserArgumentResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cấu hình: ánh xạ url /uploads/** vào thư mục vật lý uploads/ trên ổ cứng server
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
