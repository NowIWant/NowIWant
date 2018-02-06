package nowiwant;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import code.CartModel;
import code.UserBean;
import code.UserModel;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static UserModel model = new UserModel();
	static CartModel modelCart = new CartModel();
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/CatMenuTop");
		dispatcher.include(request, response);
		dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(request.getSession().getAttribute("utente") != null){
			request.setAttribute("erroreLogin", "Ti sei già autenticato! Per entrare come nuovo utente effettua prima il logout.");
			doGet(request, response);
		}else if (request.getParameter("username") != null && !request.getParameter("username").trim().equals("")
				&& request.getParameter("password") != null && !request.getParameter("password").trim().equals("")) {

			String user = request.getParameter("username").trim();
			String pass = request.getParameter("password").trim();
			UserBean utente = null;
			try {
				utente = model.loginUser(user, pass);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (utente != null && !utente.equals("")) {
				try {
					request.getSession().setAttribute("carrello", modelCart.getCart(utente.getId_utente()));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.getSession().setAttribute("cartAgg", 1);
				request.getSession().setAttribute("utente", utente);
				response.sendRedirect("Home");
			} else {
				// RequestDispatcher dispatcher =
				// getServletContext().getRequestDispatcher("/CatMenuTop");
				// dispatcher.include(request, response);
				request.setAttribute("erroreLogin", "Username e/o password non corretti");
				doGet(request, response);
			}
		}else{
			doGet(request, response);
		}
	}
}
