package code;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import Connessione.Connessione;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.jdbc.Statement;

public class UserModel {

	private static DataSource ds;

	static {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/nowiwant");

		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}

	private static final String TABLE_NAME = " utenti AS user ";

	public UserBean loginUser(String user, String pass) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		UserBean bean = new UserBean();

		String selectSQL = "SELECT id_utente, username, password, nome, cognome, admin FROM " + TABLE_NAME
				+ "WHERE BINARY username = ? AND BINARY password = ?";
		try {
			conn = Connessione.getConnessione();
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, user);
			pstmt.setString(2, pass);
			ResultSet rs = pstmt.executeQuery();
			rs.last();
			int righe = rs.getRow();
			rs.beforeFirst();
			if (righe == 1) {

				rs.beforeFirst();
				while (rs.next()) {
					bean.setId_utente(rs.getInt("id_utente"));
					bean.setUsername(rs.getString("username"));
					// bean.setPassword(rs.getString("password"));
					bean.setNome(rs.getString("nome"));
					bean.setCognome(rs.getString("cognome"));
					bean.setAdmin(rs.getInt("admin"));
				}
				System.out.println("INTERROGATO DATABASE PER LOGIN UTENTE");
				return bean;
			} else {
				return null;
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
	}

	public Collection<UserBean> ottieniUtenti() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		Collection<UserBean> utenti = new LinkedList<UserBean>();

		String selectSQL = "SELECT id_utente, username, password, nome, cognome, admin FROM " + TABLE_NAME;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(selectSQL);

			ResultSet rs = pstmt.executeQuery();

			rs.beforeFirst();
			while (rs.next()) {
				UserBean bean = new UserBean();
				bean.setId_utente(rs.getInt("id_utente"));
				bean.setUsername(rs.getString("username"));
				// bean.setPassword(rs.getString("password"));
				bean.setNome(rs.getString("nome"));
				bean.setCognome(rs.getString("cognome"));
				bean.setAdmin(rs.getInt("admin"));

				utenti.add(bean);
			}
			System.out.println("INTERROGATO DATABASE PER UTENTI");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
		return utenti;
	}

	public UserBean registra(String nome, String cognome, String user, String pass) throws SQLException, Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String selectSQL = "SELECT username FROM " + TABLE_NAME + " WHERE BINARY username = ?";
		try {
			conn = Connessione.getConnessione();
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, user);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next() == false) {

				String insertSQL = "INSERT INTO utenti (nome,cognome,username,password) VALUES (?,?,?,?)";

				pstmt.close();
				pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

				pstmt.setString(1, nome);
				pstmt.setString(2, cognome);
				pstmt.setString(3, user);
				pstmt.setString(4, pass);

				int inserito = pstmt.executeUpdate();

				if (inserito != 0) {
					ResultSet chiavi = pstmt.getGeneratedKeys();
					chiavi.next();
					int chiave = chiavi.getInt(1);

					UserBean utente = new UserBean();

					utente.setId_utente(chiave);
					utente.setNome(nome);
					utente.setCognome(cognome);
					utente.setUsername(user);

					return utente;
				} else {
					throw new Exception("Errore registrazione nuovo utente");
				}

			} else {
				throw new Exception("Username già utilizzato");
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
	}

	public void updateUser(UserBean user) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String updateSQL = "UPDATE utenti SET username = ?, nome = ?, cognome = ?, admin = ? WHERE id_utente = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(updateSQL);

			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getNome());
			pstmt.setString(3, user.getCognome());
			pstmt.setInt(4, user.getAdmin());
			pstmt.setInt(5, user.getId_utente());

			pstmt.executeUpdate();

		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
	}

	public void deleteUser(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String deleteSQL = "DELETE FROM utenti WHERE id_utente = ?";
		try {
			conn = Connessione.getConnessione();

			pstmt = conn.prepareStatement(deleteSQL);

			pstmt.setInt(1, id);

			pstmt.executeUpdate();

		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
	}

}
