package com.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.springboot.model.User;
import com.springboot.repositories.UserRepository;

public class UsersService {
	
	UserRepository userRepository;
	
	@Autowired
	public UsersService (UserRepository userRepository) {
	this.userRepository = userRepository; 	 
	}
	
	
	public User save(User user) {
		return this.userRepository.save(user);
	}
	
	public User obtainById(String id) {
		return this.userRepository.findById(id);
	}
	
	public User obtainByName(String name) {
		return this.userRepository.findByName(name);
	}
	
	public User update(User user) {
		return this.userRepository.save(user);
	}
	
	public void delete(String id) {
		this.userRepository.delete(id);
	}
	
	public List<User> obtainAll() {
		return this.userRepository.findAll();
	}
	
	public boolean isUserExist(User user) {
		return this.userRepository.findById(user.getId()) != null;
	}
	
}
