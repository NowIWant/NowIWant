package test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import code.CartModel;
import code.ProductBean;
import code.ProductModel;
import code.UserBean;
import code.UserModel;

public class TestAddCart {

	static ProductBean prodotto = new ProductBean();
	static ProductModel modelP = new ProductModel();
	static UserModel userM = new UserModel();
	static UserBean userB = new UserBean();
	static CartModel cart = new CartModel();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		prodotto.setNome("casa");
		prodotto.setDescrizione("di legno");
		prodotto.setId_categoria(1);
		prodotto.setPrezzo(1);

		userB = userM.registra("fracesco", "sbebe", "wewe", "we");

		modelP.addProduct(prodotto);

		/*
		 * ArrayList carrello = new ArrayList();
		 * 
		 * carrello.add(prodotto); Collection<?> carrello2 = (Collection<?>) carrello;
		 * 
		 * cart.saveCart(userB.getId_utente(), carrello2);
		 */

	}

	@Test
	public void test() {

	}

	@AfterClass
	public static void tearDownAfterClass() throws SQLException {

	}

}
