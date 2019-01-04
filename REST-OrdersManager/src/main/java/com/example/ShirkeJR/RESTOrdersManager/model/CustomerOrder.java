package com.example.ShirkeJR.RESTOrdersManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrder extends ResourceSupport {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long orderId;

    private LocalDate orderDate;

    private LocalDate dispatchDate;

    private Double totalOrderAmount;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product>  products = new ArrayList<>();

    public void addProduct(Product product){
        products.add(product);
        this.totalOrderAmount = totalOrderAmount + product.getPrice();
    }

    public CustomerOrder(LocalDate orderDate, LocalDate dispatchDate) {
        this.orderDate = orderDate;
        this.dispatchDate = dispatchDate;
        this.totalOrderAmount = 0.0;
    }
}
