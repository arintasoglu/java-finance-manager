package io.github.arintasoglu.finance_manager.service;

import java.util.List;
import java.util.UUID;

import io.github.arintasoglu.finance_manager.exception.CategoryInUseException;
import io.github.arintasoglu.finance_manager.exception.ErrorMessages;
import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.exception.NotFoundException;
import io.github.arintasoglu.finance_manager.model.Category;
import io.github.arintasoglu.finance_manager.repository.CategoryRepository;
import io.github.arintasoglu.finance_manager.repository.JdbcCategoryRepository;

public class CategoryService {
	private final CategoryRepository repo;

	public CategoryService() {
		this(new JdbcCategoryRepository());
	}

	public CategoryService(CategoryRepository repo) {
		this.repo = repo;
	}

	public void addCategory(UUID id, String name) {

		if (name == null || name.isBlank()) {
			throw new InvalidInputException(ErrorMessages.CATEGORY_EMPTY);
		}
		if (repo.existsByAccountIdAndName(id, name)) {
			throw new CategoryInUseException(ErrorMessages.CATEGORY_EXISTS);
		}
		Category c = new Category(id, name);
		repo.save(c);

	}

	public void deleteCategory(UUID id, String name) {
		if (name == null || name.isBlank()) {
			throw new InvalidInputException(ErrorMessages.CATEGORY_EMPTY);
		}
		if (!(repo.existsByAccountIdAndName(id, name))) {
			throw new NotFoundException(ErrorMessages.CATEGORY_NOT_FOUND);
		}
		repo.deleteByAccountIdAndName(id, name);
		System.out.println(
				"Die Kategorie wurde erfolgreich gelöscht.\n Bestehende Buchungen mit dieser Kategorie bleiben weiterhin erhalten.");

	}

	public void listCategories(UUID id) {
		List<Category> list = repo.findAllByAccountId(id);
		if (list == null || list.isEmpty()) {
			System.out.println("Es sind keine Kategorien vorhanden.");

		}
		for (Category c : list) {
			System.out.println(c);
		}

	}

}
