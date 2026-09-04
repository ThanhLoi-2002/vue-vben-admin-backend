package com.example.demo.common.exception;

import com.example.demo.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "";

        if (ex instanceof ResponseStatusException e) {
            status = HttpStatus.valueOf(e.getStatusCode().value());
            message = e.getReason();
        }

        if (message == null || message.isBlank()) {
            message = ex.getMessage(); // fallback
        }

        System.out.println("message = " + ex.getMessage());
        ex.printStackTrace();
        ApiResponse<?> response = ApiResponse.builder()
                .error(message)
                .data(null)
                .build();

        return ResponseEntity.status(status)
                .body(response);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<?>> handleNull(NullPointerException ex) {

        System.out.println("message = " + ex.getMessage());
        ex.printStackTrace();

        ApiResponse<?> response = ApiResponse.builder()
                .error(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity.badRequest()
                .body(response);
    }

    /**
     * Bắt lỗi validate dữ liệu từ @RequestBody (dùng @Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException ex) {

        // Lấy thông báo lỗi đầu tiên hoặc gom tất cả các lỗi lại
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return fieldName + ": " + errorMessage;
                })
                .findFirst() // Lấy lỗi đầu tiên, hoặc bạn có thể join lại nếu muốn hiển thị tất cả
                .orElse("Validation error");

        log.error("Validation error: {}", message);

        ApiResponse<?> response = ApiResponse.builder()
                .error(message)
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
