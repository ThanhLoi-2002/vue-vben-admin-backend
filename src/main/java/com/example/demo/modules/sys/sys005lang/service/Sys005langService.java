package com.example.demo.modules.sys.sys005lang.service;

import com.example.demo.common.filter.LangFilter;
import com.example.demo.modules.sys.sys005lang.dto.LangDto;
import com.example.demo.modules.sys.sys005lang.dto.request.LangCreationRequest;
import com.example.demo.modules.sys.sys005lang.dto.request.LangUpdateRequest;
import com.example.demo.modules.sys.sys005lang.dto.response.LangResponse;
import com.example.demo.modules.sys.sys005lang.entity.Sys005lang;
import com.example.demo.modules.sys.sys005lang.repo.Sys005langRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class Sys005langService {

    Sys005langRepo langRepository;

    public Map<String, String> getByLang(String lang) {

        return langRepository.findByLang(lang).stream()
                .collect(Collectors.toMap(
                        LangDto::getCode,
                        LangDto::getValue
                ));
    }

    public Page<LangResponse> getAll(LangFilter filter) {
//        List<Lang> list = langRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//        return langMapper.toListResponses(list);

        Pageable pageable = filter.toPageable();

        Page<Sys005lang> page = langRepository.findAllLang(filter.getCode(), pageable);

        return page.map(LangResponse::new);
    }

    public LangResponse getById(Long id) {
        Sys005lang l = langRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "notFound"));
        return new LangResponse(l);
    }

    public LangResponse create(LangCreationRequest lang, Long userId) {

        if (langRepository.existsByCode(lang.getCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Code already exists");
        }

        Sys005lang e = new Sys005lang();
        e.setCode(lang.getCode());
        e.setEn(lang.getEn());
        e.setVi(lang.getVi());
        e.setTw(lang.getTw());
        e.setCn(lang.getCn());
        e.setCu(userId);

        return new LangResponse(langRepository.save(e));
    }

    public LangResponse update(Long id, LangUpdateRequest request, Long userId) {

        Sys005lang e = langRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "notFound"));

        e.setCode(request.getCode());
        e.setEn(request.getEn());
        e.setVi(request.getVi());
        e.setTw(request.getTw());
        e.setCn(request.getCn());
        e.setEu(userId);

        return new LangResponse(langRepository.save(e));
    }

    public void delete(Long id) {
        langRepository.deleteById(id);
    }
}