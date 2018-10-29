package com.rdelgatte.hexagonal.price.api;

import com.rdelgatte.hexagonal.price.domain.Price;
import com.rdelgatte.hexagonal.price.spi.PriceRepository;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;

public class PriceServiceImpl implements PriceService {

  private final PriceRepository priceRepository;

  public PriceServiceImpl(PriceRepository priceRepository) {
    this.priceRepository = priceRepository;
  }

  public Price createPrice(Price price) {
    Option<Price> priceById = priceRepository.findPriceById(price.getId());
    if (priceById.isDefined()) {
      throw new IllegalArgumentException(
          "Price " + price.getId().toString() + " already exists so you can't create it");
    }
    return priceRepository.addPrice(price);
  }

  public void deletePrice(UUID id) {
    if (findPriceById(id).isEmpty()) {
      throw new IllegalArgumentException("Price " + id.toString() + " does not exist");
    }
    priceRepository.deletePrice(id);
  }

  public List<Price> getAllPrices() {
    return priceRepository.findAllPrices();
  }

  public Option<Price> findPriceById(UUID priceId) {
    return priceRepository.findPriceById(priceId);
  }
}
