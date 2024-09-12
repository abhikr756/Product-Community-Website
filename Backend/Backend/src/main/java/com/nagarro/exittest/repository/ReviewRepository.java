package com.nagarro.exittest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nagarro.exittest.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	/**
	 * Retrieves a list of reviews based on the given product ID.
	 * 
	 * @param productId The ID of the product.
	 * @return A list of reviews for the specified product.
	 */
	@Query(value = "from Review r where r.productId = ?1")
	List<Review> findAllById(Long productId);

	/**
	 * Retrieves all reviews from the database.
	 * 
	 * @return A list of all reviews.
	 */
	@Query(value = "from Review r where r.approved = 0 OR r.approved = null")
	List<Review> findAll();

	/**
	 * Retrieves all approved reviews from the database.
	 * 
	 * @return A list of approved reviews.
	 */
	@Query(value = "from Review r where r.approved = 1")
	List<Review> findAllReviews();
	
	/**
	 * Retrieves a list of reviews based on the given product ID.
	 * 
	 * @param productId The ID of the product.
	 * @return A list of reviews for the specified product.
	 */
	List<Review> findByProductId(Long productId);
}
