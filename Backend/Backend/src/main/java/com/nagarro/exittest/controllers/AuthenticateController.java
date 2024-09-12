package com.nagarro.exittest.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.exittest.config.JwtUtils;
import com.nagarro.exittest.impl.UserDetailsServiceImpl;
import com.nagarro.exittest.models.JwtRequest;
import com.nagarro.exittest.models.JwtResponse;
import com.nagarro.exittest.models.User;

@RestController
@CrossOrigin("*")
public class AuthenticateController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl detailsServiceImpl;

	@Autowired
	private JwtUtils jwtUtils;

	/**
	 * Generates a JWT token for the given username and password.
	 * 
	 * @param jwtRequest - JWT request object containing username and password
	 * @return ResponseEntity containing the generated JWT token
	 * @throws Exception if the user is not found
	 */
	@PostMapping("/generate-token")
	@CrossOrigin("*")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		try {
			authenticate(jwtRequest.getUserName(), jwtRequest.getPassword());
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User not found");
		}
		UserDetails userDetails = this.detailsServiceImpl.loadUserByUsername(jwtRequest.getUserName());
		System.out.println(userDetails);
		String token = this.jwtUtils.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	/**
	 * Authenticates the user using the provided username and password.
	 * 
	 * @param username - User's username
	 * @param password - User's password
	 * @throws Exception if the user is disabled or the credentials are invalid
	 */
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("User Disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("Bad Credentials!!");
		}
	}

	/**
	 * Retrieves the current authenticated user.
	 * 
	 * @param principal - The principal object representing the currently authenticated user
	 * @return The User object representing the current user
	 */
	@GetMapping("/current-user")
	@CrossOrigin("*")
	public User getCurrentUser(Principal principal) {
		System.out.println(principal.getName());
		return ((User) this.detailsServiceImpl.loadUserByUsername(principal.getName()));
	}
}
