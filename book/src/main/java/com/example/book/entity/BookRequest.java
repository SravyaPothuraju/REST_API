package com.example.book.entity;


import lombok.Data;


@Data
public class BookRequest {

	private Long id;
	private String name;
	private String author;
	private String description;
	private double rating;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public BookRequest(Long id, String name, String author, String description, double rating) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.description = description;
		this.rating = rating;
	}

	public BookRequest() {

	}

	@Override
	public String toString() {
		return "BookRequest [id=" + id + ", name=" + name + ", author=" + author + ", description=" + description
				+ ", rating=" + rating + "]";
	}

}