package io.github.arintasoglu.finance_manager.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.github.arintasoglu.finance_manager.exception.ErrorMessages;
import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.exception.NotFoundException;
import io.github.arintasoglu.finance_manager.model.Transaction;
import io.github.arintasoglu.finance_manager.model.Type;
import io.github.arintasoglu.finance_manager.repository.CategoryRepository;
import io.github.arintasoglu.finance_manager.repository.JdbcCategoryRepository;
import io.github.arintasoglu.finance_manager.repository.JdbcTransactionRepository;
import io.github.arintasoglu.finance_manager.repository.TransactionRepository;

public class TransactionService {
	private final CategoryRepository cate;
	private final TransactionRepository tran;

	public TransactionService() {
		this(new JdbcCategoryRepository(), new JdbcTransactionRepository());
	}

	public TransactionService(CategoryRepository categoryRepo, TransactionRepository transactionRepo) {
		this.cate = categoryRepo;
		this.tran = transactionRepo;
	}

	public void addTransaction(UUID account_id, Type type, BigDecimal amount, LocalDate date, String desc,
			String category) {

		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidInputException(ErrorMessages.AMOUNT_NOT_POSITIVE);
		}
		if (!(cate.existsByAccountIdAndName(account_id, category))) {
			throw new InvalidInputException(ErrorMessages.CATEGORY_NOT_FOUND);
		}
		Transaction transaction = new Transaction(account_id, type, amount, date, desc, category);
		tran.save(transaction);

	}

	public List<Transaction> listTransactions(UUID account_id) {
		List<Transaction> list = tran.findAllByAccountId(account_id);
		if (list == null || list.isEmpty()) {
			System.out.println("Es sind keine Buchungen vorhanden.");
		}
		return list;

	}

	public void deleteTransaction(int txId, UUID account_id) {
		int exe = tran.deleteByIdAndAccountId(txId, account_id);
		if (exe == 0)
			throw new NotFoundException(ErrorMessages.TX_NOT_FOUND);

	}

	public Map<String, BigDecimal> report(UUID account_id) {
		Map<String, BigDecimal> dict = new HashMap<>();
		BigDecimal totalIncome = new BigDecimal("0");
		BigDecimal totalExpense = new BigDecimal("0");
		List<Transaction> list = tran.findAllByAccountId(account_id);
		for (Transaction t : list) {
			if (t.getType() == Type.EXPENSE) {
				totalExpense = totalExpense.add(t.getAmount());

			}
			if (t.getType() == Type.INCOME) {
				totalIncome = totalIncome.add(t.getAmount());
			}
		}
		dict.put("totalIncome", totalIncome);
		dict.put("totalExpense", totalExpense);

		return dict;

	}

}
