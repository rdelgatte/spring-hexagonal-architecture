package com.github.rdelgatte.hexagonal.client.controller;

import com.github.rdelgatte.hexagonal.product.api.ProductService;
import com.github.rdelgatte.hexagonal.product.domain.Product;
import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ProductController.BASE_PATH)
@AllArgsConstructor
class ProductController {

  static final String BASE_PATH = "products";
  private final ProductService productService;

  @PostMapping
  void createProduct() {
    Product product = new Product()
        .withCode("1515")
        .withLabel("label");

    productService.createProduct(product);
  }

  @GetMapping
  List<Product> findAll() {
    return productService.getAllProducts();
  }


}
