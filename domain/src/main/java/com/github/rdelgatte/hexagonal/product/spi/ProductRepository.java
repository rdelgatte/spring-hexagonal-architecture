package com.github.rdelgatte.hexagonal.product.spi;

import com.github.rdelgatte.hexagonal.product.domain.Product;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;

public interface ProductRepository {

  Product addProduct(Product product);

  void deleteProduct(UUID productId);

  Option<Product> findProductById(UUID productId);

  List<Product> findAllProducts();
}
