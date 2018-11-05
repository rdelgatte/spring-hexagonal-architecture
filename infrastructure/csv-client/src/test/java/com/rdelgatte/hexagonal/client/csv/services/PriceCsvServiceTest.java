package com.rdelgatte.hexagonal.client.csv.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.rdelgatte.hexagonal.price.api.PriceService;
import com.rdelgatte.hexagonal.price.domain.Price;
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
class PriceCsvServiceTest {

  private static final String ANY_FILE_PATH = "any/file/path";
  private static final int ANY_PRICE_VALUE_1 = 43;
  private static final int ANY_PRICE_VALUE_2 = 54;
  private PriceCsvService cut;
  @Mock
  private CsvDataLoader csvDataLoaderMock;
  @Mock
  private PriceService priceServiceMock;
  @Captor
  private ArgumentCaptor<Price> priceArgumentCaptor;

  @BeforeEach
  void setUp() {
    cut = new PriceCsvService(csvDataLoaderMock, priceServiceMock);
  }

  @Test
  @DisplayName("Given a file with no price, when it saves prices from the file, it does nothing.")
  void noPriceInFile_savePricesFromFile_doNothing() {
    when(csvDataLoaderMock.loadObjectList(Price.class, ANY_FILE_PATH)).thenReturn(List.empty());

    cut.savePricesFromFile(ANY_FILE_PATH);

    verifyZeroInteractions(priceServiceMock);
  }

  @Test
  @DisplayName("Given a file with some prices, when it saves prices from the file, it saves each price.")
  void pricesInFile_savePricesFromFile_savesEachPrice() {
    List<Price> prices = List.of(
        new Price().withValue(ANY_PRICE_VALUE_1),
        new Price().withValue(ANY_PRICE_VALUE_2)
    );
    when(csvDataLoaderMock.loadObjectList(Price.class, ANY_FILE_PATH)).thenReturn(prices);

    cut.savePricesFromFile(ANY_FILE_PATH);

    verify(priceServiceMock, times(2)).createPrice(priceArgumentCaptor.capture());
    List<Price> savedPrices = List.ofAll(priceArgumentCaptor.getAllValues());
    assertThat(savedPrices).isEqualTo(prices);
  }
}
