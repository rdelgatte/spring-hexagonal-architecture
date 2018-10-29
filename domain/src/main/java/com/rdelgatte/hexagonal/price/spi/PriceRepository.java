package com.rdelgatte.hexagonal.price.spi;

import com.rdelgatte.hexagonal.price.domain.Price;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;

public interface PriceRepository {

  Price addPrice(Price price);

  void deletePrice(UUID priceId);

  Option<Price> findPriceById(UUID priceId);

  List<Price> findAllPrices();
}
