package com.example.ShirkeJR.RESTOrdersManager.domain.converter;

import com.example.ShirkeJR.RESTOrdersManager.domain.dto.ProductDto;
import com.example.ShirkeJR.RESTOrdersManager.domain.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ProductConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public ProductConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductDto toView(Product product){
        return modelMapper.map(product, ProductDto.class);
    }

    public Set<ProductDto> toView(Set<Product> products){
        Set<ProductDto> productDtos = new HashSet<>();
        for (Product product: products) {
            productDtos.add(toView(product));
        }
        return productDtos;
    }

    public Product toModel(ProductDto productDto){
        return modelMapper.map(productDto, Product.class);
    }
}
