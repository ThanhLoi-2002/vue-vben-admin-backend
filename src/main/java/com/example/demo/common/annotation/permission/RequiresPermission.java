package com.example.demo.common.annotation.permission;

import java.lang.annotation.*;

@Target({ElementType.METHOD}) // Chỉ áp dụng trên các hàm (API)
@Retention(RetentionPolicy.RUNTIME) // Tồn tại khi chạy ứng dụng để Reflection đọc được
@Documented
public @interface RequiresPermission {
    String[] value(); // Nơi truyền mã quyền, ví dụ: "user:write"
}
