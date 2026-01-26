package io.github.arintasoglu.finance_manager.ui;

import java.util.Scanner;

public class AdminMenu {

	Scanner scan = new Scanner(System.in);

	public void show() {
		
		boolean inv = true;
		
		while (inv) {
			System.out.println("1. Neues Konto anlegen");
			System.out.println("2. Konten auflisten");
			System.out.println("3. Konto löschen");
			System.out.println("4. Abmelden");

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
				inv = false;
				break;

			default:
				System.out.println("ungültige Eingabe");

			}

		}
	}

}
