package io.github.arintasoglu.finance_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.arintasoglu.finance_manager.exception.ErrorMessages;
import io.github.arintasoglu.finance_manager.exception.InvalidInputException;
import io.github.arintasoglu.finance_manager.exception.NotFoundException;
import io.github.arintasoglu.finance_manager.model.Type;
import io.github.arintasoglu.finance_manager.repository.CategoryRepository;
import io.github.arintasoglu.finance_manager.repository.TransactionRepository;
import io.github.arintasoglu.finance_manager.service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionTest {

	@Mock
	TransactionRepository tran;
	@Mock
	CategoryRepository cate;

	private TransactionService transer;

	@BeforeEach
	void setup() {
		transer = new TransactionService(cate, tran);
	}

	@Test
	void addTransaction_negativeAmount_shouldThrow() {
		BigDecimal amount = new BigDecimal("0");
		InvalidInputException ex = assertThrows(InvalidInputException.class, () -> transer
				.addTransaction(UUID.randomUUID(), Type.EXPENSE, amount, LocalDate.parse("2026-01-04"), "", "food"));
		assertEquals(ErrorMessages.AMOUNT_NOT_POSITIVE, ex.getMessage());

	}

	@Test
	void deleteTransaction_notOwner_shouldThrow() {
		UUID accountId = UUID.randomUUID();
		int txId = 1;

		when(tran.deleteByIdAndAccountId(txId, accountId)).thenReturn(0);

		NotFoundException ex = assertThrows(NotFoundException.class, () -> transer.deleteTransaction(txId, accountId));

		assertEquals(ErrorMessages.TX_NOT_FOUND, ex.getMessage());
	}

}
