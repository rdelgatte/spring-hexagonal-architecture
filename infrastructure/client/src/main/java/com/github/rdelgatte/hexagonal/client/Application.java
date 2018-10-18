package com.github.rdelgatte.hexagonal.client;


import com.fasterxml.jackson.databind.Module;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

/**
 * Main application
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

  public static void main(String[] args) {
    // Sonar complains that this context is never closed, however it implements AutoClosable (see https://jira.sonarsource.com/browse/SONARJAVA-1687)
    SpringApplication.run(Application.class, args); // NOSONAR
  }

  @Bean
  public Module vavrFriendlyJackson() {
    return new VavrModule();
  }

}

