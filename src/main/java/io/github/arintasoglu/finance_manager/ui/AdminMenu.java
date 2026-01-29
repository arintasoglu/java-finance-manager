package io.github.arintasoglu.finance_manager.ui;

import java.util.UUID;

import io.github.arintasoglu.finance_manager.exception.AuthenticationException;
import io.github.arintasoglu.finance_manager.exception.CategoryInUseException;
import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.exception.NotFoundException;
import io.github.arintasoglu.finance_manager.exception.UnauthorizedAccessException;
import io.github.arintasoglu.finance_manager.model.Account;
import io.github.arintasoglu.finance_manager.model.Role;
import io.github.arintasoglu.finance_manager.service.AccountService;
import io.github.arintasoglu.finance_manager.service.AuthService;
import io.github.arintasoglu.finance_manager.service.Session;
import io.github.arintasoglu.finance_manager.util.ConsoleInput;

public class AdminMenu {

	private final ConsoleInput in;
	private final Session ses;

	private final AccountService ac = new AccountService();

	public AdminMenu(ConsoleInput in, Session ses) {
		this.in = in;
		this.ses = ses;
	}

	Account account = null;
	AuthService auth = new AuthService();

	public void show(Account curr) {
		ses.login(curr);

		boolean inv = true;

		while (inv) {
			System.out.println("1. Neues Benutzerkonto anlegen");
			System.out.println("2. Eigene Benutzerkonten anzeigen");
			System.out.println("3. Benutzerkonto löschen");
			System.out.println("4. Abmelden");

			int choice = in.readInt("Auswahl: ");

			try {

				switch (choice) {
				case 1:
					String benutzername = in.readNonBlank("Benutzername: ");
					String password = in.readNonBlank("Passwort: ");
					Role role = Role.USER;
					account = ac.addUser(UUID.randomUUID(), benutzername, ses.getCurrentUser().getUsername(), password,
							role);
					System.out.println("Benutzerkonto erstellt.");

					break;
				case 2:
					System.out.println("Von Ihnen erstellte Benutzerkonten: ");
					String email = ses.getCurrentUser().getUsername();
					ac.findAll(email);
					break;
				case 3:
					String username = in.readNonBlank("Benutzername zum Löschen: ");
					String ad = ses.getCurrentUser().getUsername();
					ac.delete(username, ad);
					System.out.println("Erfolgreich: Benutzerkonto wurde gelöscht.");
					break;
				case 4:
					inv = false;
					ses.logout();
					System.out.println("Abmeldung erfolgreich.");
					break;

				default:
					System.out.println("ungültige Eingabe");

				}

			} catch (InvalidInputException | NotFoundException | UnauthorizedAccessException | AuthenticationException
					| CategoryInUseException e) {
				System.out.println("Fehler: " + e.getMessage());
			}

		}

	}

}
