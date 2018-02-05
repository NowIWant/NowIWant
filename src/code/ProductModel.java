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

import com.mysql.jdbc.Statement;

public class ProductModel {
	/*
	 * @Resource(name = "jdbc/nowiwant")
	 * 
	 * @Resource(lookup = "java:comp/env/jdbc/nowiwant")
	 * 
	 * private static DataSource ds;
	 */
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

	private static final String TABLE_NAME = " prodotti AS pro ";


	public synchronized Collection<ProductBean> ottieniProdotti() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<ProductBean> products = new LinkedList<ProductBean>();

		String selectSQL = "SELECT pro.id_prodotto,pro.id_categoria,pro.nome,pro.descrizione,pro.prezzo,pro.date_add,cat.nome AS nomecategoria,cat.id_cat_padre , cpad.nome AS nomepadre"
				+ ",(SELECT  immagine FROM immagini imm WHERE imm.id_prodotto = pro.id_prodotto LIMIT 1) AS immagine "
				+ " FROM " + TABLE_NAME + " LEFT JOIN categorie cat ON pro.id_categoria = cat.id_categoria"
				+ " LEFT JOIN categorie cpad ON cat.id_cat_padre = cpad.id_categoria "
				+ "ORDER BY pro.date_add DESC "
				+ "LIMIT 4";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ProductBean bean = new ProductBean();

				bean.setId_prodotto(rs.getInt("id_prodotto"));
				bean.setId_categoria(rs.getInt("id_categoria"));
				bean.setNome(rs.getString("nome"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setPrezzo(rs.getFloat("prezzo"));
				bean.setFirstImm(rs.getString("immagine"));
				if (rs.wasNull()) {
					bean.setFirstImm("noimage.png");
				}
				bean.setNomecategoria(rs.getString("nomecategoria"));
				bean.setId_cat_padre(rs.getInt("id_cat_padre"));
				bean.setNomepadre(rs.getString("nomepadre"));

				products.add(bean);
			}
			System.out.println("INTERROGATO DATABASE PER PRODOTTI");
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}

