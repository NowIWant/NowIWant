package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import code.UserBean;
import code.UserModel;

public class TestRegistrazione {

	static UserModel model = new UserModel();
	static UserBean ub = new UserBean();
	static String pass;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		ub = model.registra("fra", "fra", "fra", "fra");
		pass = "fra";

	}

	@Test
	public void test() {

		assertEquals(ub.getNome(), "fra");
		assertEquals(ub.getCognome(), "fra");
		assertEquals(ub.getUsername(), "fra");
		assertEquals(pass, "fra");

	}

	@AfterClass
	public static void tearDownAfterClass() throws SQLException {

		model.deleteUser(ub.getId_utente());
	}

}
