package com.example.ShirkeJR.RESTOrdersManager.Repository;

import com.example.ShirkeJR.RESTOrdersManager.model.CustomerOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<CustomerOrder, Long> {

}
