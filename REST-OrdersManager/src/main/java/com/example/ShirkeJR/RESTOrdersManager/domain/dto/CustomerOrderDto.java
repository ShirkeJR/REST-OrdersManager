package com.example.ShirkeJR.RESTOrdersManager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderDto extends ResourceSupport {

    private Long orderId;
    private LocalDate orderDate;
    private LocalDate dispatchDate;
    private Double totalOrderAmount;
    private List<ProductDto> products;
}
