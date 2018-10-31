package com.rdelgatte.hexagonal.persistence.inmemory.repository;

import static io.vavr.API.List;
import static io.vavr.API.None;
import static io.vavr.API.Option;
import static org.assertj.core.api.Assertions.assertThat;

import com.rdelgatte.hexagonal.product.domain.Product;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryProductRepositoryTest {

  private InMemoryProductRepository cut;
  private Product product;

  @BeforeEach
  void setUp() {
    cut = new InMemoryProductRepository();
    product = new Product()
        .withCode("1234")
        .withLabel("My awesome product");
  }

  /**
   * {@link InMemoryProductRepository#addProduct(Product)}
   */
  @Test
  void addProductAndAssert() {
    assertThat(cut.addProduct(product)).isEqualTo(product);
    assertThat(cut.getInMemoryProducts()).isEqualTo(List(product));
  }

  /**
   * {@link InMemoryProductRepository#deleteProduct(UUID)}
   */
  @Test
  void deleteExistingProduct() {
    cut.addProduct(product);
    cut.deleteProduct(product.getId());

    assertThat(cut.findProductByCode(product.getCode())).isEmpty();
  }

  /**
   * {@link InMemoryProductRepository#findProductByCode(String)}
   */
  @Test
  void findExistingProduct() {
    cut.addProduct(product);

    assertThat(cut.findProductByCode(product.getCode())).isEqualTo(Option(product));
  }

  @Test
  void findUnknownProduct() {
    assertThat(cut.findProductByCode(product.getCode())).isEqualTo(None());
  }

}