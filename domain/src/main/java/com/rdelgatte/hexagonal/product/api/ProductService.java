package com.rdelgatte.hexagonal.product.api;

import com.rdelgatte.hexagonal.product.domain.Product;
import io.vavr.collection.List;
import io.vavr.control.Option;

public interface ProductService {

  Product createProduct(Product product);

  void deleteProduct(String code);

  List<Product> getAllProducts();

  Option<Product> findProductByCode(String code);


}
