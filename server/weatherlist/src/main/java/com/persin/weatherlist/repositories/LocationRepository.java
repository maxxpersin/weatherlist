package com.persin.weatherlist.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.persin.weatherlist.models.Location;

@Repository
public interface LocationRepository extends MongoRepository<Location, String>{
	public List<Location> findByOwner(String owner);
}
