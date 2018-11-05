package com.rdelgatte.hexagonal.client.csv.services;

import com.rdelgatte.hexagonal.price.api.PriceService;
import com.rdelgatte.hexagonal.price.domain.Price;
import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Read price data from a CSV file and call the domain service to save them
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PriceCsvService {

  private CsvDataLoader csvDataLoader;
  private PriceService priceService;

  public void savePricesFromFile(String filePath) {
    getPrices(filePath).forEach(priceService::createPrice);
  }

  private List<Price> getPrices(String filePath) {
    return csvDataLoader.loadObjectList(Price.class, filePath);
  }
}
