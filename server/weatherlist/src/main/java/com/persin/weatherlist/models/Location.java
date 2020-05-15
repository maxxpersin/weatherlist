package com.persin.weatherlist.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "locations")
public class Location {

	@Id
	private String id;
	private String owner;
	private String lat;
	private String lon;
	private String name;

	public Location() {

	}

	private Location(Builder b) {
		this.id = b.id;
		this.owner = b.owner;
		this.lat = b.lat;
		this.lon = b.lon;
		this.name = b.name;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", owner=" + owner + ", lat=" + lat + ", lon=" + lon + ", name=" + name + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static class Builder {
		private String id;
		private String owner;
		private String lat;
		private String lon;
		private String name;

		public Location build() {
			return new Location(this);
		}

		public Builder owner(String owner) {
			this.owner = owner;
			return this;
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder lat(String lat) {
			this.lat = lat;
			return this;
		}

		public Builder lon(String lon) {
			this.lon = lon;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}
	}
}
