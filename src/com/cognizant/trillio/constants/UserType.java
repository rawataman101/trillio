package com.cognizant.trillio.constants;

public enum UserType { // no methods and instance variables

	USER("user"), EDITOR("editor"), CHIEF_EDITOR("chiefeditor");

	private UserType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	private String name;

}