package com.example.demo.common.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@Data
@NoArgsConstructor
public abstract class BaseFilter<T> {

    private int page = 0;           // 0-based index
    private int pageSize = 20;      // Mặc định 20 items/trang
    private String sort;     // Field mặc định sort
    private String order;   // asc / desc
    private String search;          // Tìm kiếm chung

    /**
     * Chuyển đổi sang Pageable an toàn cho Spring Data JPA
     */
    public Pageable toPageable() {
        // 1. Validate tham số trang tránh IllegalArgumentException
        int validPage = Math.max(0, this.page);
        int validPageSize = this.pageSize < 1 ? 20 : Math.min(this.pageSize, 500); // Khống chế tối đa 500 items/trang

        // 2. Chỉ tạo Sort khi 'sort' có giá trị (không null, không rỗng)
        if (StringUtils.hasText(this.sort)) {
            Sort.Direction direction = "desc".equalsIgnoreCase(this.order)
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            return PageRequest.of(validPage, validPageSize, Sort.by(direction, this.sort.trim()));
        }

        // 3. Nếu 'sort' không có giá trị, trả về Pageable không kèm điều kiện sắp xếp (Sort.unsorted())
        return PageRequest.of(validPage, validPageSize, Sort.unsorted());
    }

    /**
     * Phục vụ Infinite Scroll / Fetch trang đầu tiên
     */
    public Pageable toScrollable(String defaultSort, Sort.Direction defaultDirection) {
        int validPageSize = this.pageSize < 1 ? 20 : Math.min(this.pageSize, 500);
        String sortField = StringUtils.hasText(defaultSort) ? defaultSort.trim() : "id";

        return PageRequest.of(0, validPageSize, Sort.by(defaultDirection, sortField));
    }

    /**
     * Build Specification cho Entity T cụ thể
     */
    public abstract Specification<T> toSpecification();
}