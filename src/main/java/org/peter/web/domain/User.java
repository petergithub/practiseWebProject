package org.peter.web.domain;

import java.util.Date;

public class User {
	private Integer userId;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private Date creationDate;

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email
				+ ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}

	// setters and getters

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
