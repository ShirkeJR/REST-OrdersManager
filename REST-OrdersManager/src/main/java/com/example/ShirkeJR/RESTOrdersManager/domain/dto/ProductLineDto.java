package com.example.ShirkeJR.RESTOrdersManager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductLineDto extends ResourceSupport {

    private Long productId;
    private Integer quantity;
}
