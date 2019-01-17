package com.example.ShirkeJR.RESTOrdersManager.data;

import com.example.ShirkeJR.RESTOrdersManager.Repository.CustomerRepository;
import com.example.ShirkeJR.RESTOrdersManager.Repository.OrderRepository;
import com.example.ShirkeJR.RESTOrdersManager.Repository.ProductRepository;
import com.example.ShirkeJR.RESTOrdersManager.domain.entity.Address;
import com.example.ShirkeJR.RESTOrdersManager.domain.entity.Customer;
import com.example.ShirkeJR.RESTOrdersManager.domain.entity.CustomerOrder;
import com.example.ShirkeJR.RESTOrdersManager.domain.entity.Product;
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

        CustomerOrder customerOrder1 = new CustomerOrder();
        CustomerOrder customerOrder2 = new CustomerOrder();


        log.debug("Loading test data...");
        Product prod1 = new Product(1L,"prod 1 name", "prod 1 decription", 45.99);
        Product prod2 = new Product(2L,"prod 2 name", "prod 2 decription", 23.99);
        Product prod3 = new Product(3L,"prod 3 name", "prod 3 decription", 120.00);
        Product prod4 = new Product(4L,"prod 4 name", "prod 4 decription", 499.99);
        Product prod5 = new Product(5L,"prod 5 name", "prod 5 decription", 125.00);
        Product prod6 = new Product(6L,"prod 6 name", "prod 6 decription", 658.99);
        productRepository.save(prod1);
        productRepository.save(prod2);
        productRepository.save(prod3);
        productRepository.save(prod4);
        productRepository.save(prod5);
        productRepository.save(prod6);

        customerOrder1.addProducts(prod1, 10);
        customerOrder1.addProducts(prod2, 50);
        customerOrder1.addProducts(prod3, 100);
        customerOrder2.addProducts(prod4, 50);
        customerOrder2.addProducts(prod5, 100);
        customerOrder2.addProducts(prod6, 150);

        Customer customer1 = new Customer("Tomek", "Kowalski", LocalDate.of(1982, 1, 10),
                new Address("Koryznowa", "Lublin", "Polska", "20-333"));
        Customer customer2 = new Customer("Janek", "Baryła", LocalDate.of(1995, 6, 22),
                new Address("Lwowska", "Poniatowa", "Anglia", "40-233"));

        customer1.addOrder(customerOrder1);
        customer2.addOrder(customerOrder2);
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        log.debug("Test data loaded...");
    }
}
