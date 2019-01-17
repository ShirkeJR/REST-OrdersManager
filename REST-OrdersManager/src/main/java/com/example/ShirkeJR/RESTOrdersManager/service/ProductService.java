package com.example.ShirkeJR.RESTOrdersManager.service;

import com.example.ShirkeJR.RESTOrdersManager.Repository.ProductRepository;
import com.example.ShirkeJR.RESTOrdersManager.domain.entity.Product;
import com.example.ShirkeJR.RESTOrdersManager.exception.ProductNotFoundException;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> findById(Long productId){
        return productRepository.findById(productId);
    }

    public Set<Product> findAll(){
        return Sets.newHashSet(productRepository.findAll());
    }

    public Boolean existsById(Long productId){
        return productRepository.existsById(productId);
    }

    public Product update(Product updatedProduct){
        Product oldProduct = productRepository.findById(updatedProduct.getProductId()).orElseThrow(ProductNotFoundException::new);
        oldProduct.setName(updatedProduct.getName());
        oldProduct.setDescription(updatedProduct.getDescription());
        oldProduct.setPrice(updatedProduct.getPrice());
        return productRepository.save(oldProduct);
    }

    public Product create(Product newProduct){
        Product product = new Product();
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        product.setPrice(newProduct.getPrice());
        return productRepository.save(product);
    }


}
