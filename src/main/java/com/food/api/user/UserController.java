package com.food.api.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.basket.BasketService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	@Autowired
	BasketService basketService;
	
	@RequestMapping(method=RequestMethod.GET,value="/users/{id}")
	public User getUser(@PathVariable String id) {
		return userService.getUser(id);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/users")
	public User addUser(@RequestBody User user) {
		return userService.save(user.getId());
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/users/{id}/baskets")
	public void addBasket(@PathVariable String id) {
		basketService.saveBasket(id);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/users")
	public List<User> getUsers() {
		return userService.getUsers();
	}

}
