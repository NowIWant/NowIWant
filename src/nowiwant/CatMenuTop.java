package nowiwant;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import code.CategoryModel;

/**
 * Servlet implementation class CatMenuTop
 */
@WebServlet("/CatMenuTop")
public class CatMenuTop extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static CategoryModel model = new CategoryModel();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CatMenuTop() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	//aggiorna le categorie
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.removeAttribute("catMenuTop");
		// String pag = request.getParameter("pag");
		try {

			request.setAttribute("catMenuTop", model.ottieniCat());
			return;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
