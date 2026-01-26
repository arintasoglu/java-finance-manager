package io.github.arintasoglu.finance_manager.model;

public enum Role {
	ADMIN, CUSTOMER;

	public static Role fromString(String string) {
		if (string.equals("ADMIN"))
			return ADMIN;
		else
			return CUSTOMER;

	}

}
