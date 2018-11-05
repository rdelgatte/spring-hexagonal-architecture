package com.rdelgatte.hexagonal.client.csv.scheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Provide a single instance of {@link ThreadPoolTaskScheduler} to handle asynchronous executions
 */
@Configuration
public class ThreadPoolTaskSchedulerConfig {

  /**
   * Configure the threadPoolTaskScheduler with a pool of 1 asynchronous task. This is a temporary ugly hack to ensure
   * imports are done in sequence to save performance, until catalog import is done in a more efficient manner. Related
   * thread name will be prefixed by "ThreadPoolTaskScheduler"
   *
   * @return The configured bean threadPoolTaskScheduler
   */
  @Bean
  public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
    ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    threadPoolTaskScheduler.setPoolSize(1);
    threadPoolTaskScheduler.setThreadNamePrefix("Catalog import scheduler");
    return threadPoolTaskScheduler;
  }
}
