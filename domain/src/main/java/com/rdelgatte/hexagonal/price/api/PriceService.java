package com.rdelgatte.hexagonal.price.api;

import com.rdelgatte.hexagonal.price.domain.Price;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;

public interface PriceService {

  Price createPrice(Price price);

  void deletePrice(UUID priceId);

  List<Price> getAllPrices();

  Option<Price> findPriceById(UUID priceId);

}
