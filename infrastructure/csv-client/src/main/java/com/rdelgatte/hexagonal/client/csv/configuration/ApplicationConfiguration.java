package com.rdelgatte.hexagonal.client.csv.configuration;

import com.rdelgatte.hexagonal.persistence.inmemory.repository.InMemoryPriceRepository;
import com.rdelgatte.hexagonal.persistence.mysql.repository.MysqlProductRepositoryImpl;
import com.rdelgatte.hexagonal.price.api.PriceService;
import com.rdelgatte.hexagonal.price.api.PriceServiceImpl;
import com.rdelgatte.hexagonal.product.api.ProductService;
import com.rdelgatte.hexagonal.product.api.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor(onConstructor = @__(@Autowired))
// We need to provide the explicit packages path to scan for infrastructure layer autowiring
@ComponentScan(basePackages = {
    "com.rdelgatte.hexagonal.persistence.mysql",
})
public class ApplicationConfiguration {

  private MysqlProductRepositoryImpl mysqlProductRepository;

  private InMemoryPriceRepository inMemoryPriceRepository() {
    return new InMemoryPriceRepository();
  }

  @Bean
  public ProductService productService() {
    return new ProductServiceImpl(mysqlProductRepository);
  }

  @Bean
  public PriceService priceService() {
    return new PriceServiceImpl(inMemoryPriceRepository());
  }

}
