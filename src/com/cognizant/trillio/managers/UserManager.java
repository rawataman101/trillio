package com.cognizant.trillio.managers;

import java.util.List;

import com.cognizant.trillio.constants.Gender;
import com.cognizant.trillio.constants.UserType;
import com.cognizant.trillio.dao.UserDao;
import com.cognizant.trillio.entities.User;

// service layer
public class UserManager { // singleton class
	private static UserManager instance = new UserManager();
	private static UserDao userDao = new UserDao();

	private UserManager() {
	}

	public static UserManager getInstance() {
		return instance;
	}

	public User createUser(long id, String email, String password, String firstName, Gender gender ,UserType userType) {
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setGender(gender);
		user.setId(id);
		user.setPassword(password);
		user.setUserType(userType);
		return user;
	}

	public List<User> getUsers() {
		return userDao.getUsers();
	}
}
