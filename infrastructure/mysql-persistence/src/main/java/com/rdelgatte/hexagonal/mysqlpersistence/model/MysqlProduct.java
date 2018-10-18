package com.rdelgatte.hexagonal.mysqlpersistence.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MysqlProduct {

  @Id
  private UUID id;
  private String code = "";
  private String label = "";

  public MysqlProduct fromDomain(com.rdelgatte.hexagonal.product.domain.Product product) {
    return new MysqlProduct(product.getId(), product.getCode(), product.getLabel());
  }

  public com.rdelgatte.hexagonal.product.domain.Product toDomain() {
    return new com.rdelgatte.hexagonal.product.domain.Product(id, code, label);
  }
}
