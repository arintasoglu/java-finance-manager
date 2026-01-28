package io.github.arintasoglu.finance_manager.model;

import java.util.UUID;

public class Category {
	UUID account_id;
	String name;

	public UUID getAccount_id() {
		return account_id;
	}

	public void setAccount_id(UUID account_id) {
		this.account_id = account_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category(UUID account_id, String name) {
		super();
		this.account_id = account_id;
		this.name = name;
	}

	public Category() {
		super();

	}

	public String toString() {
		return name;
	}

}
