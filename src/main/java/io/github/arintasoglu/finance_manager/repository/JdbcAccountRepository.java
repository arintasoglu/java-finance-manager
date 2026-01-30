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
import io.github.arintasoglu.finance_manager.model.Account;
import io.github.arintasoglu.finance_manager.model.Role;

public class JdbcAccountRepository implements AccountRepository {

	@Override
	public Account findByUsername(String username) {
		Account acc = null;
		try (Connection con = DatabaseConnection.provideConnection()) {
			PreparedStatement pst = con.prepareStatement("select * from accountf where username = ?");
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				UUID id = UUID.fromString(rs.getString("id"));
				String name = rs.getString("username");
				String email = rs.getString("email");
				String pass = rs.getString("passwordf");
				Role role = Role.fromString(rs.getString("role"));
				acc = new Account(id, name, email, pass, role);

			}
		} catch (SQLException e) {
			throw new DataAccessException("Datenbankfehler. Bitte später erneut versuchen."); 

		}
		return acc;
	}

	@Override
	public int createAccount(Account acc) {
		int exe;
		try (Connection con = DatabaseConnection.provideConnection()) {
			PreparedStatement pst = con.prepareStatement("insert into accountf values(?,?,?,?,?)");
			pst.setString(1, acc.getId().toString());
			pst.setString(2, acc.getUsername());
			pst.setString(3, acc.getEmail());
			pst.setString(4, acc.getPassword());
			pst.setString(5, acc.getRole().toString());

			exe = pst.executeUpdate();
			if (exe == 0)
				throw new DataAccessException("Datenbankfehler. Bitte später erneut versuchen. ");

		} catch (SQLException e) {
			throw new DataAccessException(" "); // TODO
		}

		return exe;
	}

	@Override
	public boolean existsByUsername(String username) {
		try (Connection con = DatabaseConnection.provideConnection()) {
			PreparedStatement pst = con.prepareStatement("select * from accountf where username = ?");
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				return true;

			return false;
		} catch (SQLException e) {
			throw new DataAccessException("Datenbankfehler. Bitte später erneut versuchen.");
		}

	}

	@Override
	public List<Account> findAllUser(String email) {
		ResultSet rs = null;
		List<Account> users = new ArrayList<>();
		Account acc = null;
		try (Connection con = DatabaseConnection.provideConnection()) {
			PreparedStatement pst = con.prepareStatement("select * from accountf where email = ?");
			pst.setString(1, email);
			rs = pst.executeQuery();
			while (rs.next()) {
				UUID id = UUID.fromString(rs.getString("id"));
				String name = rs.getString("username");
				String admin_name = rs.getString("email");
				String pass = rs.getString("passwordf");
				Role role = Role.fromString(rs.getString("role"));
				acc = new Account(id, name, admin_name, pass, role);
				users.add(acc);

			}
			return users;

		} catch (SQLException e) {
			throw new DataAccessException("Datenbankfehler. Bitte später erneut versuchen.");
		}

	}

	@Override
	public int delete(String username) {
		int b;
		try (Connection con = DatabaseConnection.provideConnection()) {
			PreparedStatement pst = con.prepareStatement("select * from accountf where username = ?");
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			String id = "";
			while (rs.next()) {
				id = rs.getString("id");
			}
			PreparedStatement ps = con.prepareStatement("delete from accountf where id = ?");
			ps.setString(1, id);
			b = ps.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException("Datenbankfehler. Bitte später erneut versuchen.");

		}

		return b;

	}
}
