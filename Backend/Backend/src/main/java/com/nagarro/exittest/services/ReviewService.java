package com.nagarro.exittest.services;

import java.util.List;

import com.nagarro.exittest.models.Review;

public interface ReviewService {
	
	/**
	 * Adds a new review to the database.
	 * 
	 * @param review The review to be added.
	 * @return The added review.
	 */
	public Review addReview(Review review);

	/**
	 * Retrieves a list of reviews for the specified product ID.
	 * 
	 * @param productId The ID of the product.
	 * @return A list of reviews for the specified product.
	 */
	public List<Review> findByProductId(Long productId);

	/**
	 * Retrieves a list of all reviews for the specified product ID, including canceled reviews.
	 * 
	 * @param productId The ID of the product.
	 * @return A list of all reviews for the specified product.
	 */
	public List<Review> showProductReview(Long productId);

	/**
	 * Retrieves a list of all reviews from the database.
	 * 
	 * @return A list of all reviews.
	 */
	public List<Review> findAll();

	/**
	 * Retrieves a list of all approved reviews from the database.
	 * 
	 * @return A list of all approved reviews.
	 */
	public List<Review> findAllReviews();
	
	/**
	 * Saves a review in the database.
	 * 
	 * @param review The review to be saved.
	 */
	public void save(Review review);

}
