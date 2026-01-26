package io.github.arintasoglu.finance_manager.service;

import java.util.UUID;

import io.github.arintasoglu.finance_manager.exception.DataAccessException;
import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.model.Account;
import io.github.arintasoglu.finance_manager.model.Role;
import io.github.arintasoglu.finance_manager.repository.AccountRepository;
import io.github.arintasoglu.finance_manager.repository.JdbcAccountRepository;

public class AccountService {
	public Account addAccount(UUID id, String username, String email, String password, Role role) {
		AccountRepository a = new JdbcAccountRepository();
		Account acc = null;

		if (username == null || username.isBlank()) {
			throw new InvalidInputException("Username darf nicht leer sein.");
		}

		if (!isValidEmail(email)) {
			throw new InvalidInputException("Email ist ungültig.");
		}

		if (password == null || password.isBlank()) {
			throw new InvalidInputException("Passwort darf nicht leer sein.");
		}
		if (a.existsByUsername(username))
			throw new InvalidInputException("Benutzername existiert bereits. Bitte einen anderen wählen.");


		acc = new Account(id, username, email, password, role);
		int x = a.createAccount(acc);
		return acc;

	}

	public boolean isValidEmail(String email) {
		if (email == null)
			return false;

		String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

		return email.matches(regex);
	}

}
