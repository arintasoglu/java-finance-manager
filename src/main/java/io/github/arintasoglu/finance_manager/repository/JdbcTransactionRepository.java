package io.github.arintasoglu.finance_manager.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.github.arintasoglu.finance_manager.databaseconnection.DatabaseConnection;
import io.github.arintasoglu.finance_manager.exception.DataAccessException;
import io.github.arintasoglu.finance_manager.model.Transaction;
import io.github.arintasoglu.finance_manager.model.Type;

public class JdbcTransactionRepository implements TransactionRepository {

	@Override
	public void save(Transaction t) {
		int exe;
		try (Connection con = DatabaseConnection.provideConnection()) {
			PreparedStatement pst = con.prepareStatement(
					"insert into transactions (account_id, type, amount, datef, descriptionf, categoryName) values(?,?,?,?,?,?)");
			;
			pst.setString(1, t.getAccount_id().toString());
			pst.setString(2, t.getType().toString());
			pst.setBigDecimal(3, t.getAmount());
			pst.setDate(4, java.sql.Date.valueOf(t.getDate()));
			pst.setString(5, t.getDescription());
			pst.setString(6, t.getCategoryName());

			exe = pst.executeUpdate();
			if (exe == 0)
				throw new DataAccessException("ein fehler beim  datenbank ");

		} catch (SQLException e) {
			throw new DataAccessException(" "); // TODO
		}

	}

	@Override
	public List<Transaction> findAllByAccountId(UUID accountId) {
		List<Transaction> list = new ArrayList<>();
		try (Connection con = DatabaseConnection.provideConnection()) {
			PreparedStatement pst = con.prepareStatement("select * from transactions where  account_id = ?");
			pst.setString(1, accountId.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Transaction t = new Transaction(UUID.fromString(rs.getString("account_id")),
						Type.fromString(rs.getString("type")), rs.getBigDecimal("amount"),
						rs.getDate("datef").toLocalDate(), rs.getString("descriptionf"), rs.getString("categoryName"));
				t.setId(rs.getInt("id"));
				list.add(t);
			}
			return list;

		} catch (SQLException e) {
			throw new DataAccessException(" ");

		}

	}

	@Override
	public int deleteByIdAndAccountId(int txId, UUID accountId) {
		int exe;
		try (Connection con = DatabaseConnection.provideConnection()) {
			PreparedStatement pst = con.prepareStatement("delete from transactions where id = ? and account_id =?");
			pst.setInt(1, txId);
			pst.setString(2, accountId.toString());
			exe = pst.executeUpdate();
			return exe;

		} catch (SQLException e) {
			throw new DataAccessException(" ");

		}

	}

}
