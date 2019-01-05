package com.example.ShirkeJR.RESTOrdersManager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends ResourceSupport {

    private Long productId;
    private String name;
    private String description;
    private Double price;
}
