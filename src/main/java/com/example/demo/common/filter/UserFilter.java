package com.example.demo.common.filter;

import com.example.demo.common.base.BaseFilter;
import com.example.demo.modules.sys.sys002user.entities.Sys002user;
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
public class UserFilter extends BaseFilter {
    private String username;
    private Long id;

    @Override
    public Specification<Sys002user> toSpecification() {
        List<Specification<Sys002user>> specs = new ArrayList<>();

        // search chung (tìm trên phone, username)
//        if (StringUtils.hasText(getSearch())) {
//            String searchPattern = "%" + getSearch().trim().toLowerCase() + "%";
//            specs.add((root, query, cb) -> cb.or(
//                    cb.like(cb.lower(root.get("phone")), searchPattern),
//                    cb.like(cb.lower(root.get("username")), searchPattern)
//            ));
//        }

        // filter riêng
        if (StringUtils.hasText(username)) {
            specs.add((root, query, cb) ->
                    cb.like(cb.lower(root.get("username")), "%" + username.trim().toLowerCase() + "%"));
        }

        if (id != null) {
            specs.add((root, query, cb) ->
                    cb.equal(root.get("id"), id));
        }

        // Kết hợp tất cả bằng AND
        return specs.stream()
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction()); // nếu không có filter → true
    }
}
