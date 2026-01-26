package io.github.arintasoglu.finance_manager.service;

import io.github.arintasoglu.finance_manager.exception.AuthenticationException;
import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.model.Account;
import io.github.arintasoglu.finance_manager.model.Role;
import io.github.arintasoglu.finance_manager.repository.JdbcAccountRepository;

public class AuthService {

	public Account loginAdmin(String username, String password) {

		JdbcAccountRepository a = new JdbcAccountRepository();
		Account ac = a.findByUsername(username);
		if (username == null || username.isBlank()) {
			throw new InvalidInputException("Bitte Benutzername/E-Mail eingeben.");
		}
		if (password == null || username.isBlank()) {
			throw new InvalidInputException("Bitte Passwort eingeben.");
		}

		if (ac == null) {
			throw new AuthenticationException("Benutzername/E-Mail oder Passwort ist falsch.");
		}

		if (ac.getRole() != Role.ADMIN) {
			throw new AuthenticationException("Kein Admin-Zugriff.");
		}

		return ac;

	}

	public Account loginUser(String username, String password) {
		JdbcAccountRepository a = new JdbcAccountRepository();
		Account ac = a.findByUsername(username);
		if (username == null || username.isBlank()) {
			throw new InvalidInputException("Bitte Benutzername/E-Mail eingeben.");
		}
		if (password == null || username.isBlank()) {
			throw new InvalidInputException("Bitte Passwort eingeben.");
		}

		if (ac == null) {
			throw new AuthenticationException("Benutzername/E-Mail oder Passwort ist falsch.");
		}

		if (ac.getRole() != Role.CUSTOMER) {
			throw new AuthenticationException("Kein Benutzer-Zugriff.");
		}

		return ac;
	}

}
