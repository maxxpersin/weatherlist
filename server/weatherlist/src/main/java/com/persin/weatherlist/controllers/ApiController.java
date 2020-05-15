package com.persin.weatherlist.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.persin.weatherlist.models.Location;
import com.persin.weatherlist.models.Photo;
import com.persin.weatherlist.models.User;
import com.persin.weatherlist.services.InitGenerator;
import com.persin.weatherlist.services.LocationService;
import com.persin.weatherlist.services.UserService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

	@Autowired
	private UserService userService;

	@Autowired
	private LocationService locationService;

	@RequestMapping(produces = {
			"application/json" }, value = "/users/{uid}/locations/{lid}", method = RequestMethod.GET)
	public Object getLocationData(@PathVariable(required = true) String uid,
			@PathVariable(required = true) String lid) {
		return locationService.getLocation(lid);
	}

	@RequestMapping(produces = { "application/json" }, value = "/users/{uid}/locations", method = RequestMethod.GET)
	public List<Location> getLocations(@PathVariable(required = true) String uid) {
		return locationService.getLocations(uid);
	}

	@RequestMapping(produces = { "application/json" }, value = "/users/{uid}/locations/gov", method = RequestMethod.GET)
	public Object findLocation(@PathVariable(required = true) String uid,
			@RequestParam(name = "lat", required = true) String lat,
			@RequestParam(name = "lon", required = true) String lon) {
		return locationService.getLocation(lat, lon);
	}

	@RequestMapping(produces = { "application/json" }, value = "/users/{uid}/locations", method = RequestMethod.POST)
	public Location addLocation(@PathVariable(required = true) String uid,
			@RequestBody(required = true) Location location) {
		return locationService.addLocation(uid, location);
	}

	@RequestMapping(produces = {
			"application/json" }, value = "/users/{uid}/profilepicture", method = RequestMethod.POST)
	public Photo changeProfilePicture(@PathVariable String uid,
			@RequestParam(name = "file", required = true) MultipartFile file, HttpServletResponse response) {
		try {
			return userService.setPhoto(uid, file);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
	}
	
	@RequestMapping(produces = {"application/json"}, value="/users/{uid}/profilepicture", method=RequestMethod.GET)
	public Photo getProfilePicture(@PathVariable String uid) {
		return userService.getPhotoByOwnerId(uid);
	}
	
	@RequestMapping(method = RequestMethod.HEAD, value = "/")
	public void csrf(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
