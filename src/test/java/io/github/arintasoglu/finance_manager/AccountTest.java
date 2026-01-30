package io.github.arintasoglu.finance_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.arintasoglu.finance_manager.exception.AuthenticationException;
import io.github.arintasoglu.finance_manager.exception.ErrorMessages;
import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.model.Account;
import io.github.arintasoglu.finance_manager.model.Role;
import io.github.arintasoglu.finance_manager.repository.AccountRepository;
import io.github.arintasoglu.finance_manager.service.AccountService;
import io.github.arintasoglu.finance_manager.service.AuthService;
import io.github.arintasoglu.finance_manager.util.PasswordUtil;

@ExtendWith(MockitoExtension.class)
public class AccountTest {
	@Mock
	AccountRepository repo;

	private AuthService auth;
	private AccountService accser;

	@BeforeEach
	void setup() {
		auth = new AuthService(repo);
		accser = new AccountService(repo);
	}

	@Test
	void login_withCorrectPassword_shouldSucceed() {
		String rawPassword = "geheim";
		String hash = PasswordUtil.hash(rawPassword);

		Account acc = new Account(UUID.randomUUID(), "user", "admin", hash, Role.USER);
		when(repo.findByUsername("user")).thenReturn(acc);

		Account result = auth.loginUser("user", rawPassword);

		assertEquals("user", result.getUsername());
	}

	@Test
	void login_withWrongPassword_shouldThrow() {
		String hash = PasswordUtil.hash("geheim");
		Account acc = new Account(UUID.randomUUID(), "user", "user@mail.com", hash, Role.USER);
		when(repo.findByUsername("user")).thenReturn(acc);

		AuthenticationException ex = assertThrows(AuthenticationException.class,
				() -> auth.loginUser("user", "falsch"));

		assertEquals(ErrorMessages.LOGIN_FAILED, ex.getMessage());
	}

	@Test
	void addAccount_duplicateUsername_shouldThrow() {

		when(repo.existsByUsername("user")).thenReturn(true);

		InvalidInputException ex = assertThrows(InvalidInputException.class,
				() -> accser.addUser(UUID.randomUUID(), "user", "admin", "123", Role.USER));
		assertEquals(ErrorMessages.USERNAME_EXISTS, ex.getMessage());
	}

	@Test
	void addAccount_invalidEmail_shouldThrow() {

		InvalidInputException ex = assertThrows(InvalidInputException.class,
				() -> accser.addAccount(UUID.randomUUID(), "admin", "xx", "password", Role.ADMIN));
		assertEquals(ErrorMessages.EMAIL_INVALID, ex.getMessage());
	}

}
