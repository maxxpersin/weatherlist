package com.persin.weatherlist.services;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.persin.weatherlist.models.Role;
import com.persin.weatherlist.models.User;
import com.persin.weatherlist.repositories.RoleRepository;
import com.persin.weatherlist.repositories.UserRepository;

@Service
public class InitGenerator {

	@Autowired private UserRepository userRepository;
	@Autowired org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;
	@Autowired PasswordEncoder encoder;
	@Autowired RoleRepository roleRepository;
	
	
	private Role mockRoles() {
		Role r = new Role.Builder().role("ROLE_USER").build();
		
		return roleRepository.save(r);
		
	}
	

	private List<User> mockUsers(Role r) {
		List<Role> roles = new ArrayList<Role>();
		roles.add(r);
		Set<Role> s1 = new HashSet<Role>(roles.subList(0, 1));
		
		return Arrays.asList(
				new User.Builder().email("tom@gmail.com").username("tom").firstName("Tom").lastName("Persin")
						.password(this.encoder.encode("123")).roles(s1).build(),
				new User.Builder().email("sue@gmail.com").username("sue").firstName("Sue").lastName("Jeffers")
						.password(this.encoder.encode("abc")).roles(s1).build(),
				new User.Builder().email("fin@gmail.com").username("fin").firstName("Fin").lastName("The Human")
						.password(this.encoder.encode("xyz")).roles(s1).build())
				.stream().map(user -> {
					return userRepository.save(user);
				}).collect(Collectors.toList());
	}

	@PostConstruct
	public void mockData() {
		mongoTemplate.getDb().drop();
		List<User> users = userRepository.findAll();
		if (users.isEmpty()) {
			Role r = mockRoles();
			mockUsers(r).forEach(user -> {
				System.out.println(user.toString());
			});
		}
		
//		userRepository.deleteAll();
//		roleRepository.deleteAll();
		
	}

}
