package io.github.arintasoglu.finance_manager.repository;

import java.util.List;

import io.github.arintasoglu.finance_manager.model.Account;

public interface AccountRepository {

	public Account findByUsername(String username);

	public int createAccount(Account acc);

	public boolean existsByUsername(String username);

	public List<Account> findAllUser(String email);

	public int delete(String username);

}
