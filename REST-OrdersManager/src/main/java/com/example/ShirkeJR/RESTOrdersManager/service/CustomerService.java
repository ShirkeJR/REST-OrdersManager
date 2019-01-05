package com.example.ShirkeJR.RESTOrdersManager.service;

import com.example.ShirkeJR.RESTOrdersManager.Repository.CustomerRepository;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Customer;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.CustomerOrder;
import com.example.ShirkeJR.RESTOrdersManager.exception.ProductNotFoundException;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Customer> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public List<Customer> findAll() {
        return Lists.newArrayList(customerRepository.findAll());
    }

    public Boolean existsById(Long customerId) {
        return customerRepository.existsById(customerId);

    }

    public Customer update(Customer updatedCustomer){
        Customer oldCustomer = customerRepository.findById(updatedCustomer.getCustomerId()).orElseThrow(ProductNotFoundException::new);
        oldCustomer.setFirstName(updatedCustomer.getFirstName());
        oldCustomer.setLastName(updatedCustomer.getLastName());
        oldCustomer.setDateOfBirth(updatedCustomer.getDateOfBirth());
        oldCustomer.setAddress(updatedCustomer.getAddress());
        return customerRepository.save(oldCustomer);
    }

    public void deleteById(Long customerId){
        customerRepository.deleteById(customerId);
    }

    public Customer create(Customer newCustomer){
        Customer customer = new Customer();
        customer.setFirstName(newCustomer.getFirstName());
        customer.setLastName(newCustomer.getLastName());
        customer.setDateOfBirth(newCustomer.getDateOfBirth());
        customer.setAddress(newCustomer.getAddress());
        return customerRepository.save(customer);
    }
}
