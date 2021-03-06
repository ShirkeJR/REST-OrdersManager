package com.example.ShirkeJR.RESTOrdersManager.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Data
@AllArgsConstructor
public class CustomerOrder{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long orderId;

    private LocalDate orderDate;

    private LocalDate dispatchDate;

    private Double totalOrderAmount;

    private Integer randomNumber;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
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
        this.randomNumber = ThreadLocalRandom.current().nextInt(10, 9999999);
        this.orderDate = LocalDate.now();
        this.dispatchDate = LocalDate.now().plusDays(3);
        this.totalOrderAmount = 0.0;
    }
}
