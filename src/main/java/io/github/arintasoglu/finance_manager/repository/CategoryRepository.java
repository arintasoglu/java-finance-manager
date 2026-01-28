package io.github.arintasoglu.finance_manager.repository;

import java.util.List;
import java.util.UUID;

import io.github.arintasoglu.finance_manager.model.Category;

public interface CategoryRepository {
	void save(Category c);

	List<Category> findAllByAccountId(UUID accountId);

	void deleteByAccountIdAndName(UUID accountId, String name);

	boolean existsByAccountIdAndName(UUID accountId, String name);
}
