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

        return ResponseEntity.ok(customerService.findAll().stream()
                .map(customerConverter::toView).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") Long customerId) {

        if (customerId == null) {
            throw new InvalidCustomerRequestException();
        }

        CustomerDto customerDto = customerConverter.toView(customerService.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new));

        customerDto.add(linkTo(methodOn(CustomerController.class)
                .getCustomer(customerDto.getCustomerId()))
                .withSelfRel());

        customerDto.add(linkTo(methodOn(CustomerController.class)
                .updateCustomer(customerDto, customerDto.getCustomerId()))
                .withRel("update"));

        customerDto.add(linkTo(methodOn(CustomerController.class)
                .removeCustomer(customerDto.getCustomerId()))
                .withRel("delete"));

        customerDto.add(linkTo(methodOn(OrderController.class)
                .getCustomerOrders(customerDto.getCustomerId()))
                .withRel("orders"));

        return ResponseEntity.ok(customerDto);
    }

    @RequestMapping(value = {"/{customerId}"}, method = {RequestMethod.PUT})
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerDto customerDto,
                                               @PathVariable("customerId") Long customerId) {

        if (!customerService.existsById(customerId)) {
            return ResponseEntity.notFound().build();
        } else {
            customerService.update(customerConverter.toModel(customerDto));
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT )
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerDto customerDto) {

        customerService.create(customerConverter.toModel(customerDto));
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeCustomer(@PathVariable("customerId") Long customerId) {

        if (customerService.existsById(customerId)) {
            customerService.deleteById(customerId);
        }

        return ResponseEntity.noContent().build();
    }
}