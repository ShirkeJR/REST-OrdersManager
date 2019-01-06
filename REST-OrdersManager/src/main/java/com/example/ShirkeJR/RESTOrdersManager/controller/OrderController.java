package com.example.ShirkeJR.RESTOrdersManager.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.example.ShirkeJR.RESTOrdersManager.domain.converter.OrderConverter;
import com.example.ShirkeJR.RESTOrdersManager.domain.converter.ProductConverter;
import com.example.ShirkeJR.RESTOrdersManager.domain.converter.ProductLineConverter;
import com.example.ShirkeJR.RESTOrdersManager.domain.dto.CustomerOrderDto;
import com.example.ShirkeJR.RESTOrdersManager.domain.dto.ProductDto;
import com.example.ShirkeJR.RESTOrdersManager.domain.dto.ProductLineDto;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Product;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.ProductLine;
import com.example.ShirkeJR.RESTOrdersManager.exception.*;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Customer;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.CustomerOrder;
import com.example.ShirkeJR.RESTOrdersManager.service.CustomerService;
import com.example.ShirkeJR.RESTOrdersManager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private ProductLineConverter productLineConverter;


    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<CustomerOrderDto> getOrder(@PathVariable("orderId") Long orderId) {

        CustomerOrderDto orderDto = orderConverter.toView(orderService.findById(orderId).orElseThrow(OrderNotFoundException::new));
        return ResponseEntity.ok(addLinks(orderDto));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CustomerOrderDto>> getAllOrders() {

        return ResponseEntity.ok(orderService.findAll().stream()
                .map(orderConverter::toView).collect(Collectors.toList()));
    }


    @RequestMapping(value = "/customer/{customerId}/orders", method = RequestMethod.GET)
    public ResponseEntity<Set<CustomerOrderDto>> getCustomerOrders(@PathVariable("customerId") Long customerId) {

        Customer customer = customerService.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        Set<CustomerOrderDto> ordersDto = orderConverter.toView(customer.getOrders());
        return ResponseEntity.ok(ordersDto);
    }


    @RequestMapping(value = "/{orderId}/products", method = RequestMethod.GET)
    public ResponseEntity<Set<ProductLineDto>> getProductsFromOrder(@PathVariable("orderId") Long orderId) {

        CustomerOrder customerOrder = orderService.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Set<ProductLineDto> productsDto = productLineConverter.toView(customerOrder.getProducts());
        productsDto.forEach(product -> {

            product.add(linkTo(methodOn(ProductController.class)
                    .getProduct(product.getProductId()))
                    .withSelfRel());

            product.add(linkTo(methodOn(OrderController.class)
                    .deleteProductFromOrder(orderId, product.getProductId()))
                    .withRel("delete-from-order"));
        });

        return ResponseEntity.ok(productsDto);
    }


    @RequestMapping(value = "/{orderId}/product/{productId}", method = RequestMethod.DELETE)
    public ResponseEntity<CustomerOrderDto> deleteProductFromOrder(@PathVariable("orderId") Long orderId,
                                                                @PathVariable("productId") Long productId) {

        CustomerOrder order = orderService.removeProductFromOrderById(productId, orderId);
        return ResponseEntity.ok(orderConverter.toView(order));
    }


    @RequestMapping(value = { "/{orderId}/product/{productId}/quantity/{quantity}" }, method = { RequestMethod.POST })
    public ResponseEntity<CustomerOrderDto> addProductToOrder(@PathVariable("orderId") Long orderId,
                                                           @PathVariable("productId") Long productId,
                                                           @PathVariable("quantity") Integer quantity) {

        if(quantity <= 0) throw new IllegalArgumentException();

        CustomerOrder order = orderService.addProductsToOrder(productId, orderId, quantity);

        CustomerOrderDto orderDto = orderConverter.toView(order);

        orderDto.getProducts()
                .forEach(prod -> {
                    orderDto.add(linkTo(methodOn(ProductController.class)
                            .getProduct(prod.getProductId()))
                            .withRel("product"));
                });

        return ResponseEntity.ok(orderDto);
    }


    @RequestMapping(value = { "/{orderId}" } , method = { RequestMethod.PUT })
    public ResponseEntity<Void> updateOrder(@RequestBody CustomerOrderDto orderDto,
                                            @PathVariable("orderId") Long orderId) {

        if(!orderService.existsById(orderId)){
            return ResponseEntity.notFound().build();
        }
        else{
            orderService.update(orderConverter.toModel(orderDto));
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(value = { "/customer/{customerId}/" }, method = { RequestMethod.POST })
    public ResponseEntity<Void> createOrder(@PathVariable("customerId") Long customerId) {

        CustomerOrderDto customerOrderDto = orderConverter.toView(orderService.create(customerId));
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/customer/{customerId}/orders/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeOrder(@PathVariable("customerId") Long customerId,
                                            @PathVariable("orderId") Long orderId) {

        orderService.removeOrder(customerId, orderId);
        return ResponseEntity.noContent().build();
    }

    private CustomerOrderDto addLinks(CustomerOrderDto order){

        order.add(linkTo(methodOn(OrderController.class)
                .getOrder(order.getOrderId()))
                .withSelfRel());

        order.add(linkTo(methodOn(OrderController.class)
                .updateOrder(order, order.getOrderId()))
                .withRel("update"));

        order.add(linkTo(methodOn(OrderController.class)
                .getProductsFromOrder(order.getOrderId()))
                .withRel("products"));

        return order;
    }

}