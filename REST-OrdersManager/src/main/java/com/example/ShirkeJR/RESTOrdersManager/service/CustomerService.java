package com.example.ShirkeJR.RESTOrdersManager.service;

import com.example.ShirkeJR.RESTOrdersManager.Repository.CustomerRepository;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Customer;
import com.example.ShirkeJR.RESTOrdersManager.exception.ProductNotFoundException;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Customer> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public Set<Customer> findAll() {
        return Sets.newHashSet(customerRepository.findAll());
    }

    public Boolean existsById(Long customerId) {
        return customerRepository.existsById(customerId);
    }

    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer update(Customer newCustomer){
        Customer customer = customerRepository.findById(newCustomer.getCustomerId()).orElseThrow(ProductNotFoundException::new);
        customer.setFirstName(newCustomer.getFirstName());
        customer.setLastName(newCustomer.getLastName());
        customer.setDateOfBirth(newCustomer.getDateOfBirth());
        customer.setAddress(newCustomer.getAddress());
        return customerRepository.save(customer);
    }

    public Customer create(Customer newCustomer){
        Customer customer = new Customer();
        customer.setFirstName(newCustomer.getFirstName());
        customer.setLastName(newCustomer.getLastName());
        customer.setDateOfBirth(newCustomer.getDateOfBirth());
        customer.setAddress(newCustomer.getAddress());
        return customerRepository.save(customer);
    }

    public void remove(Long customerId){
        if(customerRepository.existsById(customerId)){
            customerRepository.deleteById(customerId);
        }
    }
}
