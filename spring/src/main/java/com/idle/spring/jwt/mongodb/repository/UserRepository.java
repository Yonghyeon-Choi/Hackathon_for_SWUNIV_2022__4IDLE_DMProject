package com.idle.spring.jwt.mongodb.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.idle.spring.jwt.mongodb.models.User;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByUserid(String userid);

  Boolean existsByUserid(String userid);

  Boolean existsByEmail(String email);
}
