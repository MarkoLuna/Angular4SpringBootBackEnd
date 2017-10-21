package com.springboot;

import com.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springboot.repositories.UserRepository;

// same as @Configuration @EnableAutoConfiguration @ComponentScan combined
@SpringBootApplication(scanBasePackages = { "com.springboot" })
public class SpringBootRestApiApp implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiApp.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		this.userRepository.deleteAll();

		this.userRepository.save(new User("Marcos", 23, 30000));
		this.userRepository.save(new User("Gerardo", 17, 1000));

		// fetch all users
		System.out.println("Users found with findAll():");
		System.out.println("-------------------------------");
		for (User user : this.userRepository.findAll()) {
			System.out.println(user);
		}

		// fetch an individual user
		System.out.println("Users found with findByFirstName('Marcos'):");
		System.out.println("--------------------------------");
		System.out.println(userRepository.findByName("Marcos"));

		System.out.println("Users found with findByLastName('Gerardo'):");
		System.out.println("--------------------------------");
		System.out.println(userRepository.findByName("Gerardo"));
	}
}
