package io.github.arintasoglu.finance_manager;

import java.util.Scanner;

import io.github.arintasoglu.finance_manager.service.Session;
import io.github.arintasoglu.finance_manager.ui.LoginMenu;
import io.github.arintasoglu.finance_manager.util.ConsoleInput;

public class App {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);  
        ConsoleInput in = new ConsoleInput(scanner);
        Session session = new Session();

        LoginMenu login = new LoginMenu(in, session);
        login.show();

	}
}
