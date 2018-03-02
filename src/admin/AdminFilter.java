package admin;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import code.UserBean;

/**
 * Servlet Filter implementation class AdminFilter
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = { "/AddProduct", "/Admin", "/CategoryControl", "/OrderControl",
		"/ProductControl", "/ProductEdit", "/UploadImage", "/UserControl" })
public class AdminFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public AdminFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//filtro per entrare nelle servlet dell'admin controlla se l'utende è loggato e se è admin
		HttpServletRequest req = (HttpServletRequest) request;

		if (req.getSession().getAttribute("utente") == null
				|| ((UserBean) req.getSession().getAttribute("utente")).getAdmin() != 1) {
			req.setAttribute("erroreLogin", "Ci hai provato!");

			RequestDispatcher dispatch = request.getRequestDispatcher("/CatMenuTop");
			dispatch.include(request, response);
			dispatch = request.getRequestDispatcher("/Login");
			dispatch.forward(request, response);
			return;
		}

		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
