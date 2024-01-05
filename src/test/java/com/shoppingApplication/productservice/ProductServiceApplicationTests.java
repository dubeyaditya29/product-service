package com.shoppingApplication.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.assertions.Assertions;
import com.shoppingApplication.productservice.dto.ProductRequest;
import com.shoppingApplication.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Autowired
	private ProductRepository productRepository;
	// create a container
	@Container
	static final MongoDBContainer mongoDBContainer = new MongoDBContainer();

	@Autowired
	private ObjectMapper objectMapper;
	// Initialize the mongoDB configuration like the URI (USING MONGODB DOCKER CONTAINER)
	@DynamicPropertySource
	static void setUpMongoDBConfig(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {

	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product/createProduct").
				contentType(MediaType.APPLICATION_JSON).
				content(productRequestString)).
				andExpect(status().isCreated());
		Assertions.assertTrue(productRepository.findAll().size()==1);

	}

	private ProductRequest getProductRequest(){
		return ProductRequest.builder().
				name("Sugar").
				description("Sweet based product").
				price(BigDecimal.valueOf(250)).
				build();
	}

}
