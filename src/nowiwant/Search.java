package nowiwant;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import code.ProductBean;
import code.ProductModel;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static ProductModel modelProduct = new ProductModel();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Search() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("key") != null && !request.getParameter("key").equals("")) {
			String key = request.getParameter("key");
			Collection<ProductBean> prodotti = new LinkedList<ProductBean>();
			try {
				prodotti = modelProduct.search(key);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("prodotti", prodotti);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/CatMenuTop");
			dispatcher.include(request, response);
			dispatcher = getServletContext().getRequestDispatcher("/search.jsp");
			dispatcher.forward(request, response);
			return;
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
