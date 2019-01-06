package com.example.ShirkeJR.RESTOrdersManager.domain.converter;

import com.example.ShirkeJR.RESTOrdersManager.domain.dto.ProductDto;
import com.example.ShirkeJR.RESTOrdersManager.domain.dto.ProductLineDto;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Product;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.ProductLine;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ProductLineConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public ProductLineConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductLineDto toView(ProductLine product){
        return modelMapper.map(product, ProductLineDto.class);
    }

    public Set<ProductLineDto> toView(Set<ProductLine> products){
        Set<ProductLineDto> productDtos = new HashSet<>();
        for (ProductLine product: products) {
            productDtos.add(toView(product));
        }
        return productDtos;
    }

    public ProductLine toModel(ProductLineDto productDtos){
        return modelMapper.map(productDtos, ProductLine.class);
    }
}
