
<%
	Collection<?> tableUtenti = (Collection<?>) request.getAttribute("tableUtenti");
	if (tableUtenti == null) {
		response.sendRedirect("UserControl");
		return;
	}
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,code.UserBean"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/admin/source/head.html"%>
<style>
DIV.table, DIV.table div.th, DIV.table span.td {
	border: 1px solid #ddd;
	text-align: left;
	padding: 8px;
}

DIV.table div.tbody form.tr:nth-child(even) {
	background-color: #f2f2f2;
}

DIV.table {
	display: table;
	margin-top: 20px;
	margin-bottom: 40px;
	border-collapse: collapse;
	border-spacing: 0;
	width: 100%;
}

DIV.thead {
	display: table-header-group;
}

DIV.tbody {
	display: table-row-group;
}

FORM.tr, DIV.tr {
	display: table-row;
}

div.th {
	font-weight: bold;
}

SPAN.td, div.th {
	display: table-cell;
}
</style>
<title>Pannello di Amministrazione | Utenti</title>
</head>
<body>
	<%@ include file="/admin/source/header.jsp"%>
	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="panel panel-default">
			<div class="panel-heading">Lista Utenti</div>
			<div class="panel-body">
				<%
					if (request.getAttribute("errore") != null) {
				%>
				<div class="alert bg-danger" role="alert"><%=request.getAttribute("errore")%></div>
				<%
					}

					if (tableUtenti != null && tableUtenti.size() != 0) {
				%>
				<div class="table">
					<div class="thead">
						<div class="tr">
							<div class="th">ID_UTENTE</div>
							<div class="th">USERNAME</div>
							<div class="th">NOME</div>
							<div class="th">COGNOME</div>
							<div class="th">ADMIN</div>
							<div class="th">AZIONI</div>
						</div>
					</div>
					<div class="tbody">
						<%
							Iterator<?> it = tableUtenti.iterator();
								while (it.hasNext()) {
									UserBean bean = (UserBean) it.next();
						%>
						<form class="tr" method="post" action="UserControl">
							<span class="td"> <%=bean.getId_utente()%></span> <span
								class="td"><input class="form-control" type="text"
								name="user" value="<%=bean.getUsername()%>" required></span> <span
								class="td"><input class="form-control" type="text"
								name="nome" value="<%=bean.getNome()%>" required></span> <span
								class="td"><input class="form-control" type="text"
								name="cognome" value="<%=bean.getCognome()%>" required></span>
							<span class="td"><input class="form-control" type="number"
								min="0" max="1" name="admin" value="<%=bean.getAdmin()%>"
								required></span> <span class="td"><input type="hidden"
								name="id" value="<%=bean.getId_utente()%>"> <input
								type="hidden" name="action" value="updateUser"> <input
								type="submit" value="Salva" class="btn btn-primary">
								&middot; <a class="btn btn-danger"
								href="UserControl?action=deleteUser&id=<%=bean.getId_utente()%>">Elimina</a>
							</span>
						</form>
						<%
							}
						%>
					</div>
				</div>
				<%
					}
				%>
			</div>
		</div>
	</div>
</body>
</html>