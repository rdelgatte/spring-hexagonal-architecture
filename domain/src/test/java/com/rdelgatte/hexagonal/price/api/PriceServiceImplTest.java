package com.rdelgatte.hexagonal.price.api;

import static io.vavr.API.List;
import static io.vavr.API.None;
import static io.vavr.API.Option;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.rdelgatte.hexagonal.price.domain.Price;
import com.rdelgatte.hexagonal.price.spi.PriceRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

  private PriceServiceImpl cut;
  @Mock
  private PriceRepository priceRepositoryMock;
  private Price price;
  private UUID priceId;

  @BeforeEach
  void setUp() {
    cut = new PriceServiceImpl(priceRepositoryMock);
    priceId = UUID.randomUUID();
    price = new Price(priceId, 1234);
  }

  /**
   * {@link PriceServiceImpl#createPrice(Price)}
   */
  @Test
  void createExistingValidPrice_throwsException() {
    when(priceRepositoryMock.findPriceById(priceId)).thenReturn(Option(price));

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> cut.createPrice(price));

    assertThat(illegalArgumentException.getMessage())
        .isEqualTo("Price " + price.getId().toString() + " already exists so you can't create it");
  }

  @Test
  void createUnknownValidPrice_createPrice() {
    when(priceRepositoryMock.findPriceById(priceId)).thenReturn(None());
    when(priceRepositoryMock.addPrice(price)).thenReturn(price);

    assertThat(cut.createPrice(price)).isEqualTo(price);
  }

  /**
   * {@link PriceServiceImpl#getAllPrices()}
   */
  @Test
  void getAllPrices_returnPrices() {
    when(priceRepositoryMock.findAllPrices()).thenReturn(List(price));

    assertThat(cut.getAllPrices()).containsExactly(price);
  }
}