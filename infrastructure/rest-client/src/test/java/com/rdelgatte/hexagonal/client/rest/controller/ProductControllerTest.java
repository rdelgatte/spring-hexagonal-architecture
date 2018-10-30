package com.rdelgatte.hexagonal.client.rest.controller;


import com.rdelgatte.hexagonal.product.api.ProductService;
import com.rdelgatte.hexagonal.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static io.vavr.API.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

  private Product product;
  private ProductController cut;
  @Mock
  private ProductService productServiceMock;

  @BeforeEach
  void setUp() {
    cut = new ProductController(productServiceMock);
    product = new Product(UUID.randomUUID(), "1616", "Easybreath");
  }

  /**
   * {@link ProductController#createProduct(Product)}
   */
  @Test
  void createProduct() {
    when(productServiceMock.createProduct(product)).thenReturn(product);

    cut.createProduct(product);
  }

  @Test
  void findProducts() {
    when(productServiceMock.getAllProducts()).thenReturn(List(product));

    assertThat(cut.findAll()).containsExactly(product);
  }
}