package com.example.ShirkeJR.RESTOrdersManager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String street;

    private String town;

    private String county;

    private String postcode;

    public Address(String street, String town, String county, String postcode) {
        this.street = street;
        this.town = town;
        this.county = county;
        this.postcode = postcode;
    }
}
