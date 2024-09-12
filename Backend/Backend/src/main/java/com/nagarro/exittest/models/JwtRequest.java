package com.nagarro.exittest.models;

public class JwtRequest {

    // User name for authentication
    String userName;

    // Password for authentication
    String password;

    /**
     * Default constructor for JwtRequest.
     */
    public JwtRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor for JwtRequest with user name and password.
     * 
     * @param userName the user name
     * @param password the password
     */
    public JwtRequest(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
    }

    /**
     * Get the user name.
     * 
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the user name.
     * 
     * @param userName the user name to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Get the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
