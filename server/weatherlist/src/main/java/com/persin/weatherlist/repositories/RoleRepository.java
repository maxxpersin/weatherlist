package com.persin.weatherlist.repositories;

import org.springframework.stereotype.Repository;

import com.persin.weatherlist.models.Role;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

}
