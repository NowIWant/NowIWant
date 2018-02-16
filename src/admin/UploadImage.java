package admin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import code.ImageModel;

/**
 * Servlet implementation class UploadImage
 */
@WebServlet(name = "/UploadImage", urlPatterns = { "/UploadImage" }, initParams = {
		@WebInitParam(name = "file-upload", value = "images\\prodotti") })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB maximum size allowed for
										// uploaded file
		maxRequestSize = 1024 * 1024 * 50) // 50 MB overall size of all uploaded
											// files

public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String SAVE_DIR = "";

	static ImageModel model = new ImageModel();

	DateFormat df = new SimpleDateFormat("ddMMyyyHHmmssSSS");

	public void init() {

		SAVE_DIR = getServletConfig().getInitParameter("file-upload");
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadImage() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String savePath = request.getServletContext().getRealPath("") + SAVE_DIR;

		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}

		if (request.getParts() != null && request.getParts().size() > 0) {
			List<String> immagini = new ArrayList<String>();
			for (Part part : request.getParts()) {
				String fileName = extractFileName(part);
				if (fileName != null && !fileName.equals("")) {
					immagini.add(fileName);
					part.write(savePath + File.separator + fileName);

				}
			}

			try {
				Part part = request.getParts().iterator().next();
				int id_prodotto = Integer
						.parseInt(part.getName().substring(part.getName().indexOf("-") + 1, part.getName().length()));
				model.saveImage(id_prodotto, immagini);
				response.sendRedirect("ProductEdit?id=" + id_prodotto);
				return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		} else {
			request.setAttribute("error", "Errore: Bisogna selezionare almeno un file");
			System.out.println("Errore: Bisogna selezionare almeno un file");

		}
		response.sendRedirect("Home");

	}

	private String extractFileName(Part part) {

		String contentDisp = part.getHeader("content-disposition");

		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return df.format(new Date()) + s.substring(s.length() - 5, s.length() - 1);

			}
		}
		return "";
	}

}
