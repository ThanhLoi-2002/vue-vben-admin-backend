package com.example.demo.modules.sys.auth;

import com.example.demo.common.annotation.Public.Public;
import com.example.demo.common.annotation.app.ResponseMessage;
import com.example.demo.common.service.JwtService;
import com.example.demo.modules.sys.auth.dto.request.LoginRequest;
import com.example.demo.modules.sys.auth.dto.request.RegisterRequest;
import com.example.demo.modules.sys.auth.dto.response.LoginResponse;
import com.example.demo.modules.sys.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Public
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public LoginResponse register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody @Valid LoginRequest request,
            HttpServletResponse response
    ) {
        LoginResponse loginResponse = authService.login(request);
        // Tạo HttpOnly Cookie chứa refreshToken
        ResponseCookie cookie = ResponseCookie.from("refreshToken", loginResponse.getRefreshToken())
                .httpOnly(true)          // Chống XSS (JS không đọc được)
                .secure(false)           // Đặt true nếu chạy HTTPS (production)
                .path("/")               // Có hiệu lực toàn bộ domain
                .maxAge(jwtService.refreshTokenTime)// Sống trong 7 ngày
                .sameSite("Strict")      // Chống CSRF
                .build();

        // Đưa cookie vào Response Header
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return authService.login(request);
    }

    @PostMapping("/logout")
    @ResponseMessage("success")
    public void logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0) // Hủy cookie ngay lập tức
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

//    @PostMapping("/refresh")
//    public Map refresh(@RequestBody Map<String, String> request) {
//        String refreshToken = request.get("refreshToken");
//
//        return Map.of(
//                "token", authService.newToken(refreshToken)
//        );
//    }

    // 2. Làm mới token: Lấy refreshToken từ Cookie để cấp accessToken mới
    @PostMapping("/refresh")
    public Map refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token không tồn tại");
        }

        return Map.of(
                "accessToken", authService.newToken(refreshToken)
        );
    }
}
