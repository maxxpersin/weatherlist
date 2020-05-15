package com.persin.weatherlist.services;

import java.util.List;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import com.persin.weatherlist.models.Location;
import com.persin.weatherlist.models.User;
import com.persin.weatherlist.repositories.LocationRepository;
import com.persin.weatherlist.repositories.UserRepository;

import org.springframework.web.reactive.function.client.WebClient;

@Service
public class LocationService {
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Location addLocation(String uid, Location location) throws IllegalArgumentException {
//		User user = userRepository.findById(uid).orElseThrow(() -> new IllegalArgumentException());
//		List<Location> userLocations = user.getLocations();
		List<Location> userLocations = locationRepository.findByOwner(uid);
		if (userLocations == null) {
			userLocations = new ArrayList<Location>();
		}
		
		boolean passed = true;
		for (Location loc : userLocations) {
			if (loc.getName().equals(location.getName())) {
				passed = false;
				break;
			}
		}
		if (!passed) {
			return location;
		}
		
		//location = locationRepository.save(location);
		//userLocations.add(location);
		//user.setLocations(userLocations);
		//userRepository.save(user);
		
		return locationRepository.save(location);
	}
	
	public List<Location> getLocations(String uid) {
		return locationRepository.findByOwner(uid);
	}
	
	public Object getLocation(String lid) {
		Location location = locationRepository.findById(lid).orElseThrow(() -> new IllegalArgumentException());
		return getLocation(location.getLat(), location.getLon());
	}
	
	
	public Object getLocation(String lat, String lon) {
		URI url = new DefaultUriBuilderFactory().builder()
					.scheme("https")
					.host("forecast.weather.gov")
					.path("/MapClick.php")
					.queryParam("lat", lat == null ? "" : lat)
					.queryParam("lon", lon == null ? "" : lon)
					.queryParam("FcstType", "json")
					.build();
		
		//System.out.println(url.toString());
					
		// WebClient to build request
		WebClient client = WebClient.builder()
				.baseUrl(url.toString())
				.build();
		
		Object result = client.get()
				.retrieve()
				.bodyToMono(Object.class)
				.block(); //Await response
		
		return result;
	}

}
