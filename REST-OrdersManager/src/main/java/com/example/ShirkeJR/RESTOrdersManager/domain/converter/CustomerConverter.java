package com.example.ShirkeJR.RESTOrdersManager.domain.converter;

import com.example.ShirkeJR.RESTOrdersManager.domain.dto.CustomerDto;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomerConverter {
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerDto toView(Customer customer){
        return modelMapper.map(customer, CustomerDto.class);
    }

    public Set<CustomerDto> toView(Set<Customer> customers){
        Set<CustomerDto> customerDtos = new HashSet<>();
        for (Customer customer: customers) {
            customerDtos.add(toView(customer));
        }
        return customerDtos;
    }

    public Customer toModel(CustomerDto customerDto){
        return modelMapper.map(customerDto, Customer.class);
    }
}
