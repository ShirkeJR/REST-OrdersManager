package com.example.ShirkeJR.RESTOrdersManager.Repository;

import com.example.ShirkeJR.RESTOrdersManager.domain.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    public Customer findByFirstName(String name);
}
