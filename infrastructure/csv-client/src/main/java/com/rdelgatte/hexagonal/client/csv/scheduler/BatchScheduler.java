package com.rdelgatte.hexagonal.client.csv.scheduler;

import static com.rdelgatte.hexagonal.client.csv.configuration.EnvironmentVariableEnum.BATCH_PRICES_FILE_PATH;
import static com.rdelgatte.hexagonal.client.csv.configuration.EnvironmentVariableEnum.BATCH_PRICES_IMPORT_CRON;
import static com.rdelgatte.hexagonal.client.csv.configuration.EnvironmentVariableEnum.BATCH_PRODUCTS_FILE_PATH;
import static com.rdelgatte.hexagonal.client.csv.configuration.EnvironmentVariableEnum.BATCH_PRODUCTS_IMPORT_CRON;

import com.rdelgatte.hexagonal.client.csv.configuration.EnvironmentVariableService;
import com.rdelgatte.hexagonal.client.csv.services.PriceCsvService;
import com.rdelgatte.hexagonal.client.csv.services.ProductCsvService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Wither;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 * Schedule batch for :
 * - Products from CSV file
 * - Prices from CSV file
 */
@Component
@EnableScheduling
@AllArgsConstructor
public class BatchScheduler {

  private EnvironmentVariableService environmentVariableService;
  private ThreadPoolTaskScheduler taskScheduler;
  private ProductCsvService productCsvService;
  private PriceCsvService priceCsvService;

  public void initialiseScheduler() {
    scheduleImportProducts();
    scheduleImportPrices();
  }

  private void scheduleImportProducts() {
    String cron = environmentVariableService.load(BATCH_PRODUCTS_IMPORT_CRON);
    String filePath = environmentVariableService.load(BATCH_PRODUCTS_FILE_PATH);
    taskScheduler.schedule(
        new BatchProductsImportTaskRunner(productCsvService).withFilePath(filePath),
        new CronTrigger(cron));
  }

  private void scheduleImportPrices() {
    String cron = environmentVariableService.load(BATCH_PRICES_IMPORT_CRON);
    String filePath = environmentVariableService.load(BATCH_PRICES_FILE_PATH);
    taskScheduler.schedule(
        new BatchPricesImportTaskRunner(priceCsvService).withFilePath(filePath),
        new CronTrigger(cron));
  }

  @Data
  @AllArgsConstructor
  static class BatchProductsImportTaskRunner implements Runnable {

    private ProductCsvService productCsvService;

    @Wither
    private String filePath = "";

    BatchProductsImportTaskRunner(ProductCsvService productCsvService) {
      this.productCsvService = productCsvService;
    }

    @Override
    public void run() {
      productCsvService.saveProductsFromFile(filePath);
    }
  }

  @Data
  @AllArgsConstructor
  static class BatchPricesImportTaskRunner implements Runnable {

    private PriceCsvService priceCsvService;

    @Wither
    private String filePath = "";

    BatchPricesImportTaskRunner(PriceCsvService priceCsvService) {
      this.priceCsvService = priceCsvService;
    }

    @Override
    public void run() {
      priceCsvService.savePricesFromFile(filePath);
    }
  }
}
