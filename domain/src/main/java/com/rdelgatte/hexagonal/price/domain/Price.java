package com.rdelgatte.hexagonal.price.domain;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class Price {

  private UUID id = UUID.randomUUID();
  private int value = 0;
}
