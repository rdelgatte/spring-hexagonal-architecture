package com.rdelgatte.hexagonal.client.csv.scheduler;

import static com.rdelgatte.hexagonal.client.csv.configuration.EnvironmentVariableEnum.BATCH_PRICES_FILE_PATH;
import static com.rdelgatte.hexagonal.client.csv.configuration.EnvironmentVariableEnum.BATCH_PRICES_IMPORT_CRON;
import static com.rdelgatte.hexagonal.client.csv.configuration.EnvironmentVariableEnum.BATCH_PRODUCTS_FILE_PATH;
import static com.rdelgatte.hexagonal.client.csv.configuration.EnvironmentVariableEnum.BATCH_PRODUCTS_IMPORT_CRON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rdelgatte.hexagonal.client.csv.configuration.EnvironmentVariableService;
import com.rdelgatte.hexagonal.client.csv.scheduler.BatchScheduler.BatchPricesImportTaskRunner;
import com.rdelgatte.hexagonal.client.csv.scheduler.BatchScheduler.BatchProductsImportTaskRunner;
import com.rdelgatte.hexagonal.client.csv.services.PriceCsvService;
import com.rdelgatte.hexagonal.client.csv.services.ProductCsvService;
import java.util.List;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

@ExtendWith(MockitoExtension.class)
class BatchSchedulerTest {

  private static final String ANY_PRODUCT_TASK_CRON = "0 0 0 * * ?";
  private static final String ANY_PRICE_TASK_CRON = "0 0 0 * * ?";
  private static final String ANY_PRODUCTS_FILE_PATH = "/any/products/path";
  private static final String ANY_PRICES_FILE_PATH = "/any/prices/path";
  private BatchScheduler cut;
  @Mock
  private EnvironmentVariableService environmentVariableServiceMock;
  @Mock
  private ThreadPoolTaskScheduler taskSchedulerMock;
  @Mock
  private ProductCsvService productCsvServiceMock;
  @Mock
  private PriceCsvService priceCsvServiceMock;
  @Captor
  private ArgumentCaptor<Runnable> runnableCaptor;
  @Captor
  private ArgumentCaptor<CronTrigger> cronTriggerCaptor;

  @BeforeEach
  void setUp() {
    cut = new BatchScheduler(environmentVariableServiceMock, taskSchedulerMock, productCsvServiceMock,
        priceCsvServiceMock);
  }

  @Test
  @DisplayName("Given some correct configuration, when it initialise the batch scheduler, it instantiates task runners for products and prices.")
  void correctConfiguration_initialise_instantiateTaskRunner() {
    when(environmentVariableServiceMock.load(BATCH_PRODUCTS_IMPORT_CRON)).thenReturn(ANY_PRODUCT_TASK_CRON);
    when(environmentVariableServiceMock.load(BATCH_PRICES_IMPORT_CRON)).thenReturn(ANY_PRICE_TASK_CRON);
    when(environmentVariableServiceMock.load(BATCH_PRODUCTS_FILE_PATH)).thenReturn(ANY_PRODUCTS_FILE_PATH);
    when(environmentVariableServiceMock.load(BATCH_PRICES_FILE_PATH)).thenReturn(ANY_PRICES_FILE_PATH);

    cut.initialiseScheduler();

    verify(taskSchedulerMock, times(2)).schedule(runnableCaptor.capture(), cronTriggerCaptor.capture());
    BatchProductsImportTaskRunner expectedBatchProductsImportTaskRunner = new BatchProductsImportTaskRunner(
        productCsvServiceMock)
        .withFilePath(ANY_PRODUCTS_FILE_PATH);
    BatchPricesImportTaskRunner expectedBatchPricesImportTaskRunner = new BatchPricesImportTaskRunner(
        priceCsvServiceMock)
        .withFilePath(ANY_PRICES_FILE_PATH);
    CronTrigger expectedProductCronTrigger = new CronTrigger(ANY_PRODUCT_TASK_CRON);
    CronTrigger expectedPriceCronTrigger = new CronTrigger(ANY_PRICE_TASK_CRON);
    List<Runnable> runnable = runnableCaptor.getAllValues();
    assertThat(runnable.get(0)).isEqualTo(expectedBatchProductsImportTaskRunner);
    assertThat(runnable.get(1)).isEqualTo(expectedBatchPricesImportTaskRunner);
    List<CronTrigger> cronTriggers = cronTriggerCaptor.getAllValues();
    assertThat(cronTriggers.get(0)).isEqualTo(expectedProductCronTrigger);
    assertThat(cronTriggers.get(1)).isEqualTo(expectedPriceCronTrigger);
  }

  @Test
  @DisplayName("Given some correct configuration, when the initialized tasks are launched, it calls expected services.")
  void correctConfiguration_launchTaskRunner_callServices() {
    when(environmentVariableServiceMock.load(BATCH_PRODUCTS_IMPORT_CRON)).thenReturn(ANY_PRODUCT_TASK_CRON);
    when(environmentVariableServiceMock.load(BATCH_PRICES_IMPORT_CRON)).thenReturn(ANY_PRICE_TASK_CRON);
    when(environmentVariableServiceMock.load(BATCH_PRODUCTS_FILE_PATH)).thenReturn(ANY_PRODUCTS_FILE_PATH);
    when(environmentVariableServiceMock.load(BATCH_PRICES_FILE_PATH)).thenReturn(ANY_PRICES_FILE_PATH);

    cut.initialiseScheduler();

    verify(taskSchedulerMock, times(2)).schedule(runnableCaptor.capture(), cronTriggerCaptor.capture());
    List<Runnable> runnable = runnableCaptor.getAllValues();
    // launch the product task runner and verify it calls products csv service
    runnable.get(0).run();
    verify(productCsvServiceMock).saveProductsFromFile(ANY_PRODUCTS_FILE_PATH);
    // launch the price task runner and verify it calls prices csv service
    runnable.get(1).run();
    verify(priceCsvServiceMock).savePricesFromFile(ANY_PRICES_FILE_PATH);

  }
}
