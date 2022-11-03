package com.cognizant.trillio.entities;

import com.cognizant.trillio.constants.Gender;
import com.cognizant.trillio.constants.UserType;

public class User {
	private long id;
	private String email;
	private String password;
	private String firstName;
	private Gender gender;
	private UserType userType;

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public Gender getGender() {
		return gender;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
//		return "User [id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName
//				+ ", gender=" + gender + ", userType=" + userType + "]";
		return "user " + id;
	}

}