		return products;
	}

	public synchronized Collection<ProductBean> ottieniProdCat(int id_categoria, String sort) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;

		Collection<ProductBean> products = new LinkedList<ProductBean>();

		String selectSQL = "SELECT pro.id_prodotto,pro.id_categoria,pro.nome,pro.descrizione,pro.prezzo,pro.date_add,cat.nome AS nomecategoria,cat.id_cat_padre , cpad.nome AS nomepadre"
				+ " ,(SELECT  immagine FROM immagini imm WHERE imm.id_prodotto = pro.id_prodotto LIMIT 1) AS immagine "
				+ " FROM " + TABLE_NAME + " LEFT JOIN categorie cat ON pro.id_categoria = cat.id_categoria"
				+ " LEFT JOIN categorie cpad ON cat.id_cat_padre = cpad.id_categoria WHERE pro.id_categoria = "
				+ id_categoria + " OR cat.id_cat_padre = " + id_categoria;

		if (sort != null && !sort.equals("")) {
			selectSQL = selectSQL + " ORDER BY " + sort;
		}
		try {
			conn = ds.getConnection();
			pstm = conn.prepareStatement(selectSQL);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				ProductBean bean = new ProductBean();

				bean.setId_prodotto(rs.getInt("id_prodotto"));
				bean.setId_categoria(rs.getInt("id_categoria"));
				bean.setNome(rs.getString("nome"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setPrezzo(rs.getFloat("prezzo"));
				bean.setFirstImm(rs.getString("immagine"));
				if (rs.wasNull()) {
					bean.setFirstImm("noimage.png");
				}
				bean.setNomecategoria(rs.getString("nomecategoria"));
				bean.setId_cat_padre(rs.getInt("id_cat_padre"));
				bean.setNomepadre(rs.getString("nomepadre"));
				products.add(bean);
			}
			System.out.println("INTERROGATO DATABASE PER PRODOTTI - CATEGORIA");
		} finally {
			try {
				if (pstm != null)
					pstm.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}

		return products;
	}

	public int addProduct(ProductBean prodotto) throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String insertSQL = "INSERT INTO prodotti (nome,descrizione,prezzo,id_categoria,date_add) VALUES (?,?,?,?,NOW())";

			conn = ds.getConnection();
			pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, prodotto.getNome());
			pstmt.setString(2, prodotto.getDescrizione());
			pstmt.setFloat(3, prodotto.getPrezzo());
			pstmt.setInt(4, prodotto.getId_categoria());

			int inserito = pstmt.executeUpdate();

			if (inserito != 0) {
				ResultSet chiavi = pstmt.getGeneratedKeys();
				chiavi.next();
				int chiave = chiavi.getInt(1);

				return chiave;
			} else {
				throw new Exception("Errore Inserimento prodotto");
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

	public void deleteProduct(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String deleteSQL = "DELETE FROM prodotti WHERE id_prodotto = ?";

			conn = ds.getConnection();

			pstmt = conn.prepareStatement(deleteSQL);

			pstmt.setInt(1, id);

			pstmt.executeUpdate();
			pstmt.close();

			deleteSQL = "DELETE FROM car_prodotti WHERE id_prodotto = ?";

			pstmt = conn.prepareStatement(deleteSQL);

			pstmt.setInt(1, id);

			pstmt.executeUpdate();
			pstmt.close();

			deleteSQL = "DELETE FROM immagini WHERE id_prodotto = ?";

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

	public ProductBean infoProduct(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;
		ProductBean bean = new ProductBean();

		String selectSQL = "SELECT pro.id_prodotto,pro.id_categoria,pro.nome,pro.descrizione,pro.prezzo,pro.date_add,cat.nome AS nomecategoria,cat.id_cat_padre , cpad.nome AS nomepadre"
				+ ",(SELECT  immagine FROM immagini imm WHERE imm.id_prodotto = pro.id_prodotto LIMIT 1) AS immagine "
				+ " FROM " + TABLE_NAME + " LEFT JOIN categorie cat ON pro.id_categoria = cat.id_categoria"
				+ " LEFT JOIN categorie cpad ON cat.id_cat_padre = cpad.id_categoria WHERE pro.id_prodotto = ?";
		try {
			conn = ds.getConnection();
			pstm = conn.prepareStatement(selectSQL);
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {

				bean.setId_prodotto(rs.getInt("id_prodotto"));
				bean.setId_categoria(rs.getInt("id_categoria"));
				bean.setNome(rs.getString("nome"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setPrezzo(rs.getFloat("prezzo"));
				// bean.setFirstImm(rs.getString("immagine"));
				// if (rs.wasNull()) {
				// bean.setFirstImm("noimage.png");
				// }
				// bean.setNomecategoria(rs.getString("nomecategoria"));
				// bean.setId_cat_padre(rs.getInt("id_cat_padre"));
				// bean.setNomepadre(rs.getString("nomepadre"));
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

	public void updateProduct(ProductBean prodotto) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String insertSQL = "UPDATE prodotti SET nome = ?, descrizione = ?, prezzo = ?, id_categoria = ? WHERE id_prodotto = ?";

			conn = ds.getConnection();
			pstmt = conn.prepareStatement(insertSQL);

			pstmt.setString(1, prodotto.getNome());
			pstmt.setString(2, prodotto.getDescrizione());
			pstmt.setFloat(3, prodotto.getPrezzo());
			pstmt.setInt(4, prodotto.getId_categoria());
			pstmt.setInt(5, prodotto.getId_prodotto());

			int inserito = pstmt.executeUpdate();

			if (inserito == 0) {
				throw new Exception("Errore aggiornamento prodotto");
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

	public Collection<ProductBean> search(String key) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;

		Collection<ProductBean> products = new LinkedList<ProductBean>();

		String selectSQL = "SELECT pro.id_prodotto,pro.id_categoria,pro.nome,pro.descrizione,pro.prezzo,pro.date_add,"
				+ "cat.nome AS nomecategoria,cat.id_cat_padre , cpad.nome AS nomepadre,"
				+ "(SELECT  immagine FROM immagini imm WHERE imm.id_prodotto = pro.id_prodotto LIMIT 1) AS immagine "
				+ "FROM prodotti pro LEFT JOIN categorie cat ON pro.id_categoria = cat.id_categoria "
				+ "LEFT JOIN categorie cpad ON cat.id_cat_padre = cpad.id_categoria "
				+ "WHERE pro.nome LIKE ? OR pro.descrizione LIKE ?";

		try {
			conn = ds.getConnection();
			pstm = conn.prepareStatement(selectSQL);
			pstm.setString(1, "%" + key + "%");
			pstm.setString(2, "%" + key + "%");
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				ProductBean bean = new ProductBean();

				bean.setId_prodotto(rs.getInt("id_prodotto"));
				bean.setId_categoria(rs.getInt("id_categoria"));
				bean.setNome(rs.getString("nome"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setPrezzo(rs.getFloat("prezzo"));
				bean.setFirstImm(rs.getString("immagine"));
				if (rs.wasNull()) {
					bean.setFirstImm("noimage.png");
				}
				bean.setNomecategoria(rs.getString("nomecategoria"));
				bean.setId_cat_padre(rs.getInt("id_cat_padre"));
				bean.setNomepadre(rs.getString("nomepadre"));
				products.add(bean);
			}
			System.out.println("INTERROGATO DATABASE PER RICERCA PRODOTTI");
		} finally {
			try {
				if (pstm != null)
					pstm.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}

		return products;
	}

	public boolean checkQuaPro(int id_prodotto, int quantita, String taglia) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;

		try {

			String selectSQL = "SELECT quantita "
					+ "FROM car_prodotti WHERE id_prodotto = ? AND BINARY taglia = ?";

			conn = ds.getConnection();

			pstm = conn.prepareStatement(selectSQL);
			pstm.setInt(1, id_prodotto);
			pstm.setString(2, taglia);
			ResultSet rs = pstm.executeQuery();

			while(rs.next()){
				if(rs.getInt("quantita") >= quantita){
					return true;
				}else{
					return false;
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
		return false;
	}

}
