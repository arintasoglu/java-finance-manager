package io.github.arintasoglu.finance_manager.ui;

import java.util.UUID;
import io.github.arintasoglu.finance_manager.exception.AuthenticationException;
import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.model.Account;
import io.github.arintasoglu.finance_manager.model.Role;
import io.github.arintasoglu.finance_manager.service.AccountService;
import io.github.arintasoglu.finance_manager.service.AuthService;
import io.github.arintasoglu.finance_manager.service.Session;
import io.github.arintasoglu.finance_manager.util.ConsoleInput;

public class LoginMenu {

	private final ConsoleInput in;
	private final Session ses;
	private final AdminMenu admin;
	private final CustomerMenu benutzer;

	private final AuthService auth = new AuthService();
	private final AccountService ac = new AccountService();

	public LoginMenu(ConsoleInput in, Session ses) {
		this.in = in;
		this.ses = ses;
		this.admin = new AdminMenu(in, ses);
		this.benutzer = new CustomerMenu(in, ses);
	}

	public void show() {
		System.out.println("1. Admin-Anmeldung");
		System.out.println("2. Benutzer-Anmeldung");
		System.out.println("3. neue Admin-Konto Erstellen");

		int choice = in.readInt("Auswahl: ");

		Account account = null;

		switch (choice) {

		case 1:

			while (account == null) {

				try {
					String username = in.readNonBlank("Benutzername: ");
					String password = in.readNonBlank("Passwort: ");

					account = auth.loginAdmin(username, password);

				} catch (InvalidInputException | AuthenticationException e) {
					System.out.println(e.getMessage());
					String answer = in.readNonBlank("Erneut versuchen? (J/N): ");

					if (answer.equalsIgnoreCase("N")) {
						System.out.println("Programm wird beendet.");
						System.exit(0);
					}
				}
			}

			ses.login(account);
			admin.show(account);
			break;

		case 2:
			while (account == null) {

				try {
					String username = in.readNonBlank("Benutzername: ");
					String password = in.readNonBlank("Passwort: ");

					account = auth.loginUser(username, password);

				} catch (InvalidInputException | AuthenticationException e) {
					System.out.println(e.getMessage());
					String answer = in.readNonBlank("Erneut versuchen? (J/N): ");

					if (answer.equalsIgnoreCase("N")) {
						System.out.println("Programm wird beendet.");
						System.exit(0);
					}
				}
			}
			ses.login(account);
			benutzer.show(account);
			break;

		case 3: {

			while (account == null) {

				try {
					String username = in.readNonBlank("Benutzername: ");
					String email = in.readNonBlank("E-Mail-Adresse: ");
					String password = in.readNonBlank("Passwort: ");

					Role role = Role.ADMIN;

					account = ac.addAccount(UUID.randomUUID(), username, email, password, role);

					System.out.println("Erfolgreich: Administratorkonto wurde erstellt.");

				} catch (InvalidInputException e) {
					System.out.println(e.getMessage());
					String answer = in.readNonBlank("Erneut versuchen? (J/N): ");

					if (answer.equalsIgnoreCase("N")) {
						System.out.println("Programm wird beendet.");
						System.exit(0);
					}
				}
			}

			ses.login(account);

			admin.show(account);

			break;
		}

		default:
			System.out.println("ungültige Eingabe");

		}

	}

}
