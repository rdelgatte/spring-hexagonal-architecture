package com.github.rdelgatte.hexagonal.client.configuration;

import com.github.rdelgatte.hexagonal.product.api.ProductService;
import com.github.rdelgatte.hexagonal.product.api.ProductServiceImpl;
import org.springframework.context.annotation.Bean;
import repository.InMemoryProductRepository;

@org.springframework.context.annotation.Configuration
public class Configuration {

  @Bean
  public InMemoryProductRepository inMemoryProductRepository() {
    return new InMemoryProductRepository();
  }

  @Bean
  public ProductService productService() {
    return new ProductServiceImpl(inMemoryProductRepository());
  }

}
