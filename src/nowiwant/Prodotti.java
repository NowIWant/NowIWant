package nowiwant;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import code.CategoryModel;
import code.ProductModel;

/**
 * Servlet implementation class Prodotti
 */
@WebServlet("/Prodotti")
public class Prodotti extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static ProductModel modelProduct = new ProductModel();
	static CategoryModel modelCategory = new CategoryModel();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Prodotti() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("categoria") != null && !request.getParameter("categoria").equals("")) {
			try {
				int cat = Integer.parseInt(request.getParameter("categoria"));
				request.setAttribute("infoCat", modelCategory.infoCat(cat));
				request.setAttribute("catMenuTop", modelCategory.ottieniCat());

				String sort = request.getParameter("sort");
				request.removeAttribute("prodCat");

				request.setAttribute("prodCat", modelProduct.ottieniProdCat(cat, sort));

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/prodotti.jsp");
				dispatcher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
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
