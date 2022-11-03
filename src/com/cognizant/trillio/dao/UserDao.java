package com.cognizant.trillio.dao;

import java.util.List;

import com.cognizant.trillio.DataStore;
import com.cognizant.trillio.entities.User;

public class UserDao {
	public List<User> getUsers() {
		return DataStore.getUsers();
	} 

}
