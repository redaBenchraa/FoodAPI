package com.food.api.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService{

	@Autowired
	private UserRepository userRepository;
	
	public User save(String id) {
		User exisitingUser = userRepository.findById(id).orElse(null);
		if(exisitingUser == null) {
			User user = new User();
			user.setId(id);
			return userRepository.save(user);
		}
		return exisitingUser;
	}
	
	public User getUser(String id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public List<User> getUsers() {
		return (List<User>) userRepository.findAll();
	}
	
	
}
