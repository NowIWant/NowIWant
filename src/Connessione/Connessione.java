package Connessione;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Connessione {

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

	public static Connection getConnessione() throws SQLException {
		try {
			return ds.getConnection();
		} catch (NullPointerException e) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				return DriverManager
						.getConnection("jdbc:mysql://localhost/nowiwant?" + "user=root&password=root&useSSL=false");
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}