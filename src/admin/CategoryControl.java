package admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import code.CategoryModel;

/**
 * Servlet implementation class CategoryControl
 */
@WebServlet("/CategoryControl")
public class CategoryControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static CategoryModel model = new CategoryModel();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CategoryControl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		try {
			if (action != null) {
				if (action.equals("deleteCat")) {
					if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
						int id = Integer.parseInt(request.getParameter("id"));
						try {
							model.deleteCat(id);
						} catch (Exception e) {
							request.setAttribute("errore", e.getMessage());
						}
					}
				} else if (action.equals("addCat")) {
					if (request.getParameter("categoria") != null && !request.getParameter("categoria").equals("")
							&& request.getParameter("padre") != null && !request.getParameter("padre").equals("")) {
						String cat = request.getParameter("categoria");
						int padre = Integer.parseInt(request.getParameter("padre"));
						try {
							model.addCat(cat,padre);
						} catch (Exception e) {
							request.setAttribute("errore", e.getMessage());
						}
					}
				} else if (action.equals("updateCat")) {
					if (request.getParameter("categoria") != null && !request.getParameter("categoria").equals("")
							&& request.getParameter("id") != null && !request.getParameter("id").equals("")) {
						String nome = request.getParameter("categoria");
						int id = Integer.parseInt(request.getParameter("id"));
						try {
							model.updateCat(nome, id);
						} catch (Exception e) {
							request.setAttribute("errore", e.getMessage());
						}
					}
				}
			}
			request.setAttribute("tableCat", model.ottieniCat());
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/categorie.jsp");
			dispatcher.forward(request, response);
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
