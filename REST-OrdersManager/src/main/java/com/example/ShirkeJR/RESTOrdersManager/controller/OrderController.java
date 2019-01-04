package com.example.ShirkeJR.RESTOrdersManager.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.example.ShirkeJR.RESTOrdersManager.Repository.CustomerRepository;
import com.example.ShirkeJR.RESTOrdersManager.Repository.OrderRepository;
import com.example.ShirkeJR.RESTOrdersManager.Repository.ProductRepository;
import com.example.ShirkeJR.RESTOrdersManager.exception.InvalidCustomerRequestException;
import com.example.ShirkeJR.RESTOrdersManager.exception.InvalidOrderRequestException;
import com.example.ShirkeJR.RESTOrdersManager.exception.OrderNotFoundException;
import com.example.ShirkeJR.RESTOrdersManager.exception.ProductNotFoundException;
import com.example.ShirkeJR.RESTOrdersManager.model.Customer;
import com.example.ShirkeJR.RESTOrdersManager.model.CustomerOrder;
import com.example.ShirkeJR.RESTOrdersManager.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public CustomerOrder getOrder(@PathVariable("orderId") Long orderId) {

        if (null==orderId) {
            throw new InvalidOrderRequestException();
        }

        CustomerOrder order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);


        order.add(linkTo(methodOn(OrderController.class)
                .getOrder(order.getOrderId()))
                .withSelfRel());

        order.add(linkTo(methodOn(OrderController.class)
                .removeOrder(order.getOrderId()))
                .withRel("delete"));

        order.add(linkTo(methodOn(OrderController.class)
                .getProductsFromOrder(order.getOrderId()))
                .withRel("products"));

        return order;
    }


    @RequestMapping(value = "/{customerId}/orders", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerOrder>> getCustomerOrders(@PathVariable("customerId") Long customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(InvalidCustomerRequestException::new);
        List<CustomerOrder> orders = customer.getOrders();

        orders.forEach(order -> {

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

        return ResponseEntity.ok(orders);
    }


    @RequestMapping(value = "/{orderId}/products", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getProductsFromOrder(@PathVariable("orderId") Long orderId) {

        CustomerOrder customerOrder = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        List<Product> products = customerOrder.getProducts();
        products.forEach(product -> {

            product.add(linkTo(methodOn(ProductController.class)
                    .getProduct(product.getProductId()))
                    .withSelfRel());

            product.add(linkTo(methodOn(OrderController.class)
                    .deleteProductFromOrder(orderId, product.getProductId()))
                    .withRel("delete-from-order"));
        });

        return ResponseEntity.ok(products);
    }


    @RequestMapping(value = "/{orderId}/product/{productId}", method = RequestMethod.DELETE)
    public ResponseEntity<CustomerOrder> deleteProductFromOrder(@PathVariable("orderId") Long orderId,
                                                                @PathVariable("productId") Long productId) {

        CustomerOrder order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Iterator<Product> productIterator = order.getProducts().iterator();

        productIterator.forEachRemaining(prod -> {
            if(prod.getId().equals(productId)){
                productIterator.remove();
            }
        });

        orderRepository.save(order);

        return ResponseEntity.ok(order);
    }


    @RequestMapping(value = { "/{orderId}/product/{productId}/quantity/{quantity}" }, method = { RequestMethod.POST })
    public ResponseEntity<CustomerOrder> addProductToOrder(@PathVariable("orderId") Long orderId,
                                                           @PathVariable("productId") Long productId,
                                                           @PathVariable("quantity") Integer quantity) {

        CustomerOrder order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        IntStream.of(quantity).forEach(i-> order.addProduct(product));
        orderRepository.save(order);

        orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new).getProducts()
                .forEach(prod -> {
                    order.add(linkTo(methodOn(ProductController.class)
                            .getProduct(prod.getProductId()))
                            .withRel("product"));
                });

        return ResponseEntity
                .created(URI.create(order.getLink("self").getHref()))
                .body(order);
    }


    @RequestMapping(value = { "/{orderId}" }, method = { RequestMethod.PUT })
    public ResponseEntity<Void> updateOrder(@RequestBody CustomerOrder order,
                                            @PathVariable("orderId") Long orderId) {

        if(!orderRepository.existsById(orderId)){
            return ResponseEntity.notFound().build();
        }
        else{
            orderRepository.save(order);
            return ResponseEntity.noContent().build();
        }
    }


    @RequestMapping(value = "/api/order/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeOrder(@PathVariable("orderId") Long orderId) {

        if(orderRepository.existsById(orderId)){
            orderRepository.deleteById(orderId);
        }

        return ResponseEntity.noContent().build();
    }

}