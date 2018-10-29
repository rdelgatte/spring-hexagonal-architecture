package com.rdelgatte.hexagonal.inmemory.repository;

import static io.vavr.API.List;

import com.rdelgatte.hexagonal.price.domain.Price;
import com.rdelgatte.hexagonal.price.spi.PriceRepository;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class InMemoryPriceRepository implements PriceRepository {

  private List<Price> inMemoryPrices = List();

  public Price addPrice(Price price) {
    this.inMemoryPrices = getInMemoryPrices().append(price);
    return price;
  }

  public void deletePrice(UUID priceId) {
    this.inMemoryPrices = getInMemoryPrices().filter(price -> !price.getId().equals(priceId));
  }

  public Option<Price> findPriceById(UUID priceId) {
    return getInMemoryPrices().find(price -> price.getId().equals(priceId));
  }

  public List<Price> findAllPrices() {
    return getInMemoryPrices();
  }
}
