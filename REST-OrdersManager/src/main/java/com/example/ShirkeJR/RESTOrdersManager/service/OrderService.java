package com.example.ShirkeJR.RESTOrdersManager.service;

import com.example.ShirkeJR.RESTOrdersManager.Repository.OrderRepository;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Customer;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.CustomerOrder;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Product;
import com.example.ShirkeJR.RESTOrdersManager.exception.CustomerNotFoundException;
import com.example.ShirkeJR.RESTOrdersManager.exception.OrderNotFoundException;
import com.example.ShirkeJR.RESTOrdersManager.exception.ProductNotFoundException;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<CustomerOrder> findAll() {
        return Lists.newArrayList(orderRepository.findAll());
    }

    public Boolean existsById(Long orderId) {
        return orderRepository.existsById(orderId);
    }

    public CustomerOrder update(CustomerOrder order) {
        return orderRepository.save(order);
    }

    public void deleteById(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public void create(Long customerId) {
        Customer customer = customerService.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        customer.addOrder(new CustomerOrder());
        customerService.save(customer);
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

