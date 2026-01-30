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
import io.github.arintasoglu.finance_manager.model.Category;

public class JdbcCategoryRepository implements CategoryRepository {

	@Override
	public void save(Category c) {
		try (Connection con = DatabaseConnection.provideConnection()) {
			PreparedStatement pst = con.prepareStatement("insert into categories values(?,?)");
			pst.setString(1, c.getAccount_id().toString());
			pst.setString(2, c.getName());
			int exe = pst.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException("Fehler beim Speichern der Kategorie in der Datenbank.");
		}

	}

	@Override
	public List<Category> findAllByAccountId(UUID accountId) {
		List<Category> list = new ArrayList<>();
		try (Connection con = DatabaseConnection.provideConnection()) {
			PreparedStatement pst = con.prepareStatement("select * from categories where account_id =?");
			pst.setString(1, accountId.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Category c = new Category(UUID.fromString(rs.getString("account_id")), rs.getString("name")

				);
				list.add(c);
			}
			return list;

		} catch (SQLException e) {
			throw new DataAccessException("Fehler beim Abrufen der Kategorien aus der Datenbank.");

		}

	}

	@Override
	public void deleteByAccountIdAndName(UUID accountId, String name) {
		try (Connection con = DatabaseConnection.provideConnection()) {
			PreparedStatement pst = con.prepareStatement("delete from categories where account_id = ? and name =?");
			pst.setString(1, accountId.toString());
			pst.setString(2, name);
			int exe = pst.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException("Fehler beim Löschen der Kategorie aus der Datenbank.");

		}

	}

	@Override
	public boolean existsByAccountIdAndName(UUID account_id, String name) {
		try (Connection con = DatabaseConnection.provideConnection()) {
			PreparedStatement pst = con
					.prepareStatement("select exists(select * from categories where account_id = ? and name =?)");
			pst.setString(1, account_id.toString());
			pst.setString(2, name);
			ResultSet rs = pst.executeQuery();
			rs.next();
			boolean exists = rs.getInt(1) == 1;
			return exists;
		} catch (SQLException e) {
			throw new DataAccessException("Fehler beim Prüfen der Kategorie-Existenz in der Datenbank.");

		}

	}

}
