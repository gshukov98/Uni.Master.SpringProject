package com.example.uniproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.uniproject.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	UserEntity findByUsername(String username);

	UserEntity findUserByUsernameAndPassword(String username, String password);
}
