package com.example.demo.model;


import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "restaurants")
public class Restaurant implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;


	public Restaurant() {

	}

	public Restaurant(String name, String description, boolean published) {
		this.name = name;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	@Override
	public String toString() {
		return "Tutorial [id=" + id + ", name=" + name + ", desc=" + description + "]";
	}
}
