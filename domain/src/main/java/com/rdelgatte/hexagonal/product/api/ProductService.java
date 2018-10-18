package com.rdelgatte.hexagonal.product.api;

import com.rdelgatte.hexagonal.product.domain.Product;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;

public interface ProductService {

  Product createProduct(Product product);

  void deleteProduct(UUID productId);

  List<Product> getAllProducts();

  Option<Product> findProductById(UUID productId);


}
