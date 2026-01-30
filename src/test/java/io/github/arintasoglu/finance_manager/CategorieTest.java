package io.github.arintasoglu.finance_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import io.github.arintasoglu.finance_manager.exception.CategoryInUseException;
import io.github.arintasoglu.finance_manager.exception.ErrorMessages;
import io.github.arintasoglu.finance_manager.repository.CategoryRepository;
import io.github.arintasoglu.finance_manager.service.CategoryService;

@ExtendWith(MockitoExtension.class)
public class CategorieTest {
	@Mock
	CategoryRepository repo;
	private CategoryService cateser;

	@BeforeEach
	void setup() {
		cateser = new CategoryService(repo);
	}

	@Test
	void addCategory_duplicate_shouldThrow() {
		UUID accountId = UUID.randomUUID();
		String name = "food";
		when(repo.existsByAccountIdAndName(accountId, name)).thenReturn(true);
		CategoryInUseException ex = assertThrows(CategoryInUseException.class,
				() -> cateser.addCategory(accountId, name));

		assertEquals(ErrorMessages.CATEGORY_EXISTS, ex.getMessage());

	}

}
