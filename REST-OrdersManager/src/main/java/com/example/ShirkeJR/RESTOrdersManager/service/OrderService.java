package com.example.ShirkeJR.RESTOrdersManager.service;

import com.example.ShirkeJR.RESTOrdersManager.Repository.OrderRepository;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Customer;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.CustomerOrder;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    public Optional<CustomerOrder> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public List<CustomerOrder> findAll() {
        return Lists.newArrayList(orderRepository.findAll());
    }

    public Boolean existsById(Long orderId) {
        return orderRepository.existsById(orderId);
    }

    public CustomerOrder update(CustomerOrder order){
        return orderRepository.save(order);
    }

    public void deleteById(Long orderId){
        orderRepository.deleteById(orderId);
    }

    public CustomerOrder create(){
        CustomerOrder customerOrder = new CustomerOrder();
        return orderRepository.save(customerOrder);
    }
}
