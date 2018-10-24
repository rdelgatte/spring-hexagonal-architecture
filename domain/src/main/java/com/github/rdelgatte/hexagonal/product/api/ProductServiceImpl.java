package com.github.rdelgatte.hexagonal.product.api;

import com.github.rdelgatte.hexagonal.product.domain.Product;
import com.github.rdelgatte.hexagonal.product.spi.ProductRepository;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;

public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product createProduct(Product product) {
    Option<Product> productById = productRepository.findProductById(product.getId());
    if (productById.isDefined()) {
      throw new IllegalArgumentException(
          "Product " + product.getId().toString() + " already exists so you can't create it");
    }
    return productRepository.addProduct(product);
  }

  public void deleteProduct(UUID id) {
    if (findProductById(id).isEmpty()) {
      throw new IllegalArgumentException("Product " + id.toString() + " does not exist");
    }
    productRepository.deleteProduct(id);
  }

  public List<Product> getAllProducts() {
    return productRepository.findAllProducts();
  }

  public Option<Product> findProductById(UUID productId) {
    return productRepository.findProductById(productId);
  }
}
