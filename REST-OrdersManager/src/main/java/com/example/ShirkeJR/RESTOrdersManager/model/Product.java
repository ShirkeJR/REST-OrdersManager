package com.example.ShirkeJR.RESTOrdersManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends ResourceSupport {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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
