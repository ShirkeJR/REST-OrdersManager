package com.example.ShirkeJR.RESTOrdersManager.domain.converter;

import com.example.ShirkeJR.RESTOrdersManager.domain.dto.CustomerOrderDto;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.CustomerOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public OrderConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerOrderDto toView(CustomerOrder customerOrderc){
        return modelMapper.map(customerOrderc, CustomerOrderDto.class);
    }

    public List<CustomerOrderDto> toView(List<CustomerOrder> customerOrders){
        List<CustomerOrderDto> customerOrderDtos = new ArrayList<>();
        for (CustomerOrder customerOrder: customerOrders) {
            customerOrderDtos.add(toView(customerOrder));
        }
        return customerOrderDtos;
    }

    public CustomerOrder toModel(CustomerOrderDto customerOrderDto){
        return modelMapper.map(customerOrderDto, CustomerOrder.class);
    }
}
