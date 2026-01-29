package io.github.arintasoglu.finance_manager.service;

import java.util.List;
import java.util.UUID;

import io.github.arintasoglu.finance_manager.exception.DataAccessException;
import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.exception.NotFoundException;
import io.github.arintasoglu.finance_manager.exception.UnauthorizedAccessException;
import io.github.arintasoglu.finance_manager.model.Account;
import io.github.arintasoglu.finance_manager.model.Role;
import io.github.arintasoglu.finance_manager.repository.AccountRepository;
import io.github.arintasoglu.finance_manager.repository.JdbcAccountRepository;
import io.github.arintasoglu.finance_manager.util.PasswordUtil;

public class AccountService {
	AccountRepository a = new JdbcAccountRepository();
	Account acc = null;

	public Account addAccount(UUID id, String username, String email, String password, Role role) {

		if (username == null || username.isBlank()) {
			throw new InvalidInputException("Der Benutzername darf nicht leer sein.");
		}

		if (!isValidEmail(email)) {
			throw new InvalidInputException("E-Mail-Adresse ist ungültig.");
		}

		if (password == null || password.isBlank()) {
			throw new InvalidInputException("Passwort darf nicht leer sein.");
		}
		if (a.existsByUsername(username))
			throw new InvalidInputException("Benutzername existiert bereits. Bitte einen anderen wählen.");

		acc = new Account(id, username, email, PasswordUtil.hash(password), role);
		int x = a.createAccount(acc);
		return acc;

	}

	public Account addUser(UUID id, String username, String email, String password, Role role) {

		if (username == null || username.isBlank()) {
			throw new InvalidInputException("Der Benutzername darf nicht leer sein.");
		}

		if (password == null || password.isBlank()) {
			throw new InvalidInputException("Passwort darf nicht leer sein.");
		}

		if (a.existsByUsername(username))
			throw new InvalidInputException("Benutzername existiert bereits. Bitte einen anderen wählen.");

		acc = new Account(id, username, email, PasswordUtil.hash(password), role);
		int x = a.createAccount(acc);
		return acc;

	}

	public boolean isValidEmail(String email) {
		if (email == null)
			return false;

		String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

		return email.matches(regex);
	}

	public void findAll(String email) {
		List<Account> x = a.findAllUser(email);
		if (x.isEmpty()) {
			throw new InvalidInputException("Sie haben bisher kein Benutzerkonto erstellt.");
		}
		for (Account a : x) {
			System.out.println(a.getUsername());
		}

	}

	public void delete(String username, String admin) {
		JdbcAccountRepository a = new JdbcAccountRepository();
		Account ac = a.findByUsername(username);

		if (username == null || username.isBlank()) {
			throw new InvalidInputException("Username darf nicht leer sein.");
		}
		if (ac == null) {
			throw new NotFoundException("Der angegebene Benutzername existiert nicht.");
		}

		if (!(ac.getEmail().equals(admin))) {
			throw new UnauthorizedAccessException(
					"Sie haben keine Berechtigung für diese Aktion.");

		}
		int x = a.delete(username);
		if (x == 0) {
			throw new DataAccessException("Das Konto konnte nicht gelöscht werden. Bitte versuchen Sie es erneut.");
		}

	}

}
