package com.rdelgatte.hexagonal.client.controller;

import com.rdelgatte.hexagonal.product.api.ProductService;
import com.rdelgatte.hexagonal.product.domain.Product;
import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ProductController.BASE_PATH)
@AllArgsConstructor
public
class ProductController {

  static final String BASE_PATH = "products";
  private static final String RESOURCE_PATH = "{code}";
  private final ProductService productService;

  @PostMapping
  void createProduct(@RequestBody Product product) {
    productService.createProduct(product);
  }

  @GetMapping
  List<Product> findAll() {
    return productService.getAllProducts();
  }

  @GetMapping(RESOURCE_PATH)
  Option<Product> find(@PathVariable String code) {
    return productService.findProductByCode(code);
  }

  @DeleteMapping(RESOURCE_PATH)
  void delete(@PathVariable String code) {
    productService.deleteProduct(code);
  }


}
