package io.github.arintasoglu.finance_manager.service;

import io.github.arintasoglu.finance_manager.exception.AuthenticationException;
import io.github.arintasoglu.finance_manager.exception.ErrorMessages;
import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.model.Account;
import io.github.arintasoglu.finance_manager.model.Role;
import io.github.arintasoglu.finance_manager.repository.AccountRepository;
import io.github.arintasoglu.finance_manager.repository.JdbcAccountRepository;
import io.github.arintasoglu.finance_manager.util.PasswordUtil;

public class AuthService {
	private final AccountRepository repo;

	public AuthService() {
		this(new JdbcAccountRepository());
	}

	public AuthService(AccountRepository repo) {
		this.repo = repo;
	}

	public Account loginAdmin(String username, String password) {

		if (username == null || username.isBlank()) {
			throw new InvalidInputException(ErrorMessages.USERNAME_EMPTY);
		}
		if (password == null || password.isBlank()) {
			throw new InvalidInputException(ErrorMessages.PASSWORD_EMPTY);
		}

		Account ac = repo.findByUsername(username);
		if (ac == null) {
			throw new AuthenticationException(ErrorMessages.LOGIN_FAILED);
		}

		if (ac.getRole() != Role.ADMIN) {
			throw new AuthenticationException(ErrorMessages.ADMIN_REQUIRED);
		}
		if (!(PasswordUtil.verify(password, ac.getPassword()))) {
			throw new AuthenticationException(ErrorMessages.LOGIN_FAILED);

		}

		return ac;

	}

	public Account loginUser(String username, String password) {

		if (username == null || username.isBlank()) {
			throw new InvalidInputException(ErrorMessages.USERNAME_EMPTY);
		}
		if (password == null || password.isBlank()) {
			throw new InvalidInputException(ErrorMessages.PASSWORD_EMPTY);
		}
		Account ac = repo.findByUsername(username);

		if (ac == null) {
			throw new AuthenticationException(ErrorMessages.LOGIN_FAILED);
		}

		if (!(PasswordUtil.verify(password, ac.getPassword()))) {
			throw new AuthenticationException(ErrorMessages.LOGIN_FAILED);

		}

		if (ac.getRole() != Role.USER) {
			throw new AuthenticationException(ErrorMessages.USER_REQUIRED);
		}

		return ac;
	}

}
