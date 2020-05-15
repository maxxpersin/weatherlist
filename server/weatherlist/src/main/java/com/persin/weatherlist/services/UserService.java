package com.persin.weatherlist.services;

import java.io.IOException;

import org.apache.el.stream.Optional;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.persin.weatherlist.models.Photo;
import com.persin.weatherlist.models.User;
import com.persin.weatherlist.repositories.PhotoRepository;
import com.persin.weatherlist.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PhotoRepository photoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("No user by that username"));
	}

	public Photo setPhoto(String uid, MultipartFile file) throws IOException, IllegalArgumentException {
		User user = userRepository.findById(uid).orElseThrow(() -> new IllegalArgumentException());
		String type = imageType(file.getOriginalFilename());
		Photo photo = new Photo.Builder().owner(user.getId()).type(type)
				.image(new Binary(BsonBinarySubType.BINARY, file.getBytes())).build();

		Photo oldPhoto = getPhoto(user.getUsername());
		if (oldPhoto != null) {
			photoRepository.delete(oldPhoto);
		}

		// photo = photoRepository.save(photo);
		// user.setProfilePicture(photo);
		return photoRepository.save(photo);
	}

	public Photo getPhoto(String username) throws IllegalArgumentException {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException());
		return photoRepository.findByOwner(user.getId());
	}
	
	public Photo getPhotoByOwnerId(String uid) {
		return photoRepository.findByOwner(uid);
	}

	private static String imageType(String filename) throws IllegalArgumentException {
		String extension = filename.split("\\.")[filename.split("\\.").length - 1].toUpperCase();

		switch (extension) {
		case "PNG":
		case "JPG":
		case "JPEG":
		case "GIF":
			return extension;
		}

		throw new IllegalArgumentException();
	}

}
