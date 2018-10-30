# Hexagonal architecture with Spring 

Some references about hexagonal architecture / DDD (Domain Driven Design):
- https://www.youtube.com/watch?v=th4AgBcrEHA (introduction by Alistair Cockburn)
- https://www.youtube.com/watch?v=Hi5aDfRe-aE (préz devoxx)
- https://en.wikipedia.org/wiki/Domain-driven_design
- https://blog.xebia.fr/2016/03/16/perennisez-votre-metier-avec-larchitecture-hexagonale/
- https://blog.octo.com/architecture-hexagonale-trois-principes-et-un-exemple-dimplementation/

## Start-up

- Run `docker-compose up -d` in infrastructure/mysql-persistence to start dockerized mysql DB
- Start `rest-client` application 

#### Products

- Create a product: `curl --header "Content-Type: application/json" --request POST --data '{"code": "1234","label": "My awesome product"}' http://localhost:8080/products`
- List products: `curl --header "Content-Type: application/json" --request GET http://localhost:8080/products` 
- Find product: `curl --header "Content-Type: application/json" --request GET http://localhost:8080/products/1234` 

If you try to run the create product statement twice with the same product code, you will get an exception handled by domain:
```
{"timestamp":"2018-10-30T09:55:10.837+0000","status":500,"error":"Internal Server Error","message":"Product 1234 already exists so you can't create it","path":"/products"}
```

#### Prices

- Create a price: `curl --header "Content-Type: application/json" --request POST --data '{"value": 1234}' http://localhost:8080/prices`
- List prices: `curl --header "Content-Type: application/json" --request GET http://localhost:8080/prices` 

## Domain

`domain` is implemented as a standalone Java module which has no dependencies to any framework (neither spring).

Actually it has only two dependencies so we can use `Lombok` and `Vavr` as libraries to make data manipulation easier.

```
  <!-- There should be no dependencies in domain for hexagonal architecture except basic helper libraries such as lombok and vavr -->
  <dependencies>
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>${lombok.version}</version>
    </dependency>
    <dependency>
      <groupId>io.vavr</groupId>
      <artifactId>vavr</artifactId>
      <version>${vavr.version}</version>
    </dependency>
  </dependencies>
  ```
 
 As defined in hexagonal architecture, in `domain` you will only find the data model definition and *API* + *SPI* interface definitions.
 
## Infrastructure

Here we will define the interactions over the domain so it implements these *ports* to implements the way to :
- interact with the domain (triggering actions)
- define where the domain gets its resources (persistence)

To do so, there are multiple sub-modules under infrastructure.

## API (Application Provider Interfaces)

It describes all the ports for everything that needs **to query the domain**. 

These interfaces are implemented by the domain.

#### Rest client

This module aims to expose some *rest* entry points to interact with products and prices.
 
- Module: `rest-client`
- Parent module: `infrastructure`
- Dependencies: `spring-boot`

In `ApplicationConfiguration`, we can find the definition of both `ProductService` and `PriceService` adapters from the domain where we can decide which repository we should use (in memory | mysql).

In the following example (default), we define the **persistence mode** for each service we want to use:
- `ProductService` will use `MysqlProductRepository` (meaning products will be persisted in a mysql DB)
- `PriceService` will use `InMemoryPriceRepository` (meaning prices will be persisted in memory)

```
  private MysqlProductRepositoryImpl mysqlProductRepository;

  private InMemoryPriceRepository inMemoryPriceRepository() {
    return new InMemoryPriceRepository();
  }

  @Bean
  public ProductService productService() {
    return new ProductServiceImpl(mysqlProductRepository);
  }

  @Bean
  public PriceService priceService() {
    return new PriceServiceImpl(inMemoryPriceRepository());
  }
```

Note: to use outside package beans like for mysql persistence beans, we need to explicit the package to configuration as below:
```
@ComponentScan(basePackages = {
    "com.rdelgatte.hexagonal.persistence.mysql",
})
``` 

## SPI

It gathers all the ports required by the domain **to retrieve information or get some services from third parties**. 

These interfaces are defined in the domain and implemented by the right side of the infrastructure.

#### In-memory persistence

Through this implementation, domain data can be persisted in memory by implementing SPI ports for domain `ProductRepository` and `PriceRepository`.
Here is an example:

```
@Data
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class InMemoryProductRepository implements ProductRepository {

  private List<Product> inMemoryProducts = List();

  public Product addProduct(Product product) {
    this.inMemoryProducts = getInMemoryProducts().append(product);
    return product;
  }

  public void deleteProduct(UUID productId) {
    this.inMemoryProducts = getInMemoryProducts().filter(product -> !product.getId().equals(productId));
  }

  public Option<Product> findProductByCode(String code) {
    return getInMemoryProducts().find(product -> product.getCode().equals(code));
  }

  public List<Product> findAllProducts() {
    return getInMemoryProducts();
  }
}
```

#### Mysql persistence

Using spring data and mysql connector, this Spring boot project define the persistence of domain data by implementing the same SPI ports as before.
 
  
 
  