package com.persin.weatherlist.models;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "users")
public class User implements UserDetails {
//	@Indexed(unique = true)
	private String username;
	@JsonIgnore
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	//@DBRef
//	private List<Location> locations;
	@DBRef
	@JsonIgnore
	private Set<Role> roles;
//	@DBRef
//	private Photo profilePicture;

	@Id
	private String id;

	public User() {
	}

	private User(Builder b) {
		this.username = b.username;
		this.password = b.password;
		this.firstName = b.firstName;
		this.lastName = b.lastName;
//		this.locations = b.locations;
		this.email = b.email;
		this.roles = b.roles;
		//this.profilePicture = b.profilePicture;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", roles=" + roles + ", id=" + id + "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public List<Location> getLocations() {
//		return locations;
//	}
//
//	public void setLocations(List<Location> locations) {
//		this.locations = locations;
//	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public void setProfilePicture(Photo profilePicture) {
//		this.profilePicture = profilePicture;
//	}
//
//	public Photo getProfilePicture() {
//		return profilePicture;
//	}

	public static class Builder {
		private String username;
		private String password;
		private String firstName;
		private String lastName;
		private String id;
		private String email;
		//private List<Location> locations;
		private Set<Role> roles;
		//private Photo profilePicture;

		public User build() {
			return new User(this);
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

//		public Builder locations(List<Location> locations) {
//			this.locations = locations;
//			return this;
//		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder roles(Set<Role> roles) {
			this.roles = roles;
			return this;
		}

//		public Builder profilePicture(Photo profilePicture) {
//			this.profilePicture = profilePicture;
//			return this;
//		}
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toSet());
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}
}
