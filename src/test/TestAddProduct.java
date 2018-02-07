package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import code.CarProductModel;
import code.CategoryModel;
import code.ProductBean;
import code.ProductModel;

public class TestAddProduct {
	
	
	private static CarProductModel cpm;
	private static CategoryModel cm;
	private static ProductBean pb;
	private static ProductModel pm;
	private static String nome;
	private static String descrizione;
	private static float prezzo;
	private static int categoria;
	private static int idProdotto;
	private static ProductBean prodotto;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		pm= new ProductModel();
		cm= new CategoryModel();
		pb= new ProductBean();
		
	}
	

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {
		
		
	}

}
