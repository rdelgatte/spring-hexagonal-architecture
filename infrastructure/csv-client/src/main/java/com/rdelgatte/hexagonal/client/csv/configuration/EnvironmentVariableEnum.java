package com.rdelgatte.hexagonal.client.csv.configuration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeration of variable used for the configuration of the application. They are defined as environment variable in
 * each platform
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EnvironmentVariableEnum {

  /**
   * File path of the csv file with products to import.
   */
  BATCH_PRODUCTS_FILE_PATH("batch.csv.products.file"),
  /**
   * Spring-valid CRON schedule for the batch import pipeline for products.
   */
  BATCH_PRODUCTS_IMPORT_CRON("batch.csv.products.cron"),
  /**
   * File path of the csv file with prices to import.
   */
  BATCH_PRICES_FILE_PATH("batch.csv.prices.file"),
  /**
   * Spring-valid CRON schedule for the batch import pipeline for prices.
   */
  BATCH_PRICES_IMPORT_CRON("batch.csv.prices.cron");

  private String label;

}
