package com.nagarro.exittest.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Product{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId; // Unique identifier for the product
	
	private String productName; // Name of the product
	private String brand; // Brand of the product
	private double prodPrice; // Price of the product

	
	private String productCode; // Code associated with the product

	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	//@JoinColumn(name = "prod_code_fk", referencedColumnName = "productId")
	@JoinColumn(name = "productId", referencedColumnName = "productId")
	private List<Review> prodReviews; // List of reviews associated with the product explain 



	public Product(Long productId, String productName, String brand, double prodPrice, String productCode,
			List<Review> prodReviews) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.brand = brand;
		this.prodPrice = prodPrice;
		this.productCode = productCode;
		this.prodReviews = prodReviews;
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Getters and setters for the product fields

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Long getProductId() {
		return productId;
	}

	public double getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(double prodPrice) {
		this.prodPrice = prodPrice;
	}

	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}

}
