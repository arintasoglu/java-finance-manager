package io.github.arintasoglu.finance_manager.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Transaction {
	int id;
	UUID account_id;
	Type type;
	BigDecimal amount;
	LocalDate date;
	String description;
	String categoryName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UUID getAccount_id() {
		return account_id;
	}

	public void setAccount_id(UUID account_id) {
		this.account_id = account_id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Transaction(UUID account_id, Type type, BigDecimal amount, LocalDate date, String description,
			String categoryName) {
		super();

		this.account_id = account_id;
		this.type = type;
		this.amount = amount;
		this.date = date;
		this.description = description;
		this.categoryName = categoryName;
	}

	public Transaction() {
		super();

	}

	@Override
	public String toString() {
		return """
				----------------------------------------
				Buchung
				----------------------------------------
				ID:            %s
				Typ:           %s
				Betrag:        %s
				Datum:         %s
				Kategorie:     %s
				Beschreibung:  %s
				----------------------------------------
				""".formatted(id, type, amount, date, categoryName,
				description != null && !description.isBlank() ? description : "Keine Beschreibung");
	}

}
