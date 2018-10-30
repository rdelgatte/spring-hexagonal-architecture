package com.rdelgatte.hexagonal.mysqlpersistence.repository;

import com.rdelgatte.hexagonal.mysqlpersistence.model.MysqlProduct;
import io.vavr.control.Option;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

interface ProductRepository extends CrudRepository<MysqlProduct, UUID> {

  Option<MysqlProduct> findOneByCode(String code);
}
