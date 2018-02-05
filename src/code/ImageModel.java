package code;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ImageModel {
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
	private static final String TABLE_NAME = " immagini AS imm ";

	public String firstProdImage(int id_prodotto) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String immagine = "";

		String selectSQL = "SELECT TOP 1 * FROM " + TABLE_NAME + "WHERE id_prodotto = " + id_prodotto;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(selectSQL);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				immagine = rs.getString("immagine");
			}
			System.out.println("INTERROGATO DATABASE PER PRIMA IMMAGINE PRODOTTO");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}

		return immagine;
	}

	public void saveImage(int id_prodotto, List<String> immagini) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String insertSQL = "INSERT INTO immagini (id_prodotto,immagine) VALUES (?,?)";
		try {
			conn = ds.getConnection();

			String[] imm = immagini.toArray(new String[immagini.size()]);
			int c = imm.length;
			for (int i = 0; i < c; i++) {
				pstmt = conn.prepareStatement(insertSQL);

				pstmt.setInt(1, id_prodotto);
				pstmt.setString(2, imm[i]);

				pstmt.executeUpdate();

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

	public Collection<ImageBean> getProdImage(int id_prodotto) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		Collection<ImageBean> immagini = new LinkedList<ImageBean>();

		String selectSQL = "SELECT id_immagine,immagine FROM immagini WHERE id_prodotto = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(selectSQL);

			pstmt.setInt(1, id_prodotto);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ImageBean bean = new ImageBean();
				bean.setId_immagine(rs.getInt("id_immagine"));
				bean.setId_prodotto(id_prodotto);
				bean.setImmagine(rs.getString("immagine"));
				immagini.add(bean);
			}
			System.out.println("INTERROGATO DATABASE PER IMMAGINI PRODOTTO");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}

		return immagini;
	}

	public void deleteImage(int id_image) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String insertSQL = "DELETE FROM immagini WHERE id_immagine = ?";
		try {
			conn = ds.getConnection();

			pstmt = conn.prepareStatement(insertSQL);

			pstmt.setInt(1, id_image);

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

	public void deleteAllProdImage(int id_prod) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String insertSQL = "DELETE FROM immagini WHERE id_prodotto = ?";
		try {
			conn = ds.getConnection();

			pstmt = conn.prepareStatement(insertSQL);

			pstmt.setInt(1, id_prod);

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
