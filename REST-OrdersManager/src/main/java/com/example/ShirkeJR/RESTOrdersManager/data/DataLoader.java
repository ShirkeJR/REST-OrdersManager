package com.example.ShirkeJR.RESTOrdersManager.data;

import com.example.ShirkeJR.RESTOrdersManager.Repository.CustomerRepository;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Address;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Customer;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.CustomerOrder;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Product;
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


    @Override
    public void run(String... arg0) throws Exception {

        log.debug("Loading test data...");

        Product prod1 = new Product("prod 1 name", "prod 1 decription", 45.99);
        Product prod2 = new Product("prod 2 name", "prod 2 decription", 23.99);
        Product prod3 = new Product("prod 3 name", "prod 3 decription", 120.00);
        Product prod4 = new Product("prod 4 name", "prod 4 decription", 499.99);
        Product prod5 = new Product("prod 5 name", "prod 5 decription", 125.00);
        Product prod6 = new Product("prod 6 name", "prod 6 decription", 658.99);

        CustomerOrder order1 = new CustomerOrder();
        CustomerOrder order2 = new CustomerOrder();
        CustomerOrder order3 = new CustomerOrder();

        order1.addProduct(prod1);
        order1.addProduct(prod2);
        order2.addProduct(prod3);
        order2.addProduct(prod4);
        order3.addProduct(prod5);
        order3.addProduct(prod6);

        Customer customer = new Customer("Joe", "Smith", LocalDate.of(1982, 1, 10),
                new Address("High Street", "Newry", "Down", "BT893PY"));

        customer.addOrder(order1);
        customer.addOrder(order2);
        customer.addOrder(order3);
        customerRepository.save(customer);

        log.debug("Test data loaded...");
    }
}
