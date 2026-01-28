package io.github.arintasoglu.finance_manager.repository;

import java.util.List;
import java.util.UUID;

import io.github.arintasoglu.finance_manager.model.Transaction;

public interface TransactionRepository {
	void save(Transaction t);

	List<Transaction> findAllByAccountId(UUID accountId);

	int deleteByIdAndAccountId(int txId, UUID accountId);

}
