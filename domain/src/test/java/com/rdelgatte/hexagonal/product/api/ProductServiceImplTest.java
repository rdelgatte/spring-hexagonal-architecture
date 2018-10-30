package com.rdelgatte.hexagonal.product.api;

import static io.vavr.API.List;
import static io.vavr.API.None;
import static io.vavr.API.Option;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rdelgatte.hexagonal.product.domain.Product;
import com.rdelgatte.hexagonal.product.spi.ProductRepository;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  private ProductServiceImpl cut;
  @Mock
  private ProductRepository productRepositoryMock;
  private Product product;
  private String productCode = "123";

  @BeforeEach
  void setUp() {
    cut = new ProductServiceImpl(productRepositoryMock);
    product = new Product()
        .withCode(productCode)
        .withLabel("My awesome product");
  }

  /**
   * {@link ProductServiceImpl#createProduct(Product)}
   */
  @Test
  void productAlreadyExists_throwsException() {
    when(productRepositoryMock.findProductByCode(productCode)).thenReturn(Option(product));

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> cut.createProduct(product));
    assertThat(illegalArgumentException.getMessage())
        .isEqualTo("Product " + product.getCode() + " already exists so you can't create it");
  }

  @Test
  void productWithoutCode_throwsException() {
    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> cut.createProduct(new Product()));

    assertThat(illegalArgumentException.getMessage()).isEqualTo("There is no code for the product");
  }

  @Test
  void unknownValidProduct_createProduct() {
    when(productRepositoryMock.findProductByCode(productCode)).thenReturn(None());
    when(productRepositoryMock.addProduct(product)).thenReturn(product);

    assertThat(cut.createProduct(product)).isEqualTo(product);
  }

  /**
   * {@link ProductServiceImpl#findProductByCode(String)}
   */
  @Test
  void unknownProduct_returnsNone() {
    when(productRepositoryMock.findProductByCode(productCode)).thenReturn(None());

    assertThat(cut.findProductByCode(productCode)).isEqualTo(None());
  }

  @Test
  void existingProduct_returnsProduct() {
    when(productRepositoryMock.findProductByCode(productCode)).thenReturn(Option(product));

    assertThat(cut.findProductByCode(productCode)).isEqualTo(Option(product));
  }

  /**
   * {@link ProductServiceImpl#deleteProduct(String)}
   */
  @Test
  void deleteUnknownProduct_throwsException() {
    when(productRepositoryMock.findProductByCode(productCode)).thenReturn(None());

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> cut.deleteProduct(productCode));
    assertThat(illegalArgumentException.getMessage()).isEqualTo("Product 123 does not exist");
  }

  @Test
  void deleteNoCode_throwsException() {
    assertThrows(NullPointerException.class, () -> cut.deleteProduct(null));
  }

  @Test
  void deleteExistingProduct_deleteProduct() {
    when(productRepositoryMock.findProductByCode(productCode)).thenReturn(Option(product));

    cut.deleteProduct(productCode);

    verify(productRepositoryMock).deleteProduct(product.getId());
  }

  /**
   * {@link ProductServiceImpl#getAllProducts()}
   */
  @Test
  void getAllProducts_returnProducts() {
    when(productRepositoryMock.findAllProducts()).thenReturn(List(product));

    assertThat(cut.getAllProducts()).containsExactly(product);
  }
}