package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import code.ProductBean;
import code.ProductModel;

public class TestAddProduct {

	private static ProductBean prodotto = new ProductBean();
	static ProductModel modelPro = new ProductModel();
	static int idProdotto;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		prodotto.setNome("filippo");
		prodotto.setDescrizione("ciao");
		prodotto.setPrezzo(1);
		prodotto.setId_categoria(1);

		idProdotto = modelPro.addProduct(prodotto);

	}

	@Test
	public void test() throws SQLException {

		prodotto = modelPro.infoProduct(idProdotto);
		assertEquals(prodotto.getNome(), "filippo");
		assertEquals(prodotto.getDescrizione(), "ciao");
		assertEquals((int) prodotto.getPrezzo(), 1);
		assertEquals(prodotto.getId_categoria(), 1);

	}

	@AfterClass
	public static void tearDownAfterClass() throws SQLException {

		modelPro.deleteProduct(idProdotto);

	}

}
