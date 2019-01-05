package com.example.ShirkeJR.RESTOrdersManager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto extends ResourceSupport{

    private Long customerId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String addressStreet;
    private String addressTown;
    private String addressCountry;
    private String addressPostcode;
}
