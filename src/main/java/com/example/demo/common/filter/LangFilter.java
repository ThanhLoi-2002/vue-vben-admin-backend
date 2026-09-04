package com.example.demo.common.filter;

import com.example.demo.common.base.BaseFilter;
import com.example.demo.modules.sys.sys005lang.entity.Sys005lang;
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

    @Override
    public Specification<Sys005lang> toSpecification() {
        List<Specification<Sys005lang>> specs = new ArrayList<>();

        // filter riêng
        if (StringUtils.hasText(code)) {
            specs.add((root, query, cb) ->
                    cb.like(cb.lower(root.get("code")), "%" + code.trim().toLowerCase() + "%"));
        }

        Specification<Sys005lang> result = specs.stream()
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction());

        // ✅ thêm sort tại đây
        return (root, query, cb) -> {
            query.orderBy(cb.desc(root.get("ct")));
            return result.toPredicate(root, query, cb);
        };
    }
}
