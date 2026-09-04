package com.example.demo.modules.sys.sys002user;

import com.example.demo.common.annotation.currentUser.CurrentUser;
import com.example.demo.modules.sys.sys002user.dto.request.UserPayload;
import com.example.demo.modules.sys.sys002user.dto.response.Sys002userResponse;
import com.example.demo.modules.sys.sys002user.entities.Sys002user;
import com.example.demo.modules.sys.sys002user.service.Sys002userService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class Sys002userController {
    private final Sys002userService sys002userService;

    @GetMapping("/info")
    public Sys002userResponse getMe(
            @CurrentUser UserPayload user
    ) {
        Sys002user sys002user = sys002userService.findById(user.getId());
        return new Sys002userResponse(sys002user);
    }

//    @PostMapping("/upload-avatar")
//    @ResponseMessage("success")
//    public Sys002userResponse uploadAvatar(
//            @RequestParam MultipartFile file,
//            @CurrentUser UserPayload user
//    ) throws Exception {
//        Sys002userResponse u = sys002userService.uploadAvatar(file, user.getId());
//
//        return new Sys002userResponse(u);
//    }
}

