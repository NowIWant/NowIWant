<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,code.CategoryBean,code.UserBean"%>
<%
	Collection<?> catMenuTop = (Collection<?>) request.getAttribute("catMenuTop");
	UserBean utente = (UserBean) request.getSession().getAttribute("utente");
%>
<div class="header">
	<div class="row" style="margin-right: 0px">
		<div class="col-sm-6" style="padding-left: 25px">
			<a href="Home"><img src="images/logonero.png" width="125px"
				align="left" alt=""></a>
		</div>
		<div class="col-sm-6" style="padding: 0px">
			<ul id="principale">
				<li><a href="Cart"><i class="fa fa-shopping-cart"></i></a> <%
 	if (utente == null) {
 %>
				<li><a href="Login">Accedi/Registrati</a></li>
				<%
					} else {
				%>
				<li><a href="logout">Logout</a>
				<li><a>Benvenuto <%=utente.getCognome()%> <%=utente.getNome()%>
				</a></li>
				<%
					String uri = request.getRequestURI();
						//String url = request.getRequestURL().toString();
						String pageName = uri.substring(uri.lastIndexOf("/") + 1);
						if (request.getQueryString() != null) {
							pageName = pageName + "?" + request.getQueryString();
						}
					}
				%>
			</ul>
		</div>
	</div>
	<div class="row" style="background-color: black; margin-right: 0px">
		<div class="col-cat" style="padding-left: 10px">
			<ul class="nav navbar-nav nav_1">
				<!-- <li><a class="color" href="index.jsp">Home</a></li> -->
				<%
					if (catMenuTop != null && catMenuTop.size() != 0) {
						Iterator<?> it = catMenuTop.iterator();
						while (it.hasNext()) {
							CategoryBean bean = (CategoryBean) it.next();
							if (bean.getId_cat_padre() == 0) {
				%>




				<li class="dropdown active"><a class="color1" href="#"
					class="dropdown-toggle" data-toggle="dropdown"> <%=bean.getNome()%>
						<span class="caret"></span></a> <%
 	Iterator<?> itera = catMenuTop.iterator();
 				int c = 0;
 				while (itera.hasNext()) {
 					CategoryBean figlio = (CategoryBean) itera.next();
 					if (figlio.getId_cat_padre() == bean.getId_categoria()) {
 						c++;
 						if (c == 1) {
 %>
					<div class="dropdown-menu">
						<div class="menu-top">
							<div class="col1">
								<div class="h_nav">
									<h4>
										<a href="Prodotti?categoria=<%=bean.getId_categoria()%>"><%=bean.getNome()%></a>
									</h4>
									<ul>
										<%
											}
										%>
										<li><a
											href="Prodotti?categoria=<%=figlio.getId_categoria()%>"><%=figlio.getNome()%></a></li>

										<%
											}
														}
														if (c != 0) {
										%>
									</ul>
								</div>
							</div>

						</div>
					</div> <%
 	}
 %></li>
				<%
					}
						}
					}
					if (utente != null && utente.getAdmin() == 1) {
				%>
				<li><a class="color" href="Admin">Amministrazione</a></li>
				<%
					}
				%>
			</ul>
		</div>

		<div class="col-cerca">
			<form action="Search" method="get" style="padding-top: 5px">
				<div class="input-group">
					<input style="height: 25px" name="key" type="text"
						class="form-control input-md" placeholder="Cerca..." required>
					<span class="input-group-btn">
						<button style="height: 25px;" class="btn btn-default btn-md">
							<i style="color: black; vertical-align: top;"
								class="fa fa-search fa-lg"></i>
						</button>
					</span>

				</div>
			</form>
		</div>
	</div>
</div>