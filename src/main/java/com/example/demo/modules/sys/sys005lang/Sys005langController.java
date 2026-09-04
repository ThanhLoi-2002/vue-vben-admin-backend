package com.example.demo.modules.sys.sys005lang;

import com.example.demo.common.annotation.Public.Public;
import com.example.demo.common.annotation.app.ResponseMessage;
import com.example.demo.common.annotation.currentUser.CurrentUser;
import com.example.demo.common.annotation.permission.RequiresPermission;
import com.example.demo.common.filter.LangFilter;
import com.example.demo.common.util.PermissionConstant;
import com.example.demo.modules.sys.sys002user.dto.request.UserPayload;
import com.example.demo.modules.sys.sys005lang.dto.request.LangCreationRequest;
import com.example.demo.modules.sys.sys005lang.dto.request.LangUpdateRequest;
import com.example.demo.modules.sys.sys005lang.dto.response.LangResponse;
import com.example.demo.modules.sys.sys005lang.service.Sys005langService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sys005lang")
@RequiredArgsConstructor
public class Sys005langController {

    private final Sys005langService langService;

    @GetMapping
//    @RequiresPermission(PermissionConstant.LANG.READ)
    public Page<LangResponse> getAll(@ModelAttribute LangFilter filter) {
        return langService.getAll(filter);
    }

    @GetMapping("/{id}")
//    @RequiresPermission(PermissionConstant.LANG.READ)
    public LangResponse getById(@PathVariable Long id) {
        return langService.getById(id);
    }

    @GetMapping("/getByLang/{lang}")
    @Public
    public Map<String, String> getByLang(@PathVariable String lang) {
        return langService.getByLang(lang);
    }

    @PostMapping
    @ResponseMessage("success")
//    @RequiresPermission(PermissionConstant.LANG.CREATE)
    public LangResponse create(@RequestBody @Valid LangCreationRequest lang, @CurrentUser UserPayload user) {
        return langService.create(lang, user.getId());
    }

    @PutMapping("/{id}")
    @ResponseMessage("success")
//    @RequiresPermission(PermissionConstant.LANG.UPDATE)
    public LangResponse update(
            @PathVariable Long id,
            @RequestBody @Valid LangUpdateRequest lang, @CurrentUser UserPayload user
    ) {
        return langService.update(id, lang, user.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseMessage("success")
//    @RequiresPermission(PermissionConstant.LANG.DELETE)
    public void delete(@PathVariable Long id) {
        langService.delete(id);
    }
}