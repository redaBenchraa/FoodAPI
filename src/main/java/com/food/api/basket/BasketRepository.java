package com.food.api.basket;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface BasketRepository extends CrudRepository<Basket, Long>{
	//List<Basket> findByUserId(String id);
}
