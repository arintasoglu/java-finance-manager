package io.github.arintasoglu.finance_manager.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

import io.github.arintasoglu.finance_manager.exception.AuthenticationException;
import io.github.arintasoglu.finance_manager.exception.CategoryInUseException;
import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.exception.NotFoundException;
import io.github.arintasoglu.finance_manager.exception.UnauthorizedAccessException;
import io.github.arintasoglu.finance_manager.model.Account;
import io.github.arintasoglu.finance_manager.model.Type;
import io.github.arintasoglu.finance_manager.service.CategoryService;
import io.github.arintasoglu.finance_manager.service.Session;
import io.github.arintasoglu.finance_manager.service.TransactionService;
import io.github.arintasoglu.finance_manager.util.ConsoleInput;

public class CustomerMenu {
	private final ConsoleInput in;
	private final Session ses;

	private final TransactionService transac = new TransactionService();
	private final CategoryService categser = new CategoryService();

	public CustomerMenu(ConsoleInput in, Session ses) {
		this.in = in;
		this.ses = ses;
	}

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

			int choice = in.readInt("Auswahl: ");
			UUID account_id = ses.getCurrentUser().getId();
			try {
				switch (choice) {
				case 1:

					/* Typ auswählen */
					System.out.println("Bitte wählen Sie den Buchungstyp:");
					System.out.println("1 = Einnahme");
					System.out.println("2 = Ausgabe");

					int t = in.readInt("Typ (1=Einnahme, 2=Ausgabe): ");
					Type type;
					if (t == 1)
						type = Type.INCOME;
					else if (t == 2)
						type = Type.EXPENSE;
					else
						throw new InvalidInputException("Bitte 1 oder 2 eingeben.");

					BigDecimal amount = in.readPositiveBigDecimal("Betrag: ");
					LocalDate date = in.readDate("Datum");
					String description = in.readLine("Beschreibung (optional, Enter = leer): ").trim();
					if (description.isEmpty())
						description = null;

					String category = in.readNonBlank("Kategorie: ");

					transac.addTransaction(account_id, type, amount, date, description, category);
					System.out.println("Buchung wurde erfolgreich hinzugefügt.");
					break;

				case 2:
					System.out.println("Buchungen:");
					transac.listTransactions(account_id);
					break;

				case 3:
					System.out.println("Buchung löschen (per ID)");
					int id = in.readInt("Buchungs-ID: ");
					transac.deleteTransaction(id, account_id);
					System.out.println("Buchung gelöscht.");
					break;

				case 4:
					System.out.println("Kategorie hinzugüfen");
					String name = in.readNonBlank("Kategoriename: ");
					categser.addCategory(account_id, name);
					System.out.println("Kategorie hinzugefügt.");
					break;

				case 5:
					String cat_name = in.readNonBlank("Kategorie löschen (Name): ");
					categser.deleteCategory(account_id, cat_name);
					System.out.println("Kategorie gelöscht.");
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
