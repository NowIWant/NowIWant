package code;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import Connessione.Connessione;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CartModel {
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

	public Collection<CartBean> getCarrello(Integer[] id_prodotto, Integer[] quantita, String[] taglie)
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;

		Collection<CartBean> carrello = new LinkedList<CartBean>();

		int c = id_prodotto.length;
		try {

			String selectSQL = "SELECT pro.nome,pro.prezzo,(SELECT  immagine FROM immagini imm "
					+ "WHERE imm.id_prodotto = pro.id_prodotto LIMIT 1) AS immagine FROM prodotti pro WHERE pro.id_prodotto = ?";

			conn = ds.getConnection();
			for (int i = 0; i < c; i++) {

				pstm = conn.prepareStatement(selectSQL);
				pstm.setInt(1, id_prodotto[i]);
				ResultSet rs = pstm.executeQuery();

				while (rs.next()) {
					CartBean bean = new CartBean();

					bean.setId_prodotto(id_prodotto[i]);
					bean.setQuantita(quantita[i]);
					bean.setTaglia(taglie[i]);
					bean.setNome(rs.getString("nome"));
					bean.setPrezzo(rs.getFloat("prezzo"));
					bean.setImmagine(rs.getString("immagine"));
					if (rs.wasNull()) {
						bean.setImmagine("noimage.png");
					}
					bean.setTotprezzo(quantita[i] * bean.getPrezzo());

					carrello.add(bean);
				}
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

		return carrello;
	}

	public void saveCart(int idutente, Collection<?> prodotti) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String deleteSQL = "DELETE FROM carrelli WHERE id_utente = ?";
		try {
			conn = Connessione.getConnessione();
			pstmt = conn.prepareStatement(deleteSQL);

			pstmt.setInt(1, idutente);

			pstmt.executeUpdate();
			pstmt.close();

			String insertSQL = "INSERT INTO carrelli (id_utente,id_prodotto,taglia,quantita) VALUES (?,?,?,?)";

			Iterator<?> it = prodotti.iterator();
			while (it.hasNext()) {
				CartBean bean = (CartBean) it.next();

				pstmt = conn.prepareStatement(insertSQL);
				pstmt.setInt(1, idutente);
				pstmt.setInt(2, bean.getId_prodotto());
				pstmt.setString(3, bean.getTaglia());
				pstmt.setInt(4, bean.getQuantita());

				pstmt.executeUpdate();
				pstmt.close();
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

	public Collection<CartBean> getCart(int id_utente) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;

		Collection<CartBean> carrello = new LinkedList<CartBean>();

		try {

			String selectSQL = "SELECT c.id_utente,c.id_prodotto,c.taglia,c.quantita,p.nome,p.prezzo "
					+ "FROM carrelli c INNER JOIN prodotti p ON p.id_prodotto = c.id_prodotto " + "WHERE id_utente = ?";

			conn = ds.getConnection();

			pstm = conn.prepareStatement(selectSQL);
			pstm.setInt(1, id_utente);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				CartBean bean = new CartBean();

				bean.setId_prodotto(rs.getInt("id_prodotto"));
				bean.setQuantita(rs.getInt("quantita"));
				bean.setTaglia(rs.getString("taglia"));
				bean.setNome(rs.getString("nome"));
				bean.setPrezzo(rs.getFloat("prezzo"));
				bean.setTotprezzo(rs.getInt("quantita") * bean.getPrezzo());

				carrello.add(bean);
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

		return carrello;
	}

	public void deleteProd(int id_prod) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String insertSQL = "DELETE FROM carrelli WHERE id_prodotto = ?";
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

	public int checkQuan(int id_user) throws SQLException {

		Connection conn = null;
		PreparedStatement pstm = null;
		int n = 0;

		try {

			String selectSQL = "SELECT c.id_utente,c.id_prodotto,c.taglia,c.quantita,p.nome,p.prezzo "
					+ "FROM carrelli c INNER JOIN prodotti p ON p.id_prodotto = c.id_prodotto "
					+ "INNER JOIN car_prodotti cp ON p.id_prodotto = cp.id_prodotto AND BINARY c.taglia = cp.taglia "
					+ "WHERE id_utente = ? AND cp.quantita >= c.quantita";

			conn = ds.getConnection();

			pstm = conn.prepareStatement(selectSQL);
			pstm.setInt(1, id_user);
			ResultSet rs = pstm.executeQuery();
				
			rs.last();
			n = rs.getRow();

		} finally {
			try {
				if (pstm != null)
					pstm.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
		// TODO Auto-generated method stub
		return n;
	}

	public void saveAcquisti(int utente, Collection<?> prodotti) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;

		String deleteSQL = "DELETE FROM carrelli WHERE id_utente = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(deleteSQL);

			pstmt.setInt(1, utente);

			pstmt.executeUpdate();
			pstmt.close();

			String insertSQL = "INSERT INTO acquisti (id_utente,id_prodotto,taglia,quantita,prezzo,date_add) VALUES (?,?,?,?,?,NOW())";

			String updateSQL = "UPDATE car_prodotti SET quantita = quantita - ? WHERE id_prodotto = ? AND BINARY taglia = ?";

			Iterator<?> it = prodotti.iterator();
			while (it.hasNext()) {
				CartBean bean = (CartBean) it.next();

				pstmt = conn.prepareStatement(insertSQL);
				pstmt.setInt(1, utente);
				pstmt.setInt(2, bean.getId_prodotto());
				pstmt.setString(3, bean.getTaglia());
				pstmt.setInt(4, bean.getQuantita());
				pstmt.setFloat(5, bean.getPrezzo());

				pstmt.executeUpdate();
				pstmt.close();

				pstmt2 = conn.prepareStatement(updateSQL);

				pstmt2.setInt(1, bean.getQuantita());
				pstmt2.setInt(2, bean.getId_prodotto());
				pstmt2.setString(3, bean.getTaglia());

				pstmt2.executeUpdate();
				pstmt2.close();
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} finally {
				try {
					if (pstmt2 != null)
						pstmt2.close();
				} finally {
					if (conn != null)
						conn.close();
				}
			}
		}
	}

	public Collection<CartBean> getStoricoUser(int utente) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;

		Collection<CartBean> carrello = new LinkedList<CartBean>();

		try {

			String selectSQL = "SELECT a.id_prodotto,a.taglia,a.prezzo,a.quantita,p.nome,"
					+ "(SELECT  immagine FROM immagini imm WHERE imm.id_prodotto = a.id_prodotto LIMIT 1) AS immagine "
					+ "FROM acquisti a INNER JOIN prodotti p ON a.id_prodotto = p.id_prodotto "
					+ "WHERE a.id_utente = ?";

			conn = ds.getConnection();

			pstm = conn.prepareStatement(selectSQL);
			pstm.setInt(1, utente);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				CartBean bean = new CartBean();

				bean.setId_prodotto(rs.getInt("id_prodotto"));
				bean.setQuantita(rs.getInt("quantita"));
				bean.setTaglia(rs.getString("taglia"));
				bean.setNome(rs.getString("nome"));
				bean.setPrezzo(rs.getFloat("prezzo"));
				bean.setImmagine(rs.getString("immagine"));
				if (rs.wasNull()) {
					bean.setImmagine("noimage.png");
				}
				bean.setTotprezzo(bean.getQuantita() * bean.getPrezzo());

				carrello.add(bean);
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

		return carrello;
	}

	public StatBean getStatAcq(String filtro) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;

		StatBean bean = new StatBean();

		try {

			String selectSQL = "SELECT count(id_acquisto) AS num, sum(prezzo * quantita) AS tot FROM acquisti";

			conn = ds.getConnection();
			pstm = conn.prepareStatement(selectSQL);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				bean.setNumAcqui(rs.getInt("num"));
				bean.setTotAcqui(rs.getInt("tot"));
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

		return bean;
	}

	public Collection<CartBean> getAllStorico() throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;

		Collection<CartBean> carrello = new LinkedList<CartBean>();

		try {

			String selectSQL = "SELECT a.id_prodotto,a.taglia,a.prezzo,a.quantita,p.nome,a.id_utente,u.username,"
					+ "(SELECT  immagine FROM immagini imm WHERE imm.id_prodotto = a.id_prodotto LIMIT 1) AS immagine "
					+ "FROM acquisti a INNER JOIN prodotti p ON a.id_prodotto = p.id_prodotto "
					+ "INNER JOIN utenti u ON a.id_utente = u.id_utente " + "";

			conn = ds.getConnection();

			pstm = conn.prepareStatement(selectSQL);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				CartBean bean = new CartBean();

				bean.setId_prodotto(rs.getInt("id_prodotto"));
				bean.setId_utente(rs.getInt("id_utente"));
				bean.setUsername(rs.getString("username"));
				bean.setQuantita(rs.getInt("quantita"));
				bean.setTaglia(rs.getString("taglia"));
				bean.setNome(rs.getString("nome"));
				bean.setPrezzo(rs.getFloat("prezzo"));
				bean.setImmagine(rs.getString("immagine"));
				if (rs.wasNull()) {
					bean.setImmagine("noimage.png");
				}
				bean.setTotprezzo(bean.getQuantita() * bean.getPrezzo());

				carrello.add(bean);
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

		return carrello;
	}

	public void deleteCart(int utente) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String deleteSQL = "DELETE FROM carrelli WHERE id_utente = ?";
		try {
			conn = Connessione.getConnessione();
			pstmt = conn.prepareStatement(deleteSQL);

			pstmt.setInt(1, utente);

			pstmt.executeUpdate();
			pstmt.close();

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
