package com.example.demo.model;


import java.io.Serializable;

import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "menus")
public class Menu implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	

	@Column(name = "published")
	private boolean published;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "tutorials_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Restaurant restaurant;
	public Menu() {
	}
	public Menu(String title, String description, boolean published, Restaurant restaurant) {
		this.title = title;
		this.description = description;
		this.published = published;
		this.restaurant =restaurant;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public void setTutorial(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getDescription() {
		return description;
	}
	public String getRestaurant() {
		return restaurant.getTitle();
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean isPublished) {
		this.published = isPublished;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", title=" + title + ", desc=" + description + ", restaurant=" + restaurant.getTitle() + "]";
	}
}
