package com.example.ShirkeJR.RESTOrdersManager.domain.converter;

import com.example.ShirkeJR.RESTOrdersManager.domain.dto.ProductDto;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Product;
import net.bytebuddy.description.method.ParameterList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public List<ProductDto> toView(List<Product> products){
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product: products) {
            productDtos.add(toView(product));
        }
        return productDtos;
    }

    public Product toModel(ProductDto productDto){
        return modelMapper.map(productDto, Product.class);
    }
}
