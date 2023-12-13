package com.zakariarachida.jwt.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zakariarachida.jwt.api.entity.User;
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByuserNameAllIgnoreCase(String username);
}
