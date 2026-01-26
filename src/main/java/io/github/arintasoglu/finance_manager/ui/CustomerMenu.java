package io.github.arintasoglu.finance_manager.ui;

import java.util.Scanner;

public class CustomerMenu {

	Scanner scan = new Scanner(System.in);

	public void show() {
		System.out.println("1. Buchung hinzufügen");
		System.out.println("2. Buchungen anzeigen");
		System.out.println("3. Buchung löschen (per ID) ");
		System.out.println("4. Kategorie hinzugüfen");
		System.out.println("5. Kategorie löschen");
		System.out.println("5. Reports");
		System.out.println("6. Abmelden");

		System.out.print("Auswahl: ");
		int choice = scan.nextInt();

		switch (choice) {
		case 1:
			System.out.println("TODO: Buchung hinzufügen");
			break;
		case 2:
			System.out.println("TODO: Buchung hinzufügen");
			break;
		case 3:
			System.out.println("TODO: Buchung hinzufügen");
			break;
		case 4:
			System.out.println("TODO: Buchung hinzufügen");
			break;
		case 5:
			System.out.println("TODO: Buchung hinzufügen");
			break;
		case 6:
			System.out.println("TODO: Buchung hinzufügen");
			break;
		default:
			System.out.println("ungültige Eingabe");

		}
	}

}
