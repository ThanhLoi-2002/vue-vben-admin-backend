package com.example.demo.modules.sys.sys005lang.repo;

import com.example.demo.modules.sys.sys005lang.dto.LangDto;
import com.example.demo.modules.sys.sys005lang.entity.Sys005lang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sys005langRepo extends JpaRepository<Sys005lang, Long> {

    boolean existsByCode(String code);

    @Query("""
              SELECT
                  l.code as code,
                  CASE\s
                      WHEN :lang = 'vi' THEN l.vi
                      WHEN :lang = 'en' THEN l.en
                      WHEN :lang = 'cn' THEN l.cn
                      WHEN :lang = 'tw' THEN l.tw
                  END as value
              FROM Sys005lang l
            \s""")
    List<LangDto> findByLang(@Param("lang") String lang);

    @Query("""
                SELECT l
                FROM Sys005lang l
                WHERE (
                    :code IS NULL
                    OR LOWER(l.vi) LIKE LOWER(CONCAT('%', :code, '%'))
                    OR LOWER(l.en) LIKE LOWER(CONCAT('%', :code, '%'))
                    OR LOWER(l.cn) LIKE LOWER(CONCAT('%', :code, '%'))
                    OR LOWER(l.tw) LIKE LOWER(CONCAT('%', :code, '%'))
                )
                AND l.stt = 1
                ORDER BY l.ct DESC
            """)
    Page<Sys005lang> findAllLang(@Param("code") String code, Pageable pageable);
}