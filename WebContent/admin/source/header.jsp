<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,code.UserBean"%>
<%
	UserBean user = (UserBean) request.getSession().getAttribute("utente");
%>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="Admin"><span>Pannello di </span>
				Amministrazione</a>
			<p class="user-menu pull-right"><%=user.getCognome()%>
				<%=user.getNome()%>
				&middot; <a href="Home">Torna al sito</a>
			</p>
		</div>

	</div>
	<!-- /.container-fluid -->
</nav>

<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
	<ul class="nav menu">
		<%
			String uri = request.getRequestURI();
			//String url = request.getRequestURL().toString();
			String pageName = uri.substring(uri.lastIndexOf("/") + 1);
		%>
		<li <%if (pageName.equals("index.jsp")) {%> class="active" <%}%>><a
			href="Admin"> Dashboard</a></li>
		<li <%if (pageName.equals("user.jsp")) {%> class="active" <%}%>><a
			href="UserControl"> Utenti</a></li>
		<li <%if (pageName.equals("categorie.jsp")) {%> class="active" <%}%>><a
			href="CategoryControl">Categorie</a></li>
		<li <%if (pageName.equals("prodotti.jsp")) {%> class="active" <%}%>><a
			href="ProductControl"> Prodotti</a></li>
		<li <%if (pageName.equals("order.jsp")) {%> class="active" <%}%>><a
			href="OrderControl"> Ordini </a></li>
		<li role="presentation" class="divider"></li>
		<li><a href="<%=request.getContextPath()%>/logout"> Logout</a></li>
	</ul>

</div>
<!--/.sidebar-->