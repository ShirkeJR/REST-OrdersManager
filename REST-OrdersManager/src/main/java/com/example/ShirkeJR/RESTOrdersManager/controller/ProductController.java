package com.example.ShirkeJR.RESTOrdersManager.controller;

import com.example.ShirkeJR.RESTOrdersManager.domain.converter.ProductConverter;
import com.example.ShirkeJR.RESTOrdersManager.domain.dto.ProductDto;
import com.example.ShirkeJR.RESTOrdersManager.exception.InvalidProductRequestException;
import com.example.ShirkeJR.RESTOrdersManager.exception.ProductNotFoundException;
import com.example.ShirkeJR.RESTOrdersManager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductConverter productConverter;

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") Long productId) {

        if (productId == null) {
            throw new InvalidProductRequestException();
        }

        ProductDto productDto = productConverter.toView(productService.findById(productId)
                .orElseThrow(ProductNotFoundException::new));

        return ResponseEntity.ok(productDto);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProductDto>> getProducts() {

        return ResponseEntity.ok(productService.findAll().stream()
                .map(productConverter::toView).collect(Collectors.toList()));
    }


    @RequestMapping(value = { "/{productId}" }, method = { RequestMethod.PUT })
    public ResponseEntity<Void> updateProduct(@RequestBody ProductDto productDto,
                                              @PathVariable("productId") Long productId) {

        if(!productService.existsById(productId)){
            return ResponseEntity.notFound().build();
        }
        else{
            productService.update(productConverter.toModel(productDto));
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT )
    public ResponseEntity<Void> createProduct(@RequestBody ProductDto productDto) {
        productService.create(productConverter.toModel(productDto));
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