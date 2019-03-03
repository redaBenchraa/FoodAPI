package com.food.api.product;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<Product, Long>{
	//List<Product> findByBasketId();
}
