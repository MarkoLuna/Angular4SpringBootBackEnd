package com.springboot.repositories;

import com.springboot.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{
	
	public User findById(String id);
	public User findByName(String name);
	

}
