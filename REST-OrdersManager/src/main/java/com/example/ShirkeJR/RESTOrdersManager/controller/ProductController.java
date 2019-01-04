package com.example.ShirkeJR.RESTOrdersManager.controller;

import com.example.ShirkeJR.RESTOrdersManager.exception.InvalidProductRequestException;
import com.example.ShirkeJR.RESTOrdersManager.exception.ProductNotFoundException;
import com.example.ShirkeJR.RESTOrdersManager.model.Product;
import com.example.ShirkeJR.RESTOrdersManager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProduct(@PathVariable("productId") Long productId) {

        if (productId == null) {
            throw new InvalidProductRequestException();
        }

        Product product = productService.findById(productId).orElseThrow(ProductNotFoundException::new);
        return ResponseEntity.ok(product);
    }


    @RequestMapping(value = { "/{productId}" }, method = { RequestMethod.PUT })
    public ResponseEntity<Void> updateProduct(@RequestBody Product product,
                                              @PathVariable("productId") Long productId) {

        if(!productService.existsById(productId)){
            return ResponseEntity.notFound().build();
        }
        else{
            productService.update(product);
            return ResponseEntity.noContent().build();
        }
    }


    @RequestMapping(value = { "/{productId}" }, method = { RequestMethod.POST })
    public ResponseEntity<Void> createProduct(@RequestBody Product product) {

        productService.create(product);
        return ResponseEntity.noContent().build();
    }


    @RequestMapping(value = "/{productId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) {

        if(productService.existsById(productId)){
            productService.deleteById(productId);
        }

        return ResponseEntity.noContent().build();
    }

}