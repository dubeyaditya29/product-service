package com.shoppingApplication.productservice.service;

import com.shoppingApplication.productservice.dto.ProductRequest;
import com.shoppingApplication.productservice.model.Product;
import com.shoppingApplication.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    //Post Call to update the products.
    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder().name(productRequest.getName()).description(productRequest.getDescription()).price(productRequest.getPrice()).build();
        productRepository.save(product);
        log.info("Product {} is saved",product.getId());
    }

    // Get Call for all Products.
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
}
