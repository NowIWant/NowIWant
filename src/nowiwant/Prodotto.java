package nowiwant;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import code.CarProductModel;
import code.CartModel;
import code.CategoryModel;
import code.ImageModel;
import code.ProductBean;
import code.ProductModel;

/**
 * Servlet implementation class Prodotto
 */
@WebServlet("/Prodotto")
public class Prodotto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static ProductModel modelProduct = new ProductModel();
	static CategoryModel modelCategory = new CategoryModel();
	static CarProductModel modelCarPro = new CarProductModel();
	static CartModel modelCart = new CartModel();
	static ImageModel modelImage = new ImageModel();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Prodotto() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	//carica le info del prodotto selezionato
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("ref") != null && !request.getParameter("ref").equals("")) {
			try {
				request.setAttribute("catMenuTop", modelCategory.ottieniCat());

				int idPro = Integer.parseInt(request.getParameter("ref"));
				ProductBean prodotto = modelProduct.infoProduct(idPro);
				request.setAttribute("prodotto", prodotto);
				request.setAttribute("immagini", modelImage.getProdImage(idPro));
				request.setAttribute("infoCat", modelCategory.infoCat(prodotto.getId_categoria()));
				request.setAttribute("carPro", modelCarPro.getCarPro(idPro));

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/prodotto.jsp");
				dispatcher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			response.sendRedirect("Home");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
