package com.example.demo.common.filter;

import com.example.demo.common.base.BaseFilter;
import com.example.demo.modules.sys.sys005lang.entity.Sys005lang;
import jakarta.persistence.criteria.Predicate;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LangFilter extends BaseFilter {
    private String code;
    private String vi;
    private String en;
    private String cn;
    private String tw;

    @Override
    public Specification<Sys005lang> toSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Cột 'code' (ASCII) dùng lower để search case-insensitive
            if (StringUtils.hasText(code)) {
                predicates.add(cb.like(cb.lower(root.get("code")), "%" + code.trim().toLowerCase() + "%"));
            }

            // 2. Các cột ngôn ngữ đa quốc gia: BỎ cb.lower() để tránh lỗi mã hóa UTF-8/Unicode
            if (StringUtils.hasText(vi)) {
                predicates.add(cb.like(root.get("vi"), "%" + vi.trim() + "%"));
            }

            if (StringUtils.hasText(en)) {
                predicates.add(cb.like(root.get("en"), "%" + en.trim() + "%"));
            }

            if (StringUtils.hasText(cn)) {
                predicates.add(cb.like(root.get("cn"), "%" + cn.trim() + "%"));
            }

            if (StringUtils.hasText(tw)) {
                predicates.add(cb.like(root.get("tw"), "%" + tw.trim() + "%"));
            }

            if (Sys005lang.class.equals(query.getResultType())) {
                query.orderBy(cb.desc(root.get("ct")));
            }

            // Kết hợp tất cả điều kiện bằng phép AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
