package com.example.ShirkeJR.RESTOrdersManager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long customerId;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CustomerOrder> orders = new HashSet<>();

    public void addOrder(CustomerOrder order){
        orders.add(order);
    }

    public void removeOrder(CustomerOrder order){
        CustomerOrder o = null;
        for (CustomerOrder orderr: orders) {
            if(orderr.getOrderId().equals(order.getOrderId())){
                   o = orderr;
                   break;
            }
        }
        if(o != null){
            orders.remove(o);
        }

    }

    public Customer(String firstName, String lastName, LocalDate dateOfBirth, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }
}
