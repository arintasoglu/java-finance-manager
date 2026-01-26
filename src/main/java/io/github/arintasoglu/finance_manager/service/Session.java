package io.github.arintasoglu.finance_manager.service;

import io.github.arintasoglu.finance_manager.model.Account;

public class Session {
	private Account currentUser;

	public void login(Account account) {
		this.currentUser = account;
	}

	public void logout() {
		this.currentUser = null;
	}

	public boolean isLoggedIn() {
		return currentUser != null;
	}

	public Account getCurrentUser() {
		return currentUser;
	}

}
