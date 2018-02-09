package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import code.CategoryBean;
import code.CategoryModel;

public class TestAddCat {

	static CategoryModel model = new CategoryModel();
	static CategoryBean cb = new CategoryBean();
	static int idCat;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		model.addCat("bombe", 1);

	}

	@Test
	public void test() throws SQLException {

		idCat = model.getIdCat(1, "bombe");

		cb = model.infoCat(idCat);

		System.out.println(idCat);

		assertEquals(cb.getId_cat_padre(), 1);
		assertEquals(cb.getNome(), "bombe");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		model.deleteCat(idCat);

	}

}
