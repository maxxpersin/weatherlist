package com.persin.weatherlist.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.persin.weatherlist.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
	public Optional<User> findByUsername(String username);
}
