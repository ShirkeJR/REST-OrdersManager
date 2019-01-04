package com.example.ShirkeJR.RESTOrdersManager.service;

import com.example.ShirkeJR.RESTOrdersManager.Repository.ProductRepository;
import com.example.ShirkeJR.RESTOrdersManager.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> findById(Long productId){
        return productRepository.findById(productId);
    }

    public Boolean existsById(Long productId){
        return productRepository.existsById(productId);
    }

    public Product update(Product product){
        return productRepository.save(product);
    }

    public void deleteById(Long productId){
        productRepository.deleteById(productId);
    }

    public Product create(Product newProduct){
        Product product = new Product();
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        product.setPrice(newProduct.getPrice());
        return productRepository.save(product);
    }
}
