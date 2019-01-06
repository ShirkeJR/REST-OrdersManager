package com.example.ShirkeJR.RESTOrdersManager.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.example.ShirkeJR.RESTOrdersManager.domain.converter.CustomerConverter;
import com.example.ShirkeJR.RESTOrdersManager.domain.dto.CustomerDto;
import com.example.ShirkeJR.RESTOrdersManager.exception.CustomerNotFoundException;
import com.example.ShirkeJR.RESTOrdersManager.exception.InvalidCustomerRequestException;
import com.example.ShirkeJR.RESTOrdersManager.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerConverter customerConverter;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CustomerDto>> getCustomers() {

        List<CustomerDto> customerDtos = customerService.findAll().stream()
                .map(customerConverter::toView).collect(Collectors.toList());

        customerDtos.forEach(customer -> {

            customer.add(linkTo(methodOn(CustomerController.class)
                    .getCustomer(customer.getCustomerId()))
                    .withSelfRel());
        });

        return ResponseEntity.ok(customerDtos);
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") Long customerId) {

        if (customerId == null) throw new InvalidCustomerRequestException();

        CustomerDto customerDto = customerConverter.toView(customerService.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new));

        return ResponseEntity.ok(addLinks(customerDto));
    }

    @RequestMapping(value = {"/{customerId}"}, method = {RequestMethod.PUT})
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto,
                                               @PathVariable("customerId") Long customerId) {

        if (!customerService.existsById(customerId)) {
            return ResponseEntity.notFound().build();
        } else {
            CustomerDto updatedCustomerDto = customerConverter.toView(customerService.update(customerConverter.toModel(customerDto)));
            return ResponseEntity.ok(addLinks(updatedCustomerDto));
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {

        CustomerDto createdCustomerDto = customerConverter.toView(customerService.create(customerConverter.toModel(customerDto)));
        return ResponseEntity.ok(addLinks(createdCustomerDto));
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeCustomer(@PathVariable Long customerId) {

        customerService.remove(customerId);
        return ResponseEntity.noContent().build();
    }

    private CustomerDto addLinks(CustomerDto customerDto){

        customerDto.add(linkTo(methodOn(CustomerController.class)
                .getCustomer(customerDto.getCustomerId()))
                .withSelfRel());

        customerDto.add(linkTo(methodOn(CustomerController.class)
                .updateCustomer(customerDto, customerDto.getCustomerId()))
                .withRel("update"));

        customerDto.add(linkTo(methodOn(OrderController.class)
                .getCustomerOrders(customerDto.getCustomerId()))
                .withRel("orders"));

        return customerDto;
    }
}