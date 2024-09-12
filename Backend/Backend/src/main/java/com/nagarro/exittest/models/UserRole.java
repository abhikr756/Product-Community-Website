package com.nagarro.exittest.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserRole {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userRoleId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Role role;

	public UserRole() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the associated User.
	 * 
	 * @return the associated User
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Set the associated User.
	 * 
	 * @param user the User to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Get the associated Role.
	 * 
	 * @return the associated Role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Set the associated Role.
	 * 
	 * @param role the Role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * Get the user role ID.
	 * 
	 * @return the user role ID
	 */
	public Long getUserRoleId() {
		return userRoleId;
	}
}
