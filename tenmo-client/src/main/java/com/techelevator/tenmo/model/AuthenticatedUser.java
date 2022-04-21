package com.techelevator.tenmo.model;

/*
Model of a user with the token that is created on login
	and needs to be passed to the api to get information from it.
 */
public class AuthenticatedUser {
	
	private String token;
	private User user;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
