package com.rdelgatte.hexagonal.persistence.inmemory.repository;

import static io.vavr.API.None;
import static io.vavr.API.Option;
import static org.assertj.core.api.Assertions.assertThat;

import com.rdelgatte.hexagonal.price.domain.Price;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryPriceRepositoryTest {

  private InMemoryPriceRepository cut;
  private Price price;

  @BeforeEach
  void setUp() {
    cut = new InMemoryPriceRepository();
    price = new Price(UUID.randomUUID(), 1234);
  }

  /**
   * {@link InMemoryPriceRepository#addPrice(Price)}
   */
  @Test
  void addPriceAndAssert() {
    assertThat(cut.addPrice(price)).isEqualTo(price);

    assertThat(cut.findAllPrices()).containsExactly(price);
  }

  /**
   * {@link InMemoryPriceRepository#deletePrice(UUID)}
   */
  @Test
  void deleteExistingPrice() {
    cut.addPrice(price);
    cut.deletePrice(price.getId());

    assertThat(cut.findPriceById(price.getId())).isEmpty();
  }

  /**
   * {@link InMemoryPriceRepository#findPriceById(UUID)}
   */
  @Test
  void findExistingPrice() {
    cut.addPrice(price);

    assertThat(cut.findPriceById(price.getId())).isEqualTo(Option(price));
  }

  @Test
  void findUnknownPrice() {
    assertThat(cut.findPriceById(price.getId())).isEqualTo(None());
  }
}