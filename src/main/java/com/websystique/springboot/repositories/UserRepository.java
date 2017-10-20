package com.websystique.springboot.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.websystique.springboot.model.User;

public interface UserRepository extends MongoRepository<User, String>{
	
	public User findById(String id);
	public User findByName(String name);
	

}
