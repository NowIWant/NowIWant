package nowiwant;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import code.CartBean;
import code.CartModel;
import code.ProductModel;
import code.UserBean;

/**
 * Servlet implementation class AddCart
 */
@WebServlet("/AddCart")
public class AddCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static ProductModel modelProduct = new ProductModel();
	static CartModel modelCart = new CartModel();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCart() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * if (request.getSession().getAttribute("utente") != null &&
		 * !request.getSession().getAttribute("utente").equals("")) {
		 */
		try {
			if (modelProduct.checkQuaPro(Integer.parseInt(request.getParameter("ref")),
					Integer.parseInt(request.getParameter("quantita")), request.getParameter("taglia"))) {

				Collection<CartBean> carrello = new ArrayList<CartBean>();

				if (request.getSession().getAttribute("carrello") == null) {
					UserBean utente = (UserBean) request.getSession().getAttribute("utente");

					request.getSession().setAttribute("carrello", modelCart.getCart(utente.getId_utente()));

				}
				if (request.getSession().getAttribute("carrello") != null) {
					Collection<?> carrelloOld = (Collection<?>) request.getSession().getAttribute("carrello");
					Iterator<?> it = carrelloOld.iterator();
					boolean ins = false;
					while (it.hasNext()) {
						CartBean bean = (CartBean) it.next();
						if (bean.getId_prodotto() == Integer.parseInt(request.getParameter("ref"))
								&& bean.getTaglia().equals(request.getParameter("taglia"))) {
							bean.setQuantita(Integer.parseInt(request.getParameter("quantita")));
							request.setAttribute("aggiunto", "Aggiornata quantità del prodotto nel carrello.");
							ins = true;
						}
						carrello.add(bean);
					}
					if (!ins) {
						CartBean bean = new CartBean();
						bean.setId_prodotto(Integer.parseInt(request.getParameter("ref")));
						bean.setQuantita(Integer.parseInt(request.getParameter("quantita")));
						bean.setTaglia(request.getParameter("taglia"));
						carrello.add(bean);
						request.setAttribute("aggiunto", "Prodotto aggiunto correttamente al carrello.");
					}
				} else {
					CartBean bean = new CartBean();
					bean.setId_prodotto(Integer.parseInt(request.getParameter("ref")));
					bean.setQuantita(Integer.parseInt(request.getParameter("quantita")));
					bean.setTaglia(request.getParameter("taglia"));
					carrello.add(bean);
					request.setAttribute("aggiunto", "Prodotto aggiunto correttamente al carrello.");

				}
				request.getSession().setAttribute("carrello", carrello);
				request.getSession().setAttribute("cartAgg", 0);
				// RequestDispatcher dispatcher =
				// getServletContext().getRequestDispatcher("/Prodotto?ref="
				// + ref);
				// dispatcher.forward(request, response);

				// return;
			} else {
				request.setAttribute("errore", "La quantità del prodotto non è disponibile.");
			}
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * } else { request.setAttribute("erroreLogin",
		 * "Per effettuare gli acquisti devi essere registrato.");
		 * 
		 * RequestDispatcher dispatcher =
		 * getServletContext().getRequestDispatcher("/Login");
		 * dispatcher.forward(request, response); return; }
		 */
		response.sendRedirect("Cart");
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
