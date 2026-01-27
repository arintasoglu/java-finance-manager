package io.github.arintasoglu.finance_manager.ui;

import java.util.Scanner;
import java.util.UUID;

import io.github.arintasoglu.finance_manager.exception.AuthenticationException;
import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.model.Account;
import io.github.arintasoglu.finance_manager.model.Role;
import io.github.arintasoglu.finance_manager.service.AccountService;
import io.github.arintasoglu.finance_manager.service.AuthService;
import io.github.arintasoglu.finance_manager.service.Session;
import io.github.arintasoglu.finance_manager.util.PasswordUtil;

public class LoginMenu {
	Scanner scan = new Scanner(System.in);
	Session ses = new Session();

	public void show() {
		System.out.println("1. Admin-Anmeldung");
		System.out.println("2. Benutzer-Anmeldung");
		System.out.println("3. neue Admin-Konto Erstellen");

		System.out.print("Auswahl: ");
		int choice = scan.nextInt();
		Account account = null;
		AuthService auth = new AuthService();
		AdminMenu admin = new AdminMenu();
		AccountService ac = new AccountService();
		CustomerMenu benutzer = new CustomerMenu();

		switch (choice) {

		case 1:

			while (account == null) {

				try {
					System.out.println("Enter your username:");
					String username = scan.next();

					System.out.println("Enter your password:");
					String password = scan.next();

					account = auth.loginAdmin(username, password);

				} catch (InvalidInputException | AuthenticationException e) {
					System.out.println(e.getMessage());
					System.out.print("Erneut versuchen? (J = ja / N = beenden): ");
					String answer = scan.next();

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
					System.out.println("Enter your username:");
					String username = scan.next();

					System.out.println("Enter your password:");
					String password = scan.next();

					account = auth.loginUser(username, password);

				} catch (InvalidInputException | AuthenticationException e) {
					System.out.println(e.getMessage());
					System.out.print("Erneut versuchen? (J = ja / N = beenden): ");
					String answer = scan.next();

					if (answer.equalsIgnoreCase("N")) {
						System.out.println("Programm wird beendet.");
						System.exit(0);
					}
				}
			}
			ses.login(account);
			benutzer.show();
			break;

		case 3: {

			while (account == null) {

				try {
					System.out.println("Enter your username:");
					String username = scan.next();

					System.out.println("Enter your e-mail address:");
					String email = scan.next();

					System.out.println("Enter your password:");
					String password = scan.next();

					Role role = Role.ADMIN;

					account = ac.addAccount(UUID.randomUUID(), username, email, password, role);

					System.out.println("Account erfolgreich erstellt!");

				} catch (InvalidInputException e) {
					System.out.println(e.getMessage());
					System.out.print("Erneut versuchen? (J = ja / N = beenden): ");
					String answer = scan.next();

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
