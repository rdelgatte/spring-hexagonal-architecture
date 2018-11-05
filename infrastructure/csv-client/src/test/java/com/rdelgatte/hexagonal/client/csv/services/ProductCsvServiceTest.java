package com.rdelgatte.hexagonal.client.csv.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.rdelgatte.hexagonal.product.api.ProductService;
import com.rdelgatte.hexagonal.product.domain.Product;
import io.vavr.collection.List;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
class ProductCsvServiceTest {

  private static final String ANY_FILE_PATH = "any/file/path";
  private static final String ANY_PRODUCT_CODE_1 = "123";
  private static final String ANY_PRODUCT_CODE_2 = "456";
  private ProductCsvService cut;
  @Mock
  private CsvDataLoader csvDataLoaderMock;
  @Mock
  private ProductService productServiceMock;
  @Captor
  private ArgumentCaptor<Product> productArgumentCaptor;

  @BeforeEach
  void setUp() {
    cut = new ProductCsvService(csvDataLoaderMock, productServiceMock);
  }

  @Test
  @DisplayName("Given a file with no product, when it saves products from the file, it does nothing.")
  void noProductInFile_saveProductsFromFile_doNothing() {
    when(csvDataLoaderMock.loadObjectList(Product.class, ANY_FILE_PATH)).thenReturn(List.empty());

    cut.saveProductsFromFile(ANY_FILE_PATH);

    verifyZeroInteractions(productServiceMock);
  }

  @Test
  @DisplayName("Given a file with some products, when it saves products from the file, it saves each product.")
  void productsInFile_saveProductsFromFile_saveEachProduct() {
    List<Product> products = List.of(
        new Product().withCode(ANY_PRODUCT_CODE_1),
        new Product().withCode(ANY_PRODUCT_CODE_2)
    );
    when(csvDataLoaderMock.loadObjectList(Product.class, ANY_FILE_PATH)).thenReturn(products);

    cut.saveProductsFromFile(ANY_FILE_PATH);

    verify(productServiceMock, times(2)).createProduct(productArgumentCaptor.capture());
    List<Product> savedProducts = List.ofAll(productArgumentCaptor.getAllValues());
    assertThat(savedProducts).isEqualTo(products);
  }
}
