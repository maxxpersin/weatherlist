package com.persin.weatherlist.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.persin.weatherlist.models.Photo;

@Repository
public interface PhotoRepository extends MongoRepository<Photo, String>{
	public Photo findByOwner(String owner);
}
