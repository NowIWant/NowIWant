package code;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CategoryModel {

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
	private static final String TABLE_NAME = " categorie AS cat ";

	public synchronized Collection<CategoryBean> ottieniCat() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		Collection<CategoryBean> categorie = new LinkedList<CategoryBean>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(selectSQL);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				CategoryBean bean = new CategoryBean();

				bean.setId_categoria(rs.getInt("id_categoria"));
				bean.setId_cat_padre(rs.getInt("id_cat_padre"));
				bean.setNome(rs.getString("nome"));
				categorie.add(bean);
			}
			System.out.println("INTERROGATO DATABASE PER CATEGORIE");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}

		return categorie;
	}

	public CategoryBean infoCat(int id_categoria) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		CategoryBean bean = new CategoryBean();

		String selectSQL = "SELECT cat.id_categoria, cat.id_cat_padre, cat.nome, cpad.nome AS nomepadre FROM "
				+ TABLE_NAME
				+ " LEFT JOIN categorie cpad ON cat.id_cat_padre = cpad.id_categoria WHERE CAT.id_categoria = "
				+ id_categoria;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(selectSQL);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean.setId_categoria(rs.getInt("id_categoria"));
				bean.setId_cat_padre(rs.getInt("id_cat_padre"));
				bean.setNome(rs.getString("nome"));
				bean.setNomepadre(rs.getString("nomepadre"));
			}
			System.out.println("INTERROGATO DATABASE PER INFO CATEGORIA " + id_categoria);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}

		return bean;
	}

	public void deleteCat(int id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String selectSQL = "SELECT id_categoria, id_cat_padre FROM categorie WHERE id_cat_padre = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next() == false) {

				selectSQL = "SELECT id_prodotto, id_categoria FROM prodotti WHERE id_categoria = ?";
				pstmt.close();
				pstmt = conn.prepareStatement(selectSQL);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();

				if (rs.next() == false) {
					String deleteSQL = "DELETE FROM categorie WHERE id_categoria = ?";

					pstmt.close();
					pstmt = conn.prepareStatement(deleteSQL);

					pstmt.setInt(1, id);

					pstmt.executeUpdate();

				} else {
					throw new Exception("La categoria contiene dei prodotti");
				}

			} else {
				throw new Exception("La categoria contiene delle sottocategorie");
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

	public void addCat(String nome, int padre) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String insertSQL = "INSERT INTO categorie (nome, id_cat_padre) VALUES (?,?)";
		try {

			conn = ds.getConnection();
			pstmt = conn.prepareStatement(insertSQL);

			pstmt.setString(1, nome);
			pstmt.setInt(2, padre);

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

	public void updateCat(String nome, int id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String updateSQL = "UPDATE categorie SET nome = ? WHERE id_categoria = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(updateSQL);

			pstmt.setString(1, nome);
			pstmt.setInt(2, id);

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
