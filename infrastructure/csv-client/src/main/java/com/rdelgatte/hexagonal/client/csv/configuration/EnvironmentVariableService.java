package com.rdelgatte.hexagonal.client.csv.configuration;

import static io.vavr.API.Option;

import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnvironmentVariableService {

  private Environment environment;

  private String load(String environmentVariable) {
    return Option(environment.getProperty(environmentVariable))
        .filter(string -> !string.isEmpty())
        .getOrElseThrow(() -> new NoSuchElementException("Missing environment variable: " + environmentVariable));
  }

  /**
   * Retrieve configuration property using [Spring's order](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html)
   */
  public String load(EnvironmentVariableEnum environmentVariable) {
    return load(environmentVariable.getLabel());
  }

}
