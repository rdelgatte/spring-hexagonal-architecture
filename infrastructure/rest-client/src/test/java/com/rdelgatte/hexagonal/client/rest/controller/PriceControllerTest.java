package com.rdelgatte.hexagonal.client.rest.controller;

import static io.vavr.API.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rdelgatte.hexagonal.price.api.PriceService;
import com.rdelgatte.hexagonal.price.domain.Price;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

  private PriceController cut;
  @Mock
  private PriceService priceServiceMock;
  private Price price;

  @BeforeEach
  void setUp() {
    cut = new PriceController(priceServiceMock);
    price = new Price(UUID.randomUUID(), 1234);
  }

  /**
   * {@link PriceController#createPrice(Price)}
   */
  @Test
  void createPrice() {
    cut.createPrice(price);

    verify(priceServiceMock).createPrice(price);
  }

  /**
   * {@link PriceController#findAll()}
   */
  @Test
  void findAllPrices() {
    when(priceServiceMock.getAllPrices()).thenReturn(List(price));

    assertThat(cut.findAll()).containsExactly(price);
  }
}