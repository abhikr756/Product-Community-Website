package com.nagarro.exittest.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Role {

	@Id
	private Long roleId; // Unique identifier for the role
	private String roleName; // Name of the role

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	private Set<UserRole> userRoles = new HashSet<>(); // Set of user roles associated with this role

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//getter setters

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
