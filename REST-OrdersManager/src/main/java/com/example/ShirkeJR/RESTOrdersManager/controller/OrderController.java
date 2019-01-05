package com.example.ShirkeJR.RESTOrdersManager.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.example.ShirkeJR.RESTOrdersManager.Repository.CustomerRepository;
import com.example.ShirkeJR.RESTOrdersManager.Repository.OrderRepository;
import com.example.ShirkeJR.RESTOrdersManager.Repository.ProductRepository;
import com.example.ShirkeJR.RESTOrdersManager.domain.converter.OrderConverter;
import com.example.ShirkeJR.RESTOrdersManager.domain.converter.ProductConverter;
import com.example.ShirkeJR.RESTOrdersManager.domain.dto.CustomerOrderDto;
import com.example.ShirkeJR.RESTOrdersManager.domain.dto.ProductDto;
import com.example.ShirkeJR.RESTOrdersManager.exception.*;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Customer;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.CustomerOrder;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Product;
import com.example.ShirkeJR.RESTOrdersManager.service.CustomerService;
import com.example.ShirkeJR.RESTOrdersManager.service.OrderService;
import com.example.ShirkeJR.RESTOrdersManager.service.ProductService;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private ProductConverter productConverter;


    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<CustomerOrderDto> getOrder(@PathVariable("orderId") Long orderId) {

        if (null==orderId) {
            throw new InvalidOrderRequestException();
        }

        CustomerOrderDto orderDto = orderConverter.toView(orderService.findById(orderId).orElseThrow(OrderNotFoundException::new));

        orderDto.add(linkTo(methodOn(OrderController.class)
                .getOrder(orderDto.getOrderId()))
                .withSelfRel());

        orderDto.add(linkTo(methodOn(OrderController.class)
                .removeOrder(orderDto.getOrderId()))
                .withRel("delete"));

        orderDto.add(linkTo(methodOn(OrderController.class)
                .getProductsFromOrder(orderDto.getOrderId()))
                .withRel("products"));

        return ResponseEntity.ok(orderDto);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CustomerOrderDto>> getOrders() {

        return ResponseEntity.ok(orderService.findAll().stream()
                .map(orderConverter::toView).collect(Collectors.toList()));
    }


    @RequestMapping(value = "/{customerId}/orders", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerOrderDto>> getCustomerOrders(@PathVariable("customerId") Long customerId) {

        Customer customer = customerService.findById(customerId).orElseThrow(InvalidCustomerRequestException::new);
        List<CustomerOrderDto> ordersDto = orderConverter.toView(customer.getOrders());

        ordersDto.forEach(order -> {

            order.add(linkTo(methodOn(OrderController.class)
                    .getOrder(order.getOrderId()))
                    .withSelfRel());

            order.add(linkTo(methodOn(OrderController.class)
                    .removeOrder(order.getOrderId()))
                    .withRel("delete"));

            order.add(linkTo(methodOn(OrderController.class)
                    .getProductsFromOrder(order.getOrderId()))
                    .withRel("products"));
        });

        return ResponseEntity.ok(ordersDto);
    }


    @RequestMapping(value = "/{orderId}/products", method = RequestMethod.GET)
    public ResponseEntity<List<ProductDto>> getProductsFromOrder(@PathVariable("orderId") Long orderId) {

        CustomerOrder customerOrder = orderService.findById(orderId).orElseThrow(OrderNotFoundException::new);
        List<ProductDto> productsDto = productConverter.toView(customerOrder.getProducts());
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

        CustomerOrder order = orderService.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Iterator<Product> productIterator = order.getProducts().iterator();

        productIterator.forEachRemaining(prod -> {
            if(prod.getProductId().equals(productId)){
                productIterator.remove();
            }
        });

        orderService.update(order);

        return ResponseEntity.ok(orderConverter.toView(order));
    }


    @RequestMapping(value = { "/{orderId}/product/{productId}/quantity/{quantity}" }, method = { RequestMethod.POST })
    public ResponseEntity<CustomerOrder> addProductToOrder(@PathVariable("orderId") Long orderId,
                                                           @PathVariable("productId") Long productId,
                                                           @PathVariable("quantity") Integer quantity) {

        CustomerOrder order = orderService.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Product product = productService.findById(productId).orElseThrow(ProductNotFoundException::new);

        IntStream.of(quantity).forEach(i-> order.addProduct(product));
        orderService.update(order);

        CustomerOrderDto customerOrderDto = orderConverter.toView(orderService.findById(orderId).orElseThrow(OrderNotFoundException::new));
        customerOrderDto.getProducts()
                .forEach(prod -> {
                    customerOrderDto.add(linkTo(methodOn(ProductController.class)
                            .getProduct(prod.getProductId()))
                            .withRel("product"));
                });

        return ResponseEntity
                .created(URI.create(customerOrderDto.getLink("self").getHref()))
                .body(order);
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

    @RequestMapping(value = { "/{customerId}/create" }, method = { RequestMethod.PUT })
    public ResponseEntity<Void> createOrder(@PathVariable("customerId") Long customerId) {

        Customer customer = customerService.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        customer.addOrder(orderService.create());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeOrder(@PathVariable("orderId") Long orderId) {

        CustomerOrder order = orderService.findById(orderId).orElseThrow(OrderNotFoundException::new);
        order.getProducts().clear();
        orderService.deleteById(orderId);

        return ResponseEntity.noContent().build();
    }

}