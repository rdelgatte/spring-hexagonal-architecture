package com.rdelgatte.hexagonal.client.controller;

import com.rdelgatte.hexagonal.price.api.PriceService;
import com.rdelgatte.hexagonal.price.domain.Price;
import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PriceController.BASE_PATH)
@AllArgsConstructor
class PriceController {

  static final String BASE_PATH = "prices";
  private final PriceService priceService;

  @PostMapping
  void createPrice(@RequestBody Price price) {
    priceService.createPrice(price);
  }

  @GetMapping
  List<Price> findAll() {
    return priceService.getAllPrices();
  }


}
