package io.github.arintasoglu.finance_manager.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtil {
	private PasswordUtil() {
	}

	private static final int COST = 12;

	public static String hash(String plainPassword) {
		String salt = BCrypt.gensalt(COST);
		return BCrypt.hashpw(plainPassword, salt);
	}

	public static boolean verify(String plainPassword, String storedHash) {
		if (plainPassword == null || storedHash == null || storedHash.isBlank()) {
			return false;
		}

		return BCrypt.checkpw(plainPassword, storedHash);
	}

}
