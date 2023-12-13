package com.zakariarachida.jwt.api;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.zakariarachida.jwt.api.entity.User;
import com.zakariarachida.jwt.api.repository.UserRepository;

@SpringBootApplication
public class SpringSecurityJwtExampleApplication {

	@Autowired
	UserRepository repository ;

	@PostConstruct
	public void initUsers() {

		List<User> users = Stream.of(
				new User(101, "zak", "password", "zak@gmail.com"),
				new User(102, "rachida", "password", "rachida@gmail.com")
		).collect(Collectors.toList());
		
		repository.saveAll(users);
	}


	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtExampleApplication.class, args);
	}

}

