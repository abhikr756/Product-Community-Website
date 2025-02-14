package com.nagarro.exittest.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.exittest.models.User;
import com.nagarro.exittest.models.UserRole;
import com.nagarro.exittest.repository.RoleRepository;
import com.nagarro.exittest.repository.UserRepository;
import com.nagarro.exittest.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	// Creates a new user with the provided details and user roles.
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		User local = this.userRepository.getUserByEmail(user.getEmail());
		if (local != null) {
			System.out.println("User exists");
			throw new Exception("User already present!");
		} else {
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
			}

			user.getUserRoles().addAll(userRoles);
			local = this.userRepository.save(user);
			return local;
		}
	}

	// Saves a user in the database.
	public User save(User user) {
		return this.userRepository.save(user);
	}

	// Retrieves a user by their email.
	public User showUser(String email) {
		return this.userRepository.getUserByEmail(email);
	}

	// Retrieves a user by their email and password.
	public User fetchUserByEmailAndPassword(String email, String password) {
		return this.userRepository.findByEmailAndPassword(email, password);
	}

	// Retrieves a list of all users.
	public List<User> findAll() {
		return this.userRepository.findAll();
	}
}
