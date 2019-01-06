package com.example.ShirkeJR.RESTOrdersManager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

import javax.persistence.*;
import java.util.*;

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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductLine> products = new HashSet<>();

    public void addProducts(Product product, Integer quantity){
        Optional<ProductLine> result = products.stream().filter(prod -> prod.getProductId().equals(product.getProductId())).findFirst();
        if(result.isPresent()){
            result.get().increaseQuantityByWithSum(quantity);
        } else {
            products.add(new ProductLine(product.getProductId(), quantity));
        }
        this.totalOrderAmount = totalOrderAmount + (quantity * product.getPrice());
    }

    public void removeProduct(Product product){
        Optional<ProductLine> result = products.stream().filter(prod -> prod.getProductId().equals(product.getProductId())).findFirst();
        result.ifPresent(prod -> {
            this.totalOrderAmount = totalOrderAmount - (prod.getQuantity() * product.getPrice());
            products.remove(prod);
        });
    }

    public CustomerOrder() {
        this.orderDate = LocalDate.now();
        this.dispatchDate = LocalDate.now().plusDays(3);
        this.totalOrderAmount = 0.0;
    }
}
