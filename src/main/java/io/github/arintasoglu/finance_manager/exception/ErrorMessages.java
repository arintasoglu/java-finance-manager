package io.github.arintasoglu.finance_manager.exception;

public final class ErrorMessages {

	private ErrorMessages() {
	}

	public static final String USERNAME_EMPTY = "Der Benutzername darf nicht leer sein.";
	public static final String EMAIL_INVALID = "E-Mail-Adresse ist ungültig.";
	public static final String PASSWORD_EMPTY = "Passwort darf nicht leer sein.";
	public static final String USERNAME_EXISTS = "Benutzername existiert bereits. Bitte einen anderen wählen.";

	public static final String LOGIN_FAILED = "Anmeldung fehlgeschlagen. Benutzername oder Passwort ist falsch.";
	public static final String ADMIN_REQUIRED = "Kein Zugriff: Administratorkonto erforderlich.";
	public static final String USER_REQUIRED = "Kein Zugriff: Benutzerkonto erforderlich.";

	
	public static final String CATEGORY_EMPTY = "Der Kategoriename darf nicht leer sein.";
	public static final String CATEGORY_EXISTS = "Eine Kategorie mit diesem Namen existiert bereits.";
	public static final String CATEGORY_NOT_FOUND = "Es existiert keine Kategorie mit dem angegebenen Namen.";

	
	public static final String AMOUNT_NOT_POSITIVE = "Der Betrag muss größer als 0 sein.";
	public static final String TX_NOT_FOUND = "Es wurde keine Buchung mit der angegebenen ID gefunden.";

}
