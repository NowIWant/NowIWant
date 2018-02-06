package nowiwant;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Logout() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Collection<?> carr = (Collection<?>) request.getSession().getAttribute("carrello");
		if (carr != null && carr.size() > 0) {

			if (request.getSession().getAttribute("cartAgg") == null
					|| (int) request.getSession().getAttribute("cartAgg") == 0) {
				if (request.getParameter("bypass") == null || !request.getParameter("bypass").equals("1")) {
					request.setAttribute("erroreCarrello",
							"Hai degli elementi non salvati nel carrello! <a href=\"logout?bypass=1\">Esci comunque</a>");

					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Cart");
					dispatcher.forward(request, response);
					return;
				}
			}
		}
		request.getSession().removeAttribute("utente");
		request.getSession().invalidate();
		/*
		 * String page = request.getParameter("page"); if (page == null) {
		 */
		//String page = request.getContextPath() + "/index.jsp";
		// }
		response.sendRedirect("Home");
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
