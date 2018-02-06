package nowiwant;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import code.CartBean;
import code.CartModel;
import code.UserBean;

/**
 * Servlet implementation class Cart
 */
@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static CartModel model = new CartModel();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Cart() {
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
		/*
		 * if (request.getSession().getAttribute("utente") != null &&
		 * !request.getSession().getAttribute("utente").equals("")) {
		 */
		UserBean utente = (UserBean) request.getSession().getAttribute("utente");
		String action = request.getParameter("action");
		try {
			if (action != null) {
				if (request.getParameter("utente") != null && Integer.parseInt(request.getParameter("utente")) == utente.getId_utente()
						) {
					if (action.equals("saveCart")) {
						Collection<?> carrello = (Collection<?>) request.getSession().getAttribute("carrello");
						if (carrelloPieno(carrello)) {

							model.saveCart(utente.getId_utente(), carrello);
							request.getSession().removeAttribute("carrello");
							try {
								request.getSession().setAttribute("carrello", model.getCart(utente.getId_utente()));
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							request.removeAttribute("erroreCarrello");
							request.setAttribute("esatto", "Carrello salvato correttamente!");
							request.getSession().setAttribute("cartAgg", 1);
							
						} else {

							request.setAttribute("erroreCarrello", "Carrello vuoto!");
						}

					} else if (action.equals("acquista")) {
						Collection<?> carrello = (Collection<?>) request.getSession().getAttribute("carrello");
						if (carrelloPieno(carrello)) {
							model.saveCart(utente.getId_utente(), carrello);

							try {
								if (carrello.size() == model.checkQuan(utente.getId_utente())) {
									model.saveAcquisti(utente.getId_utente(), carrello);
									request.getSession().removeAttribute("carrello");
									request.removeAttribute("erroreCarrello");
									request.setAttribute("esatto", "Prodotti acquistati correttamente!");
								} else {
									request.setAttribute("erroreCarrello",
											"Dei prodotti non sono attualmente disponibili!");
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							request.setAttribute("erroreCarrello", "Carrello vuoto!");
						}

					} else if (action.equals("deleteCart")) {
						Collection<?> carrello = (Collection<?>) request.getSession().getAttribute("carrello");

						if (carrelloPieno(carrello)) {
							request.getSession().removeAttribute("carrello");
							model.deleteCart(utente.getId_utente());
							request.getSession().setAttribute("cartAgg", 1);
						} else {
							request.setAttribute("erroreCarrello", "Carrello vuoto!");
						}

					} else if (action.equals("delProd")) {
						Collection<?> carrelloOld = (Collection<?>) request.getSession().getAttribute("carrello");
						if (carrelloPieno(carrelloOld)) {
							Collection<CartBean> carrello = new ArrayList<CartBean>();

							int id = Integer.parseInt(request.getParameter("id"));
							String taglia = request.getParameter("taglia");
							Iterator<?> it = carrelloOld.iterator();
							while (it.hasNext()) {
								CartBean bean = (CartBean) it.next();
								if (id != bean.getId_prodotto() || !taglia.equals(bean.getTaglia())) {
									carrello.add(bean);
								}
							}
							request.getSession().setAttribute("carrello", carrello);
							request.getSession().setAttribute("cartAgg", 0);
							response.sendRedirect("Cart");
							return;
						} else {
							request.setAttribute("erroreCarrello", "Carrello vuoto!");
						}
					}
				} else if (action.equals("storico")) {
					request.setAttribute("storico", model.getStoricoUser(utente.getId_utente()));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/CatMenuTop");
					dispatcher.include(request, response);
					dispatcher = getServletContext().getRequestDispatcher("/storico.jsp");
					dispatcher.forward(request, response);
					return;
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collection<?> carrello = (Collection<?>) request.getSession().getAttribute("carrello");
		if (carrello != null) {
			List<Integer> id_prodotto = new ArrayList<Integer>();
			List<Integer> quantita = new ArrayList<Integer>();
			List<String> taglie = new ArrayList<String>();

			Iterator<?> it = carrello.iterator();
			while (it.hasNext()) {

				CartBean bean = (CartBean) it.next();

				id_prodotto.add(bean.getId_prodotto());
				quantita.add(bean.getQuantita());
				taglie.add(bean.getTaglia());
			}
			Integer[] idPro = id_prodotto.toArray(new Integer[id_prodotto.size()]);
			Integer[] Qua = quantita.toArray(new Integer[quantita.size()]);
			String[] tag = taglie.toArray(new String[taglie.size()]);
			try {
				request.getSession().setAttribute("carrello", model.getCarrello(idPro, Qua, tag));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				request.getSession().setAttribute("carrello", model.getCart(utente.getId_utente()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/CatMenuTop");
		dispatcher.include(request, response);
		dispatcher = getServletContext().getRequestDispatcher("/carrello.jsp");
		dispatcher.forward(request, response);
		return;
		/*
		 * } else { request.setAttribute("erroreLogin",
		 * "Per effettuare gli acquisti devi essere registrato.");
		 * 
		 * RequestDispatcher dispatcher =
		 * getServletContext().getRequestDispatcher("/Login");
		 * dispatcher.forward(request, response); return; }
		 */
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

	private boolean carrelloPieno(Collection<?> carrello) {
		if (carrello != null && carrello.size() > 0) {
			return true;
		} else {
			return false;

		}
	}

}
