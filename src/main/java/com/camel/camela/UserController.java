package com.camel.camela;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.camel.camela.entity.User;
import com.camel.camela.repo.UserRepository2;

@RestController

public class UserController {
	
	@Autowired
	UserRepository2 userRepo;
	
	@GetMapping(value="users2")
	public List<User> getUsers() {
		return userRepo.findAll();
	}
	
	@GetMapping(value="users2/{id}")
	public User getUsers(@PathVariable ("id") Long id) {
		return userRepo.findById(id).get();
	}
	
	@PostMapping("users2")
	public User postUser(@RequestBody User user) {
		userRepo.save(user);
		return user;
	}

}
