package com.rdelgatte.hexagonal.price.api;

import com.rdelgatte.hexagonal.price.domain.Price;
import io.vavr.collection.List;

public interface PriceService {

  Price createPrice(Price price);

  List<Price> getAllPrices();

}
