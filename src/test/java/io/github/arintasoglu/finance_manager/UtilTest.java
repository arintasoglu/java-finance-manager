package io.github.arintasoglu.finance_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.github.arintasoglu.finance_manager.util.PasswordUtil;

public class UtilTest {

	@Test
	void passwordHash_matchesCorrectPassword() {
		String password = "123";
		String stored = PasswordUtil.hash(password);
		assertEquals(true, PasswordUtil.verify(password, stored));

	}

	@Test
	void passwordHash_rejectsWrongPassword() {
		String password = "123";
		String stored = PasswordUtil.hash(password);
		String wrong = "xxx";
		assertEquals(false, PasswordUtil.verify(wrong, stored));

	}

}
