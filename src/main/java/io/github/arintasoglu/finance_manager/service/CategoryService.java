package io.github.arintasoglu.finance_manager.service;

import java.util.List;
import java.util.UUID;

import io.github.arintasoglu.finance_manager.exception.CategoryInUseException;
import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.exception.NotFoundException;
import io.github.arintasoglu.finance_manager.model.Category;
import io.github.arintasoglu.finance_manager.repository.JdbcCategoryRepository;

public class CategoryService {
	JdbcCategoryRepository cate = new JdbcCategoryRepository();

	public void addCategory(UUID id, String name) {
		if (name == null || name.isBlank()) {
			throw new InvalidInputException("Der Kategoriename darf nicht leer sein.");
		}
		if (cate.existsByAccountIdAndName(id, name)) {
			throw new CategoryInUseException("Eine Kategorie mit diesem Namen existiert bereits.");
		}
		Category c = new Category(id, name);
		cate.save(c);

	}

	public void deleteCategory(UUID id, String name) {
		if (name == null || name.isBlank()) {
			throw new InvalidInputException("Der Kategoriename darf nicht leer sein.");
		}
		if (!(cate.existsByAccountIdAndName(id, name))) {
			throw new NotFoundException("Es existiert keine Kategorie mit dem angegebenen Namen.");
		}
		cate.deleteByAccountIdAndName(id, name);
		System.out.println(
				"Die Kategorie wurde erfolgreich gelöscht.\n Bestehende Buchungen mit dieser Kategorie bleiben weiterhin erhalten.");

	}

	public void listCategories(UUID id) {
		List<Category> list = cate.findAllByAccountId(id);
		if (list == null || list.isEmpty()) {
			System.out.println("Es sind keine Kategorien vorhanden.");

		}
		for (Category c : list) {
			System.out.println(c);
		}

	}

}
