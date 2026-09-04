package com.example.demo.common.annotation.permission;

import com.example.demo.modules.sys.sys002user.dto.request.UserPayload;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
public class PermissionAspect {
    // Kích hoạt TRƯỚC KHI hàm API chạy. Nó tự động ép biến "requiresPermission" vào để đọc value
    @Before("@annotation(requiresPermission)")
    public void checkPermission(RequiresPermission requiresPermission) {
        // Lấy đối tượng UserPayload từ Security Context
        Object principal = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();

        if (!(principal instanceof UserPayload currentUser)) {
            throw new AccessDeniedException("User không hợp lệ!");
        }

        // Tiến hành check quyền từ currentUser tương tự như trên...
        String[] requiredPermissions = requiresPermission.value();

        // Kiểm tra xem user có ÍT NHẤT MỘT trong các quyền yêu cầu hay không
        boolean hasPermission = Arrays.stream(requiredPermissions)
                .anyMatch(currentUser.getPermissions()::contains);
//        System.out.println("các quyền: " + Arrays.toString(requiredPermissions) + " " + hasPermission);
        if (!hasPermission) {
            throw new AccessDeniedException("accessDenied");
        }
    }
}
