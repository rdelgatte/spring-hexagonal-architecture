package com.rdelgatte.hexagonal.client.csv.services;

import com.rdelgatte.hexagonal.product.api.ProductService;
import com.rdelgatte.hexagonal.product.domain.Product;
import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Read product data from a CSV file and call the domain service to save them
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductCsvService {

  private CsvDataLoader csvDataLoader;
  private ProductService productService;

  public void saveProductsFromFile(String filePath) {
    getProducts(filePath).forEach(productService::createProduct);
  }

  private List<Product> getProducts(String filePath) {
    return csvDataLoader.loadObjectList(Product.class, filePath);
  }
}
