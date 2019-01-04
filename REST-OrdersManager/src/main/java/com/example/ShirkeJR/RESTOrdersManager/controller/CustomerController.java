package com.example.ShirkeJR.RESTOrdersManager.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.example.ShirkeJR.RESTOrdersManager.Repository.CustomerRepository;
import com.example.ShirkeJR.RESTOrdersManager.exception.CustomerNotFoundException;
import com.example.ShirkeJR.RESTOrdersManager.exception.InvalidCustomerRequestException;
import com.example.ShirkeJR.RESTOrdersManager.model.Customer;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(Lists.newArrayList(customerRepository.findAll()));
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomer(@PathVariable("customerId") Long customerId) {

        if (customerId == null) {
            throw new InvalidCustomerRequestException();
        }

        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);

        customer.add(linkTo(methodOn(CustomerController.class)
                .getCustomer(customer.getCustomerId()))
                .withSelfRel());

        customer.add(linkTo(methodOn(CustomerController.class)
                .updateCustomer(customer, customer.getCustomerId()))
                .withRel("update"));

        customer.add(linkTo(methodOn(CustomerController.class)
                .removeCustomer(customer.getCustomerId()))
                .withRel("delete"));

        customer.add(linkTo(methodOn(OrderController.class)
                .getCustomerOrders(customer.getCustomerId()))
                .withRel("orders"));

        return ResponseEntity.ok(customer);
    }


    @RequestMapping(value = {"/{customerId}"}, method = {RequestMethod.PUT})
    public ResponseEntity<Void> updateCustomer(@RequestBody Customer customer,
                                               @PathVariable("customerId") Long customerId) {

        if (!customerRepository.existsById(customerId)) {
            return ResponseEntity.notFound().build();
        } else {
            customerRepository.save(customer);
            return ResponseEntity.noContent().build();
        }
    }


    @RequestMapping(value = "{customerId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeCustomer(@PathVariable("customerId") Long customerId) {

        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
        }

        return ResponseEntity.noContent().build();
    }


}