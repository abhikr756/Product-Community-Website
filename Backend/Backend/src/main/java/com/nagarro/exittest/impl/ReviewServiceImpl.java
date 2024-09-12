package com.nagarro.exittest.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.exittest.models.Review;
import com.nagarro.exittest.repository.ReviewRepository;
import com.nagarro.exittest.services.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;

	/**
	 * Adds a new review to the database.
	 * 
	 * @param review The review to be added.
	 * @return The added review.
	 */
	public Review addReview(Review review) {
		return this.reviewRepository.save(review);
	}

	/**
	 * Retrieves a list of reviews based on the given product ID.
	 * 
	 * @param productId The ID of the product.
	 * @return A list of reviews for the specified product.
	 */
	public List<Review> findByProductId(Long productId) {
		return this.reviewRepository.findByProductId(productId);
	}

	/**
	 * Retrieves a list of reviews for a specific product.
	 * 
	 * @param productId The ID of the product.
	 * @return A list of reviews for the specified product.
	 */
	public List<Review> showProductReview(Long productId) {
		return this.reviewRepository.findAllById(productId);
	}

	/**
	 * Retrieves all reviews from the database.
	 * 
	 * @return A list of all reviews.
	 */
	public List<Review> findAll() {
		return this.reviewRepository.findAll();
	}

	/**
	 * Retrieves all reviews from the database.
	 * 
	 * @return A list of all reviews.
	 */
	public List<Review> findAllReviews() {
		return this.reviewRepository.findAllReviews();
	}

	/**
	 * Saves a review in the database.
	 * 
	 * @param review The review to be saved.
	 */
	public void save(Review review) {
		this.reviewRepository.save(review);
	}
	
	/**
	 * Deletes a review from the database.
	 * 
	 * @param review The review to be deleted.
	 */
	public void delete(Review review) {
		this.reviewRepository.delete(review);
	}
}
