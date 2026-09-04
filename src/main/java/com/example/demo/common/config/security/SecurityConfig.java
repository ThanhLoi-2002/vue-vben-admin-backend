package com.example.demo.common.config.security;

import com.example.demo.common.annotation.Public.Public;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final RequestMappingHandlerMapping handlerMapping;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Đưa JwtFilter vào trước UsernamePasswordAuthenticationFilter (chuẩn Spring Security)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests(auth -> auth
                                // 1. Cho phép các request hệ thống/Preflight/Auth cơ bản
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers("/ws/**").permitAll()

                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/v3/api-docs/**"
                                ).permitAll()

                                // 2. Mặc định tất cả các route còn lại đều phải check qua Custom AuthorizationManager
                                .anyRequest().access((authentication, context) -> {
                                    assert context != null;
                                    HttpServletRequest request = context.getRequest();
                                    boolean isPublic = false;

                                    try {
                                        // Kiểm tra xem route hiện tại có gắn annotation @PublicEndpoint hay không
                                        var handler = handlerMapping.getHandler(request);
                                        if (handler != null && handler.getHandler() instanceof HandlerMethod hm) {
                                            if (hm.hasMethodAnnotation(Public.class) || hm.getBeanType().isAnnotationPresent(Public.class)) {
                                                isPublic = true;
                                            }
                                        }
                                    } catch (Exception e) {
                                        // Nếu có lỗi khi trích xuất handler, mặc định không cho qua
                                    }

                                    // Nếu là public endpoint -> Cho phép truy cập không cần token
                                    if (isPublic) {
                                        return new AuthorizationDecision(true);
                                    }

                                    // Ngược lại: Bắt buộc phải được xác thực (JwtFilter đã set Authentication vào SecurityContext)
                                    boolean isAuthenticated = authentication.get() != null && authentication.get().isAuthenticated()
                                            // Thêm điều kiện này để loại bỏ khách vãng lai (anonymousUser)
                                            && !(authentication.get() instanceof org.springframework.security.authentication.AnonymousAuthenticationToken);
//                                    System.out.println(isAuthenticated);
//                                    System.out.println(G.toJson(authentication.get()));
                                    return new AuthorizationDecision(isAuthenticated);
                                })
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Dùng allowedOriginPatterns thay cho allowedOrigins để bypass lỗi của Spring
        configuration.setAllowedOriginPatterns(List.of("*"));
//        configuration.setAllowedOrigins(List.of(
//                "http://localhost:8100",
//                "http://127.0.0.1:8100",
//                "http://10.0.111.38:8100",
//                "http://10.0.2.2:8100",
//                "https://chat-realtime-java-ionic.vercel.app/"
//        ));

        configuration.setAllowedMethods(List.of("*"));

        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}