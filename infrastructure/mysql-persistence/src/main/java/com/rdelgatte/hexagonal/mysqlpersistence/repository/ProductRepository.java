package com.rdelgatte.hexagonal.mysqlpersistence.repository;

import com.rdelgatte.hexagonal.mysqlpersistence.model.MysqlProduct;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

interface ProductRepository extends CrudRepository<MysqlProduct, UUID> {

}
