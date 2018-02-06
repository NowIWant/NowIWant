package admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import code.UserBean;
import code.UserModel;

/**
 * Servlet implementation class UserControl
 */
@WebServlet("/UserControl")
public class UserControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static UserModel model = new UserModel();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserControl() {
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
				if (action.equals("updateUser")) {
					if (request.getParameter("id") != null && !request.getParameter("id").equals("")
							&& request.getParameter("user") != null && !request.getParameter("user").equals("")
							&& request.getParameter("nome") != null && !request.getParameter("nome").equals("")
							&& request.getParameter("cognome") != null && !request.getParameter("cognome").equals("")
							&& request.getParameter("admin") != null && !request.getParameter("admin").equals("")) {
						UserBean user = new UserBean();
						user.setId_utente(Integer.parseInt(request.getParameter("id")));
						user.setUsername(request.getParameter("user"));
						user.setNome(request.getParameter("nome"));
						user.setCognome(request.getParameter("cognome"));
						user.setAdmin(Integer.parseInt(request.getParameter("admin")));
						model.updateUser(user);

					}
				} else if (action.equals("deleteUser")) {
					if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
						int id = Integer.parseInt(request.getParameter("id"));
						model.deleteUser(id);

					}
				}
			}
			request.setAttribute("tableUtenti", model.ottieniUtenti());
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/user.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			request.setAttribute("errore", e.getMessage());
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/user.jsp");
			dispatcher.forward(request, response);
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
