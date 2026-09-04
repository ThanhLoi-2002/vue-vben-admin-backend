package com.example.demo.common.config.security;

import com.example.demo.common.service.JwtService;
import com.example.demo.modules.sys.sys002user.dto.request.UserPayload;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && !authHeader.isBlank()) {

            String token = authHeader;

            // Nếu chuỗi bắt đầu bằng "Bearer " (có phân biệt hoa thường hoặc không), hãy cắt nó đi
            if (authHeader.startsWith("Bearer ") || authHeader.startsWith("bearer ")) {
                token = authHeader.substring(7); // Cắt bỏ 7 ký tự đầu tiên ("Bearer ")
            }

            try {
                UserPayload user = jwtService.getUserByToken(token, "accessToken");
//                List<GrantedAuthority> authorities = user.getPermissions().stream()
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());

                // Tạo đối tượng Authentication của Spring Security, nhét "user" vào phần Principal
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, null); // Có thể truyền authorities vào tham số thứ 3 nếu muốn

                //lưu trữ các thông tin kỹ thuật của HTTP request hiện tại
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Đẩy vào kho lưu trữ bảo mật
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ResponseStatusException e) {
                System.out.println("JWT Authentication failed ResponseStatusException: " + e.getReason());

                // 1. Set mã lỗi HTTP (ví dụ: 401 hoặc 404 dựa theo e.getStatusCode())
                response.setStatus(e.getStatusCode().value());

                // 2. Định nghĩa kiểu trả về là JSON
                response.setContentType("application/json;charset=UTF-8");

                // 3. Ghi trực tiếp thông điệp lỗi vào Body của response
                response.getWriter().write("{\"status\":" + e.getStatusCode().value() + ",\"message\":\"" + e.getReason() + "\"}");

                // 4. Lệnh "return" cực kỳ quan trọng để chặn request không đi tiếp vào controller
                return;

            } catch (Exception e) {
                System.out.println("JWT Authentication failed General Exception: " + e.getMessage());

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"status\":401,\"message\":\"unauthorized\"}");

                // Chặn request đi tiếp
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
