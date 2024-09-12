package com.nagarro.exittest.models;

public class JwtResponse {
	
	// JWT token
	String token;

	/**
	 * Default constructor for JwtResponse.
	 */
	public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor for JwtResponse with token.
	 * 
	 * @param token the JWT token
	 */
	public JwtResponse(String token) {
		super();
		this.token = token;
	}

	/**
	 * Get the JWT token.
	 * 
	 * @return the JWT token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Set the JWT token.
	 * 
	 * @param token the JWT token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
}
