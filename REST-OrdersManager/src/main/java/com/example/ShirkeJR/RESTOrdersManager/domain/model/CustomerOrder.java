package com.example.ShirkeJR.RESTOrdersManager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class CustomerOrder{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long orderId;

    private LocalDate orderDate;

    private LocalDate dispatchDate;

    private Double totalOrderAmount;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        products.add(product);
        this.totalOrderAmount = totalOrderAmount + product.getPrice();
    }

    public CustomerOrder() {
        this.orderDate = LocalDate.now();
        this.dispatchDate = LocalDate.now().plusDays(3);
        this.totalOrderAmount = 0.0;
    }
}
