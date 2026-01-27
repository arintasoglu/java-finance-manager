package io.github.arintasoglu.finance_manager.ui;

import java.util.Scanner;
import java.util.UUID;

import io.github.arintasoglu.finance_manager.model.Account;
import io.github.arintasoglu.finance_manager.model.Role;
import io.github.arintasoglu.finance_manager.service.AccountService;
import io.github.arintasoglu.finance_manager.service.AuthService;
import io.github.arintasoglu.finance_manager.service.Session;

public class AdminMenu {
	Account account = null;
	AuthService auth = new AuthService();
	AccountService ac = new AccountService();
	Session ses = new Session();

	Scanner scan = new Scanner(System.in);

	public void show(Account curr) {
		ses.login(curr);

		boolean inv = true;

		while (inv) {
			System.out.println("1. Neues Benutzerkonto anlegen");
			System.out.println("2. Eigene Benutzerkonten anzeigen");
			System.out.println("3. Benutzerkonto löschen");
			System.out.println("4. Abmelden");

			System.out.print("Auswahl: ");
			int choice = scan.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Bitte geben Sie den Benutzernamen ein: ");
				String benutzername = scan.next();
				System.out.println("Bitte geben Sie das Passwort ein: ");
				String password = scan.next();
				Role role = Role.USER;
				account = ac.addUser(UUID.randomUUID(), benutzername, ses.getCurrentUser().getUsername(), password,
						role);

				break;
			case 2:
				System.out.println("Von Ihnen erstellte Benutzerkonten: ");
				String email = ses.getCurrentUser().getUsername();
				ac.findAll(email);
				break;
			case 3:
				System.out.println("Bitte geben Sie den Benutzernamen des Kontos ein, das Sie löschen möchten: ");
				String username = scan.next();
				String ad = ses.getCurrentUser().getUsername();
				ac.delete(username, ad);
				break;
			case 4:
				inv = false;
				break;

			default:
				System.out.println("ungültige Eingabe");

			}

		}
		scan.close();
	}

}
