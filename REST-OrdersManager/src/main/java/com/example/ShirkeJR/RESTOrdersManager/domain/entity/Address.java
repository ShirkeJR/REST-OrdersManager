package com.example.ShirkeJR.RESTOrdersManager.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String street;

    private String town;

    private String country;

    private String postcode;

    public Address(String street, String town, String country, String postcode) {
        this.street = street;
        this.town = town;
        this.country = country;
        this.postcode = postcode;
    }
}
