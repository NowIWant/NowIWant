package nowiwant;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import code.UserBean;
import code.UserModel;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static UserModel model = new UserModel();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
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
		// TODO Auto-generated method stub
		boolean controlUsername, controlName, controlSurname;
		if (request.getSession().getAttribute("utente") != null) {
			request.setAttribute("erroreLogin",
					"Ti sei già autenticato! Per registrarti come nuovo utente effettua prima il logout.");
			doGet(request, response);
		} else {

			String nome = request.getParameter("nome").trim();
			String cognome = request.getParameter("cognome").trim();
			String user = request.getParameter("user").trim();
			String pass = request.getParameter("pass").trim();

			controlUsername = controlloStringa(user);
			controlName = controlloStringa(nome);
			controlSurname = controlloStringa(cognome);

			if (nome != null && !nome.equals("") && cognome != null && !cognome.equals("") && user != null
					&& controlSurname != false && controlName != false && controlUsername != false && !user.equals("")
					&& pass != null && !pass.equals("")) {
				UserBean utente;
				try {
					utente = model.registra(nome, cognome, user, pass);
					request.getSession().setAttribute("utente", utente);
					response.sendRedirect("Home");
				} catch (Exception e) {
					request.setAttribute("erroreLogin", e.getMessage());
					doGet(request, response);
				}
			} else {
				request.setAttribute("erroreLogin", "Parametri di registrazione non inseriti correttamente");
				doGet(request, response);
			}
		}

	}

	public boolean controlloStringa(String username) {

		String array[] = username.split("");
		boolean b = true;
		for (int i = 0; i < array.length; i++) {

			if (array[i].equals("$") || array[i].equals("&") || array[i].equals("%") || array[i].equals("£")
					|| array[i].equals("(") || array[i].equals(")") || array[i].equals("=") || array[i].equals("?")
					|| array[i].equals("^") || array[i].equals("#") || array[i].equals("§") || array[i].equals("°"))

				b = false;

		}

		return b;
	}

}
