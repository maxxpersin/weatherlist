package com.persin.weatherlist.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
public class Role {
	@Id
	private String id;
	private String role;

	public Role() {

	}

	private Role(Builder b) {
		this.role = b.role;
		this.id = b.id;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static class Builder {
		private String role;
		private String id;

		public Role build() {
			return new Role(this);
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder role(String role) {
			this.role = role;
			return this;
		}
	}

}
