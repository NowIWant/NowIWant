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

public class CarProductModel {

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

	public void addCarPro(int id_prodotto, String[] taglie, String[] quantita) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			for (int i = 0; i < taglie.length; i++) {

				String selectSQL = "SELECT id_prodotto, taglia FROM car_prodotti WHERE id_prodotto = ? AND BINARY taglia = ?";

				conn = ds.getConnection();
				pstmt = conn.prepareStatement(selectSQL);
				pstmt.setInt(1, id_prodotto);
				pstmt.setString(2, taglie[i]);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next() == false) {
					// taglia non esistente ancora per il prodotto
					String insertSQL = "INSERT INTO car_prodotti (id_prodotto,taglia,quantita) VALUES (?,?,?)";

					pstmt.close();
					pstmt = conn.prepareStatement(insertSQL);

					pstmt.setInt(1, id_prodotto);
					pstmt.setString(2, taglie[i]);
					pstmt.setInt(3, Integer.parseInt(quantita[i]));
					pstmt.executeUpdate();

				} else {
					// taglia già esistente per il prodotto
					String updateSQL = "UPDATE car_prodotti SET quantita = ? WHERE id_prodotto = ? AND BINARY taglia = ?";

					pstmt.close();
					pstmt = conn.prepareStatement(updateSQL);

					pstmt.setInt(1, Integer.parseInt(quantita[i]));
					pstmt.setInt(2, id_prodotto);
					pstmt.setString(3, taglie[i]);

					pstmt.executeUpdate();

				}
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

	public Collection<CarProBean> getCarPro(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;

		Collection<CarProBean> caratteristiche = new LinkedList<CarProBean>();

		String selectSQL = "SELECT id_car_prodotto,id_prodotto,taglia,quantita "
				+ " FROM car_prodotti WHERE id_prodotto = ?";
		try {
			conn = ds.getConnection();
			pstm = conn.prepareStatement(selectSQL);
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				CarProBean bean = new CarProBean();

				bean.setId_car_prodotto(rs.getInt("id_car_prodotto"));
				bean.setId_prodotto(rs.getInt("id_prodotto"));
				bean.setTaglia(rs.getString("taglia"));
				// bean.setColore(rs.getString("colore"));
				bean.setQuantita(rs.getInt("quantita"));

				caratteristiche.add(bean);
			}
		} finally {
			try {
				if (pstm != null)
					pstm.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}

		return caratteristiche;
	}

	public Collection<ProductBean> getLastQua() throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;

		Collection<ProductBean> prodotti = new LinkedList<ProductBean>();

		String selectSQL = "SELECT p.id_prodotto,p.nome,c.taglia,c.quantita "
				+ " FROM car_prodotti c INNER JOIN prodotti p ON p.id_prodotto = c.id_prodotto "
				+ "ORDER BY c.quantita ASC";
		try {
			conn = ds.getConnection();
			pstm = conn.prepareStatement(selectSQL);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				ProductBean bean = new ProductBean();

				bean.setId_prodotto(rs.getInt("id_prodotto"));
				bean.setNome(rs.getString("nome"));
				bean.setTaglia(rs.getString("taglia"));
				bean.setQuantita(rs.getInt("quantita"));

				prodotti.add(bean);
			}
		} finally {
			try {
				if (pstm != null)
					pstm.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}

		return prodotti;
	}

}
