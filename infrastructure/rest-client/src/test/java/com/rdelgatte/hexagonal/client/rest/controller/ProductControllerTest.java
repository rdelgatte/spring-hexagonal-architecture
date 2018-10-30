package com.rdelgatte.hexagonal.client.rest.controller;


import static io.vavr.API.List;
import static io.vavr.API.None;
import static io.vavr.API.Option;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rdelgatte.hexagonal.product.api.ProductService;
import com.rdelgatte.hexagonal.product.domain.Product;
import java.util.UUID;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

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
    verify(productServiceMock).createProduct(product);
  }

  /**
   * {@link ProductController#findAll()}
   */
  @Test
  void findAllProducts() {
    when(productServiceMock.getAllProducts()).thenReturn(List(product));

    assertThat(cut.findAll()).containsExactly(product);
  }

  /**
   * {@link ProductController#find(String)}
   */
  @Test
  void findExistingProduct() {
    when(productServiceMock.findProductByCode(product.getCode())).thenReturn(Option(product));

    assertThat(cut.find(product.getCode())).isEqualTo(Option(product));
  }

  @Test
  void findUnknownProduct() {
    when(productServiceMock.findProductByCode(product.getCode())).thenReturn(None());

    assertThat(cut.find(product.getCode())).isEqualTo(None());
  }

  /**
   * {@link ProductController#delete(String)}
   */
  @Test
  void deleteProduct() {
    cut.delete(product.getCode());

    verify(productServiceMock).deleteProduct(product.getCode());
  }
}