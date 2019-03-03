package com.food.api.basket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.api.user.User;
import com.food.api.user.UserRepository;
import com.food.api.user.UserService;

@Service
public class BasketService {

	@Autowired
	private BasketRepository basketRepository;
	
	@Autowired
	private UserService userService;
	
	public Basket getBasket(Long id) {
		return basketRepository.findById(id).orElse(new Basket());
	}
	
	/*public List<Basket> getUserBaskets(String email) {
		return basketRepository.findByUserId(email);
	}
	*/
	public Basket saveBasket(String email) {
		User user = userService.save(email);
		Basket basket = new Basket();
		basket.setUser(user);
		return basketRepository.save(basket);
	}
	
	public void deleteBasket(Long id) {
		Basket basket = basketRepository.findById(id).get();
		basketRepository.delete(basket);
	}
	
}
