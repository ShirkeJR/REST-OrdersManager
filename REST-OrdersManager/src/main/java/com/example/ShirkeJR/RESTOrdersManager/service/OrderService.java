package com.example.ShirkeJR.RESTOrdersManager.service;

import com.example.ShirkeJR.RESTOrdersManager.Repository.OrderRepository;
import com.example.ShirkeJR.RESTOrdersManager.domain.entity.Customer;
import com.example.ShirkeJR.RESTOrdersManager.domain.entity.CustomerOrder;
import com.example.ShirkeJR.RESTOrdersManager.domain.entity.Product;
import com.example.ShirkeJR.RESTOrdersManager.exception.CustomerNotFoundException;
import com.example.ShirkeJR.RESTOrdersManager.exception.OrderNotFoundException;
import com.example.ShirkeJR.RESTOrdersManager.exception.ProductNotFoundException;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;

    public Optional<CustomerOrder> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public Set<CustomerOrder> findAll() {
        return Sets.newHashSet(orderRepository.findAll());
    }

    public Boolean existsById(Long orderId) {
        return orderRepository.existsById(orderId);
    }

    public CustomerOrder update(CustomerOrder order) {
        return orderRepository.save(order);
    }

    public void removeOrder(Long customerId, Long orderId) {
        Customer customer = customerService.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        CustomerOrder order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        customer.removeOrder(order);
        customerService.save(customer);
    }

    public CustomerOrder create(Long customerId) {
        Customer customer = customerService.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        CustomerOrder newOrder = new CustomerOrder();
        customer.addOrder(newOrder);
        customerService.save(customer);
        return orderRepository.findByRandomNumber(newOrder.getRandomNumber());
    }

    public CustomerOrder removeProductFromOrderById(Long productId, Long orderId) {
        CustomerOrder order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Product product = productService.findById(productId).orElseThrow(ProductNotFoundException::new);
        order.removeProduct(product);
        return orderRepository.save(order);
    }

    public CustomerOrder addProductsToOrder(Long productId, Long orderId, Integer quantity) {
        CustomerOrder order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Product product = productService.findById(productId).orElseThrow(ProductNotFoundException::new);
        order.addProducts(product, quantity);
        return orderRepository.save(order);
    }
}

