package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import code.UserBean;
import code.UserModel;

public class TestLogin {

	static UserModel model = new UserModel();
	static UserBean ub = new UserBean();
	static String pass;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		model.registra("fra", "facc", "fra", "fra");
		pass = "fra";
		ub = model.loginUser("fra", "fra");

	}

	@Test
	public void test() {

		assertEquals(ub.getUsername(), "fra");
		assertEquals(pass, "fra");

	}

	@AfterClass
	public static void tearDownAfterClass() throws SQLException {

		model.deleteUser(ub.getId_utente());

	}

}
