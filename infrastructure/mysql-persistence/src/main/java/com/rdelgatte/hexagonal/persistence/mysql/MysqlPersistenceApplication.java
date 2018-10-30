package com.rdelgatte.hexagonal.persistence.mysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MysqlPersistenceApplication {

  public static void main(String[] args) {
    SpringApplication.run(MysqlPersistenceApplication.class, args);
  }
}
