package com.example.demo.common.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@Data
@NoArgsConstructor
public abstract class BaseFilter {

    private int page = 0;           // bắt đầu từ 0
    private int limit = 20;         // mặc định 20 items/trang
    private String sort = "id";     // field mặc định sort
    private String order = "asc";   // asc / desc
    private String search;          // tìm kiếm chung (full-text-like trên nhiều field)

    // Các field filter cụ thể sẽ được thêm ở class con

    public Pageable toPageable() {
        Sort.Direction direction = "desc".equalsIgnoreCase(order)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Sort sortObj = Sort.by(direction, sort);

        return PageRequest.of(page, limit, sortObj);
    }

    public Pageable toScrollable(String sort, Sort.Direction direction) {
        Sort sortObj = Sort.by(direction, sort);

        return PageRequest.of(0, limit, sortObj);
    }

    // Nếu bạn muốn sort nhiều field (sort=email,createdAt&order=asc,desc)
    // thì có thể override method này ở class con
    public Pageable toPageableWithMultiSort() {
        return toPageable(); // mặc định chỉ 1 field
    }

    /**
     * Build Specification từ filter này
     * Class con sẽ override để thêm điều kiện riêng
     */
    public abstract <T> Specification<T> toSpecification();
}
