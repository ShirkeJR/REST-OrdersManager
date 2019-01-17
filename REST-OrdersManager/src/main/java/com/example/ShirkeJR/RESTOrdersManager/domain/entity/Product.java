package com.example.ShirkeJR.RESTOrdersManager.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long productId;

    private String name;

    private String description;

    private Double price;

    public Product(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
