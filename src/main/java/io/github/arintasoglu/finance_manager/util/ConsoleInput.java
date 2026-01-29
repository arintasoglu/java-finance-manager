package io.github.arintasoglu.finance_manager.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleInput {
	
	private final Scanner scanner;

	public ConsoleInput(Scanner scanner) {
		this.scanner = scanner;
	}

	public String readLine(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine();
	}

	public String readNonBlank(String prompt) {
		while (true) {
			String s = readLine(prompt).trim();
			if (!s.isEmpty())
				return s;
			System.out.println("Eingabe darf nicht leer sein.");
		}
	}

	public int readInt(String prompt) {
		while (true) {
			String s = readLine(prompt).trim();
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				System.out.println("Bitte eine gültige Zahl eingeben.");
			}
		}
	}

	public BigDecimal readPositiveBigDecimal(String prompt) {
		while (true) {
			String s = readLine(prompt).trim();
			try {
				BigDecimal v = new BigDecimal(s);
				if (v.compareTo(BigDecimal.ZERO) > 0)
					return v;
				System.out.println("Betrag muss größer als 0 sein.");
			} catch (NumberFormatException e) {
				System.out.println("Ungültiger Betrag.");
			}
		}
	}

	public LocalDate readDate(String prompt) {
		while (true) {
			String s = readLine(prompt + " (yyyy-MM-dd): ").trim();
			try {
				return LocalDate.parse(s);
			} catch (DateTimeParseException e) {
				System.out.println("Ungültiges Datum.");
			}
		}
	}
}
