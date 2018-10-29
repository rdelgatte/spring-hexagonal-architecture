package com.rdelgatte.hexagonal.product.domain;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class Product {

  private UUID id = UUID.randomUUID();
  private String code = "";
  private String label = "";
}
