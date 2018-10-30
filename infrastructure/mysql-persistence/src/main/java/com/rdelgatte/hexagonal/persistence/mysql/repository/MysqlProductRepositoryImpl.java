package com.rdelgatte.hexagonal.persistence.mysql.repository;

import com.rdelgatte.hexagonal.persistence.mysql.model.MysqlProduct;
import com.rdelgatte.hexagonal.product.spi.ProductRepository;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MysqlProductRepositoryImpl implements ProductRepository {

  private com.rdelgatte.hexagonal.persistence.mysql.repository.ProductRepository productRepository;

  @Override
  public com.rdelgatte.hexagonal.product.domain.Product addProduct(
      com.rdelgatte.hexagonal.product.domain.Product product) {
    productRepository.save(new MysqlProduct().fromDomain(product));
    return product;
  }

  @Override
  public void deleteProduct(UUID productId) {
    productRepository.deleteById(productId);
  }

  @Override
  public Option<com.rdelgatte.hexagonal.product.domain.Product> findProductByCode(String code) {
    return productRepository.findOneByCode(code).map(MysqlProduct::toDomain);
  }

  @Override
  public List<com.rdelgatte.hexagonal.product.domain.Product> findAllProducts() {
    return List.ofAll(productRepository.findAll())
        .map(MysqlProduct::toDomain);
  }
}
