package io.github.arintasoglu.finance_manager.service;

import java.util.List;
import java.util.UUID;

import io.github.arintasoglu.finance_manager.exception.DataAccessException;
import io.github.arintasoglu.finance_manager.exception.ErrorMessages;
import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.exception.NotFoundException;
import io.github.arintasoglu.finance_manager.exception.UnauthorizedAccessException;
import io.github.arintasoglu.finance_manager.model.Account;
import io.github.arintasoglu.finance_manager.model.Role;
import io.github.arintasoglu.finance_manager.repository.AccountRepository;
import io.github.arintasoglu.finance_manager.repository.JdbcAccountRepository;
import io.github.arintasoglu.finance_manager.util.PasswordUtil;

public class AccountService {
	private final AccountRepository repo;

	public AccountService() {
		this(new JdbcAccountRepository());
	}

	public AccountService(AccountRepository repo) {
		this.repo = repo;
	}

	Account acc = null;

	public Account addAccount(UUID id, String username, String email, String password, Role role) {

		if (username == null || username.isBlank()) {
			throw new InvalidInputException(ErrorMessages.USERNAME_EMPTY);
		}

		if (!isValidEmail(email)) {
			throw new InvalidInputException(ErrorMessages.EMAIL_INVALID);
		}

		if (password == null || password.isBlank()) {
			throw new InvalidInputException(ErrorMessages.PASSWORD_EMPTY);
		}
		if (repo.existsByUsername(username))
			throw new InvalidInputException(ErrorMessages.USERNAME_EXISTS);

		acc = new Account(id, username, email, PasswordUtil.hash(password), role);
		int x = repo.createAccount(acc);
		return acc;

	}

	public Account addUser(UUID id, String username, String email, String password, Role role) {

		if (username == null || username.isBlank()) {
			throw new InvalidInputException(ErrorMessages.USERNAME_EMPTY);
		}

		if (password == null || password.isBlank()) {
			throw new InvalidInputException(ErrorMessages.PASSWORD_EMPTY);
		}

		if (repo.existsByUsername(username))
			throw new InvalidInputException(ErrorMessages.USERNAME_EXISTS);

		acc = new Account(id, username, email, PasswordUtil.hash(password), role);
		int x = repo.createAccount(acc);
		return acc;

	}

	public boolean isValidEmail(String email) {
		if (email == null)
			return false;

		String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

		return email.matches(regex);
	}

	public void findAll(String email) {
		List<Account> x = repo.findAllUser(email);
		if (x.isEmpty()) {
			throw new InvalidInputException("Sie haben bisher kein Benutzerkonto erstellt.");
		}
		for (Account a : x) {
			System.out.println(a.getUsername());
		}

	}

	public void delete(String username, String admin) {

		if (username == null || username.isBlank()) {
			throw new InvalidInputException(ErrorMessages.USERNAME_EMPTY);
		}
		Account ac = repo.findByUsername(username);
		if (ac == null) {
			throw new NotFoundException("Der angegebene Benutzername existiert nicht.");
		}

		if (!(ac.getEmail().equals(admin))) {
			throw new UnauthorizedAccessException("Sie haben keine Berechtigung für diese Aktion.");

		}
		int x = repo.delete(username);
		if (x == 0) {
			throw new DataAccessException("Das Konto konnte nicht gelöscht werden. Bitte versuchen Sie es erneut.");
		}

	}

}
