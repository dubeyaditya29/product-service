package com.shoppingApplication.productservice.controller;

import com.shoppingApplication.productservice.dto.ProductRequest;
import com.shoppingApplication.productservice.dto.ProductResponse;
import com.shoppingApplication.productservice.model.Product;
import com.shoppingApplication.productservice.repository.ProductRepository;
import com.shoppingApplication.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class Controller {

    // Autowired beans
    private final ProductService productService;

    // APIs
    @PostMapping("/createProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    // Internal methods
    public ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder().
                id(product.getId()).
                name(product.getName()).
                description(product.getDescription()).
                price(product.getPrice()).
                build();
    }
}
