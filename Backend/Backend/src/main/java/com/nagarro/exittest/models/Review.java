package com.nagarro.exittest.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; // Unique identifier for the review

	private int rating; // Rating given for the product
	private String title; // Title or heading of the review


	private String description; // Description or content of the review
	private Long productId; // Identifier of the associated product
	private Boolean approved = false; // Flag indicating whether the review is approved or not
	private Boolean cancel = false; // Flag indicating whether the review is cancelled or not
	private String productName;
	private String productCode;
	
	
	public Review() {
		super();
	}

	public Review(@NotNull @Min(1) @Max(5) int rating, String heading, @Size(min = 20, max = 400) String review,
			Long productId, String productName, String productCode) {
		super();
		this.rating = rating;
		this.title = heading;
		this.description = review;
		this.productId = productId;
		//this.setProductName(ProductName);
		this.productName= productName;
		this.productCode=productCode;
	
	
	}
	
	// Getters and setters for the review fields

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String heading) {
		this.title = heading;
	}

	public String getReview() {
		return description;
	}

	public void setReview(String review) {
		this.description = review;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public long getId() {
		return id;
	}

	public Boolean getApproved() {
		return approved;
	}
	
	public Boolean getCancel() {
		return cancel;
	}
	
	public void setCancel(Boolean cancel) {
		this.cancel = cancel;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
