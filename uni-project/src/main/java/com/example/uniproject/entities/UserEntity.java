package com.example.uniproject.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({"products"})
public class UserEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5824816829682291854L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "username", length = 255, nullable = false, unique = true)
	private String username;

	// password hash algorithm sha-256
	@Column(name = "password", length = 64, nullable = false)
	private String password;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<ProductEntity> products;

	public UserEntity() {
	}

	public UserEntity(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<ProductEntity> getProducts() {
		return products;
	}

	public void setProducts(List<ProductEntity> products) {
		this.products = products;
	}
}
