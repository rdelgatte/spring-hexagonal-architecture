package com.rdelgatte.hexagonal.inmemory.repository;

import static io.vavr.API.List;
import static org.assertj.core.api.Assertions.assertThat;

import com.rdelgatte.hexagonal.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryProductRepositoryTest {

  private InMemoryProductRepository cut;

  @BeforeEach
  void setUp() {
    cut = new InMemoryProductRepository();
  }

  @Test
  void add() {
    Product product = new Product()
        .withCode("1212")
        .withLabel("label1");
    assertThat(cut.addProduct(product)).isEqualTo(product);
    assertThat(cut.getInMemoryProducts()).isEqualTo(List(product));
  }

}