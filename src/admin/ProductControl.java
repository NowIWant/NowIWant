package admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import code.CartModel;
import code.ImageBean;
import code.ImageModel;
import code.ProductModel;

/**
 * Servlet implementation class ProductControl
 */
@WebServlet("/ProductControl")
public class ProductControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static ProductModel model = new ProductModel();
	static ImageModel modelImage = new ImageModel();
	static CartModel modelCart = new CartModel();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductControl() {
		super();
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
				if (action.equals("deleteProduct")) {
					if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
						int id = Integer.parseInt(request.getParameter("id"));
						Collection<ImageBean> immagini = modelImage.getProdImage(id);
						Iterator<?> it = immagini.iterator();
						while (it.hasNext()) {
							ImageBean bean = (ImageBean) it.next();
							File file = new File(request.getServletContext().getRealPath("") + "images\\prodotti\\"
									+ bean.getImmagine());
							System.out.println(file.exists());
							Files.deleteIfExists(file.toPath());
						}
						model.deleteProduct(id);
						modelImage.deleteAllProdImage(id);
						modelCart.deleteProd(id);
						// response.sendRedirect("ProductControl");
						// return;
					}
				}
			}
			request.setAttribute("tablePro", model.ottieniProdotti());
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/prodotti.jsp");
			dispatcher.forward(request, response);
			return;
		} catch (SQLException e) {
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
		doGet(request, response);
	}

}