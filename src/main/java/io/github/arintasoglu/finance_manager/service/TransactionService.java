package io.github.arintasoglu.finance_manager.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.exception.NotFoundException;
import io.github.arintasoglu.finance_manager.model.Transaction;
import io.github.arintasoglu.finance_manager.model.Type;
import io.github.arintasoglu.finance_manager.repository.JdbcCategoryRepository;
import io.github.arintasoglu.finance_manager.repository.JdbcTransactionRepository;

public class TransactionService {
	JdbcCategoryRepository cate = new JdbcCategoryRepository();
	JdbcTransactionRepository tran = new JdbcTransactionRepository();

	public void addTransaction(UUID account_id, Type type, BigDecimal amount, LocalDate date, String desc,
			String category) {

		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidInputException("Der Betrag muss größer als 0 sein.");
		}
		if (!(cate.existsByAccountIdAndName(account_id, category))) {
			throw new InvalidInputException("Es existiert keine Kategorie mit dem angegebenen Namen.");
		}
		Transaction transaction = new Transaction(account_id, type, amount, date, desc, category);
		tran.save(transaction);

	}

	public void listTransactions(UUID account_id) {
		List<Transaction> list = tran.findAllByAccountId(account_id);
		if (list == null | list.isEmpty()) {
			System.out.println("Es sind keine Buchungen vorhanden.");
		}
		for (Transaction t : list) {
			System.out.println(t);
		}

	}

	public void deleteTransaction(int txId, UUID account_id) {
		int exe = tran.deleteByIdAndAccountId(txId, account_id);
		if (exe == 0)
			throw new NotFoundException("Es wurde keine Buchung mit der angegebenen ID gefunden.");

	}

}
