package io.github.arintasoglu.finance_manager.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.model.Account;
import io.github.arintasoglu.finance_manager.model.Type;
import io.github.arintasoglu.finance_manager.service.CategoryService;
import io.github.arintasoglu.finance_manager.service.Session;
import io.github.arintasoglu.finance_manager.service.TransactionService;

public class CustomerMenu {

	Scanner scan = new Scanner(System.in);
	Session ses = new Session();
	TransactionService transac = new TransactionService();
	CategoryService categser = new CategoryService();

	public void show(Account curr) {
		boolean inv = true;

		while (inv) {
			ses.login(curr);
			System.out.println("1. Buchung hinzufügen");
			System.out.println("2. Buchungen anzeigen");
			System.out.println("3. Buchung löschen (nach ID)");
			System.out.println("4. Kategorie hinzufügen");
			System.out.println("5. Kategorie löschen");
			System.out.println("6. Kategorien auflisten");
			System.out.println("7. Reports");
			System.out.println("8. Abmelden");

			System.out.print("Auswahl: ");
			int choice = scan.nextInt();
			UUID account_id = ses.getCurrentUser().getId();

			switch (choice) {
			case 1:

				/* Typ auswählen */
				System.out.println("Bitte wählen Sie den Buchungstyp:");
				System.out.println("1 = Einnahme");
				System.out.println("2 = Ausgabe");

				int t = scan.nextInt();
				Type type;

				if (t == 1) {
					type = Type.INCOME;
				} else if (t == 2) {
					type = Type.EXPENSE;
				} else {
					throw new InvalidInputException(
							"Ungültige Auswahl. Bitte geben Sie 1 (Einnahme) oder 2 (Ausgabe) ein.");
				}

				System.out.print("Bitte geben Sie den Betrag ein: ");
				BigDecimal amount = new BigDecimal(scan.next());

				System.out.print("Bitte geben Sie das Datum ein (yyyy-MM-dd): ");
				LocalDate date = LocalDate.parse(scan.next());

				System.out.print("Optional: Beschreibung eingeben (ohne Leerzeichen) oder 0 für keine:");
				String description = scan.next();

				System.out.print("Bitte geben Sie den Kategorienamen ein: ");
				String category = scan.next();
				transac.addTransaction(account_id, type, amount, date, description, category);
				System.out.println("Buchung wurde erfolgreich hinzugefügt.");
				break;

			case 2:
				System.out.println(" Buchungen anzeigen");
				transac.listTransactions(account_id);
				break;

			case 3:
				System.out.println("Buchung löschen (per ID)");
				System.out.print("Bitte geben Sie die Buchungs-ID ein: ");
				int id = scan.nextInt();
				transac.deleteTransaction(id, account_id);
				break;

			case 4:
				System.out.println("Kategorie hinzugüfen");
				System.out.print("Bitte geben Sie den Kategorienamen ein:");
				String name = scan.next();
				categser.addCategory(account_id, name);
				break;

			case 5:
				System.out.println("Kategorie löschen");
				System.out.println("Bitte geben Sie den Namen der Kategorie ein, die Sie löschen möchten:");
				String cat_name = scan.next();
				categser.deleteCategory(account_id, cat_name);
				break;

			case 6:
				System.out.println("Kategorie auflisten");
				categser.listCategories(account_id);
				break;
			case 7:
				System.out.println("Reports");
				break;
			case 8:
				inv = false;
				System.out.println("Sie wurden erfolgreich abgemeldet.");
				break;

			default:
				System.out.println("ungültige Eingabe");

			}
		}
	}

}
