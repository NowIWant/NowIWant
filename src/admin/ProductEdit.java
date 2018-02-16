package admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import code.CarProductModel;
import code.CategoryModel;
import code.ImageModel;
import code.ProductBean;
import code.ProductModel;

/**
 * Servlet implementation class ProductEdit
 */
@WebServlet("/ProductEdit")
public class ProductEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static ProductModel model = new ProductModel();
	static CategoryModel modelCat = new CategoryModel();
	static CarProductModel modelCarPro = new CarProductModel();
	static ImageModel modelImage = new ImageModel();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductEdit() {
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
				if (action.equals("updateProduct")) {
					if (request.getParameter("nome") != null && !request.getParameter("nome").trim().equals("")
							&& request.getParameter("descrizione") != null
							&& !request.getParameter("descrizione").trim().equals("")
							&& request.getParameter("prezzo") != null
							&& !request.getParameter("prezzo").trim().equals("")
							&& request.getParameter("categoria") != null
							&& !request.getParameter("categoria").trim().equals("")) {

						int id = Integer.parseInt(request.getParameter("id"));
						String nome = request.getParameter("nome").trim();
						String descrizione = request.getParameter("descrizione").trim();
						float prezzo = Float.parseFloat(request.getParameter("prezzo"));
						int categoria = Integer.parseInt(request.getParameter("categoria"));

						ProductBean prodotto = new ProductBean();
						prodotto.setId_prodotto(id);
						prodotto.setNome(nome);
						prodotto.setDescrizione(descrizione);
						prodotto.setPrezzo(prezzo);
						prodotto.setId_categoria(categoria);
						try {
							model.updateProduct(prodotto);
							String[] taglie = request.getParameterValues("taglia");
							String[] quantita = request.getParameterValues("quantita");
							modelCarPro.addCarPro(id, taglie, quantita);

						} catch (Exception e) {
							request.setAttribute("errore", "Errore con l'aggiornamento del prodotto nel database");
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else {
						request.setAttribute("errore", "Compilare tutti i campi obbligatori");
					}
				} else if (action.equals("deleteImage")) {
					int idImage = Integer.parseInt(request.getParameter("idImage"));
					modelImage.deleteImage(idImage);
					File file = new File(request.getServletContext().getRealPath("") + "images\\prodotti\\"
							+ request.getParameter("image"));

					Files.deleteIfExists(file.toPath());

				}
			}
			if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
				int id = Integer.parseInt(request.getParameter("id"));
				request.setAttribute("prodotto", model.infoProduct(id));

				request.setAttribute("selectCat", modelCat.ottieniCat());

				request.setAttribute("carPro", modelCarPro.getCarPro(id));
				request.setAttribute("immagini", modelImage.getProdImage(id));

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/productDetail.jsp");
				dispatcher.forward(request, response);
				return;
			} else {
				response.sendRedirect("ProductControl");
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		return;
	}

}