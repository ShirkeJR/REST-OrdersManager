package com.example.ShirkeJR.RESTOrdersManager.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class ProductLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private Long productId;
    private Integer quantity;

    public ProductLine(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public void increaseQuantityByWithSum(Integer quantity) {
        this.quantity += quantity;
    }
}