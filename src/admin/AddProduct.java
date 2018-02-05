package admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import code.CarProductModel;
import code.CategoryModel;
import code.ProductBean;
import code.ProductModel;

/**
 * Servlet implementation class addProduct
 */
@WebServlet("/AddProduct")
public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static ProductModel modelPro = new ProductModel();
	static CategoryModel modelCat = new CategoryModel();
	static CarProductModel modelCarPro = new CarProductModel();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddProduct() {
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
				if (action.equals("addProduct")) {
					if (request.getParameter("nome") != null && !request.getParameter("nome").trim().equals("")
							&& request.getParameter("descrizione") != null
							&& !request.getParameter("descrizione").trim().equals("")
							&& request.getParameter("prezzo") != null
							&& !request.getParameter("prezzo").trim().equals("")
							&& request.getParameter("categoria") != null
							&& !request.getParameter("categoria").trim().equals("")) {

						// RequestDispatcher dispatcher =
						// getServletContext().getRequestDispatcher("/ImageControl");
						// dispatcher.include(request, response);

						String nome = request.getParameter("nome").trim();
						String descrizione = request.getParameter("descrizione").trim();
						float prezzo = Float.parseFloat(request.getParameter("prezzo"));
						int categoria = Integer.parseInt(request.getParameter("categoria"));
						// String immagini = request.getParameter("immagini");

						ProductBean prodotto = new ProductBean();
						prodotto.setNome(nome);
						prodotto.setDescrizione(descrizione);
						prodotto.setPrezzo(prezzo);
						prodotto.setId_categoria(categoria);

						int idProdotto;
						try {
							idProdotto = modelPro.addProduct(prodotto);
							String[] taglie = request.getParameterValues("taglia");
							//String[] colori = request.getParameterValues("colore");
							String[] quantita = request.getParameterValues("quantita");
							modelCarPro.addCarPro(idProdotto, taglie, quantita);
							response.sendRedirect("ProductControl");
							return;
						} catch (Exception e) {
							request.setAttribute("errore", "Errore con l'inserimento del prodotto nel database");
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else {
						request.setAttribute("errore", "Compilare tutti i campi obbligatori");
					}
				}
			}

			request.setAttribute("selectCat", modelCat.ottieniCat());

			//request.setAttribute("selectTaglie", modelSize.ottieniTaglie());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/addProduct.jsp");
		dispatcher.forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
