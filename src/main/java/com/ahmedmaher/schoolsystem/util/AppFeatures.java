package com.ahmedmaher.schoolsystem.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.Arrays;
import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppFeatures {
    private String sort;
    private  int size;
    private  int page;

    public Sort splitSort() {
        List<Sort.Order> sortOrders = Arrays.stream(this.sort.split(","))
                .map(s -> (s.startsWith("-")) ? Sort.Order.desc(s.substring(1)) : Sort.Order.asc(s) ).toList();
        return Sort.by(sortOrders);
    }
    public Pageable splitPageable() {
        return  PageRequest.of(this.page, this.size , this.splitSort());
    }
}
