package com.example.ShirkeJR.RESTOrdersManager.Repository;

import com.example.ShirkeJR.RESTOrdersManager.domain.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
