package com.example.ShirkeJR.RESTOrdersManager.service;

import com.example.ShirkeJR.RESTOrdersManager.Repository.ProductRepository;
import com.example.ShirkeJR.RESTOrdersManager.domain.model.Product;
import com.example.ShirkeJR.RESTOrdersManager.exception.ProductNotFoundException;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> findById(Long productId){
        return productRepository.findById(productId);
    }

    public List<Product> findAll(){
        return Lists.newArrayList(productRepository.findAll());
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
