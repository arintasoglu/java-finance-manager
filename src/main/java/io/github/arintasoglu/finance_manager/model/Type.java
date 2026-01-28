package io.github.arintasoglu.finance_manager.model;

public enum Type {
	INCOME, EXPENSE;

	public static Type fromString(String string) {
		if (string.equals("INCOME"))
			return INCOME;
		else
			return EXPENSE;

	}

}
