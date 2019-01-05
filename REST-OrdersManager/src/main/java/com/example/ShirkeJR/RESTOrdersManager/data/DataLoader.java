package com.example.ShirkeJR.RESTOrdersManager.data;

import com.example.ShirkeJR.RESTOrdersManager.Repository.CustomerRepository;
import com.example.ShirkeJR.RESTOrdersManager.Repository.OrderRepository;
import com.example.ShirkeJR.RESTOrdersManager.Repository.ProductRepository;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Address;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Customer;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.CustomerOrder;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Product;
import com.example.ShirkeJR.RESTOrdersManager.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
@Deprecated
@Slf4j
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... arg0) throws Exception {

        log.debug("Loading test data...");

        Product prod1 = new Product("prod 1 name", "prod 1 decription", 45.99);
        Product prod2 = new Product("prod 2 name", "prod 2 decription", 23.99);
        Product prod3 = new Product("prod 3 name", "prod 3 decription", 120.00);
        Product prod4 = new Product("prod 4 name", "prod 4 decription", 499.99);
        Product prod5 = new Product("prod 5 name", "prod 5 decription", 125.00);
        Product prod6 = new Product("prod 6 name", "prod 6 decription", 658.99);
        productRepository.save(prod1);
        productRepository.save(prod2);
        productRepository.save(prod3);
        productRepository.save(prod4);
        productRepository.save(prod5);
        productRepository.save(prod6);

        Customer customer = new Customer("Joe", "Smith", LocalDate.of(1982, 1, 10),
                new Address("High Street", "Newry", "Down", "BT893PY"));
        customerRepository.save(customer);

        Customer customer1 = customerRepository.findByFirstName("Joe");
        customer1.addOrder(new CustomerOrder());
        customerRepository.save(customer1);
        Product p1 = productRepository.findById(1L).get();
        CustomerOrder ord22 = orderRepository.findById(9L).get();
        ord22.addProducts(p1, 2);
        orderRepository.save(ord22);

        log.debug("Test data loaded...");
    }
}
