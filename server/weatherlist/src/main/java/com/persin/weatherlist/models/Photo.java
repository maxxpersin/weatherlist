package com.persin.weatherlist.models;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "photos")
public class Photo {
	@Id
	private String id;
	private Binary image;
	private String owner;
	private String type;

	public Photo() {

	}

	private Photo(Builder b) {
		this.id = b.id;
		this.image = b.image;
		this.owner = b.owner;
		this.type = b.type;
	}

	@Override
	public String toString() {
		return "Photo [id=" + id + ", image=" + image + ", owner=" + owner + ", type=" + type + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Binary getImage() {
		return image;
	}

	public void setImage(Binary image) {
		this.image = image;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static class Builder {
		private String id;
		private Binary image;
		private String owner;
		private String type;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder image(Binary image) {
			this.image = image;
			return this;
		}

		public Builder owner(String owner) {
			this.owner = owner;
			return this;
		}

		public Builder type(String type) {
			this.type = type;
			return this;
		}

		public Photo build() {
			return new Photo(this);
		}
	}
}
