package com.nagarro.exittest.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.exittest.impl.ProductServiceImpl;
import com.nagarro.exittest.impl.ReviewServiceImpl;
import com.nagarro.exittest.impl.UserServiceImpl;
import com.nagarro.exittest.models.Product;
import com.nagarro.exittest.models.Review;
import com.nagarro.exittest.models.Role;
import com.nagarro.exittest.models.Status;
import com.nagarro.exittest.models.User;
import com.nagarro.exittest.models.UserRole;

import javassist.NotFoundException;

@RestController
@CrossOrigin("*")
public class MainController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private ProductServiceImpl productService;

	@Autowired
	private ReviewServiceImpl reviewService;

	/**
	 * Register a new user.
	 * 
	 * @param user the user to register
	 * @return the registered user
	 * @throws Exception if the user already exists
	 */
	@PostMapping("/user/register")
	@CrossOrigin("*")
	public User register(@RequestBody User user) throws Exception {
		try {
			// Encode the password
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			Set<UserRole> roles = new HashSet<>();
			Role role = new Role();
			if (user.getEmail().equalsIgnoreCase("abhishek@gmail.com")) {
				role.setRoleId(44L);
				role.setRoleName("ADMIN");
			} else {
				role.setRoleId(45L);
				role.setRoleName("NORMAL");
			}

			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole(role);
			roles.add(userRole);
			return this.userService.createUser(user, roles);
		} catch (Exception e) {
			throw new Exception("User with email " + user.getEmail() + " already exists!!");
		}
	}

	/**
	 * Save a product.
	 * 
	 * @param product the product to save
	 * @return the status of the operation
	 */
	@PostMapping("/allProducts")
	public Status saveProduct(@Valid @RequestBody Product product) {
		return productService.addProduct(product);
	}

	/**
	 * Fetch products based on a search query.
	 * 
	 * @param query the search query
	 * @return the list of matching products
	 * @throws Exception if the products are not found
	 */
	@GetMapping("/products")
	@CrossOrigin("*")
	public List<Product> product(@RequestParam String query) throws Exception {
		try {
			List<Product> products = this.productService.fetchProductByProductNameOrBrandOrProductCode(query);
			return products;
		} catch (Exception e) {
			throw new Exception("Product Not Found!");
		}
	}

	/**
	 * Fetch all products.
	 * 
	 * @return the list of all products
	 * @throws Exception if the products are not found
	 */
	@GetMapping("/allProducts")
	@CrossOrigin("*")
	public List<Product> products() throws Exception {
		try {
		List<Product> products = this.productService.findAll();
		return products;
		} catch (Exception e) {
		throw new Exception("Product Not Found!");
		}
     }
	
	/**
	 * Fetch all users.
	 *
	 * @return the list of all users
	 */
	@GetMapping("/user/users")
	@CrossOrigin("*")
	public List<User> showUser() {
		return this.userService.findAll();
	}

	/**
	 * Fetch all products.
	 *
	 * @return the list of all products
	 */
	@GetMapping("/user/products")
	@CrossOrigin("*")
	public List<Product> showProducts() {
		return this.productService.findAll();
	}

	/**
	 * Fetch all reviews.
	 *
	 * @return the list of all reviews
	 */
	@GetMapping("/user/reviews")
	public List<Review> showReviews() {
		return this.reviewService.findAllReviews();
	}

	/**
	 * Fetch all reviews (admin only).
	 *
	 * @return the list of all reviews
	 */
	@GetMapping("/admin/reviews")
	public List<Review> showAllReviews() {
		return this.reviewService.findAll();
	}

	/**
	 * Add a new product.
	 *
	 * @param product the product to add
	 * @return the added product
	 * @throws Exception if the product cannot be added
	 */
	@CrossOrigin("*")
	@PostMapping("/addProduct")
	public Product addProduct(@RequestBody Product product) throws Exception {
		try {
			System.out.println(product);
			return this.productService.saveProduct(product);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@GetMapping("/getProduct/{productId}")
	@CrossOrigin("*")
	public Product getProductById(@PathVariable long productId) {
	    // Assuming you have a repository or service to fetch the product by ID
	    Product product = productService.showSingleProduct(productId);
	   return product;
	}


	/**
	 * Add a new review.
	 *
	 * @param review the review to add
	 * @return the added review
	 * @throws Exception if the review cannot be added
	 */
	@CrossOrigin("*")
	@PostMapping("/addReview")
	public Review addReview(@RequestBody Review review) throws Exception {
		try {
			System.out.println(review);
			return this.reviewService.addReview(review);
		} catch (Exception e) {
			throw new Exception("Bad Data");
		}
	}

	/**
	 * Fetch reviews for a specific product.
	 *
	 * @param productId the ID of the product
	 * @return the list of reviews for the product
	 * @throws Exception if the product is not found
	 */
	@CrossOrigin("*")
	@GetMapping("products/{productId}/showReviews")
	public List<Review> showProductReview(@PathVariable Long productId) throws Exception {
		try {
			return this.reviewService.showProductReview(productId);
		} catch (Exception e) {
			throw new Exception("Product Not Found");
		}
	}

	/**
	 * Fetch statistics for the home page.
	 *
	 * @return a list of integers representing different statistics
	 */
	@CrossOrigin("*")
	@GetMapping("home/stats")
	public List<Integer> showStats() {
		List<User> users = this.userService.findAll();
		int totalUsers = users.size();
		int posts = this.reviewService.findAllReviews().size();
		int products = this.productService.getNumberofProducts();
		int onlineUsers = 0;
		for (User u : users) {
			if (u.getEnabled()) {
				onlineUsers++;
			}
		}
		List<Integer> stats = new ArrayList<>();
		stats.add(totalUsers);
		stats.add(posts);
		stats.add(onlineUsers);
		stats.add(products);
		return stats;
	}

	/**
	 * Approve a review.
	 *
	 * @param review the review to approve
	 * @return true if the review is approved successfully
	 * @throws Exception
    */
	@PutMapping("review/approve")
	@CrossOrigin("*")
	public Boolean approveReview(@RequestBody Review review) throws Exception {
		try {
			review.setApproved(true);
			this.reviewService.save(review);
			return true;
		} catch (Exception e) {
			throw new Exception("Something went wrong!!");
		}
	}

	/**
	 * Cancel a review.
	 *
	 * @param review the review to cancel
	 * @return true if the review is canceled successfully
	 * @throws Exception if an error occurs during the cancellation
	 */
	@PutMapping("review/cancel")
	@CrossOrigin("*")
	public Boolean cancelReview(@RequestBody Review review) throws Exception {
		try {
			review.setCancel(false);
			this.reviewService.delete(review);
			return true;
		} catch (Exception e) {
			throw new Exception("Something went wrong!!");
		}
	}

	/**
	 * Activate/Deactivate a user.
	 *
	 * @param user the user to activate/deactivate
	 * @return the updated user
	 * @throws Exception if an error occurs during the activation/deactivation
	 */
	@PutMapping("user/active")
	@CrossOrigin("*")
	public User activateUser(@RequestBody User user) throws Exception {
		try {
			if (!user.getEnabled())
				user.setEnabled(true);
			else
				user.setEnabled(false);
			return this.userService.save(user);
		} catch (Exception e) {
			throw new Exception("Something went wrong!!");
		}
	}

	/**
	 * Request reviews for a product.
	 *
	 * @param product the product to request reviews for
	 * @return the list of reviews for the product
	 */
	@PostMapping("review/request")
	@CrossOrigin("*")
	public ResponseEntity<List<Review>> requestReviews(@RequestBody Product product) {
		Product p = this.productService.findByProductCode(product.getProductCode());
		if (p != null) {
			List<Review> reviews = this.reviewService.findByProductId(p.getProductId());
			return ResponseEntity.ok(reviews);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Delete a product.
	 *
	 * @param productId the ID of the product to delete
	 * @throws NotFoundException if the product is not found
	 */
	@DeleteMapping("/delete/{productId}")
	@CrossOrigin("*")
	public void deleteProduct(@PathVariable("productId") Long productId) throws NotFoundException {
		productService.deleteProduct(productId);
	}
}

