package io.github.arintasoglu.finance_manager.repository;

import io.github.arintasoglu.finance_manager.exception.DataAccessException;
import io.github.arintasoglu.finance_manager.model.Account;

public interface AccountRepository {

	public Account findByUsername(String username);

	public int createAccount(Account acc);
	
	public boolean existsByUsername(String username);

}
