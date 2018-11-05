package com.rdelgatte.hexagonal.client.csv.configuration;

import com.rdelgatte.hexagonal.client.csv.scheduler.BatchScheduler;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

  private BatchScheduler batchScheduler;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    initialisePlatform();
  }

  private void initialisePlatform() {
    batchScheduler.initialiseScheduler();
  }

}
