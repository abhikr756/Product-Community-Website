package com.nagarro.exittest.services;

import java.util.List;
import java.util.Set;

import com.nagarro.exittest.models.User;
import com.nagarro.exittest.models.UserRole;

public interface UserService {

	// Creates a new user with the given details and associated roles.
	public User createUser(User user, Set<UserRole> userRoles) throws Exception;

	// Saves a user in the database.
	public User save(User user);

	// Retrieves a user by their email.
	public User showUser(String email);

	// Retrieves a user by their email and password.
	public User fetchUserByEmailAndPassword(String email, String password);

	// Retrieves all users.
	public List<User> findAll();

}
