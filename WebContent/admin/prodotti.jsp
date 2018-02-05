
<%
	Collection<?> tablePro = (Collection<?>) request.getAttribute("tablePro");
	if (tablePro == null) {
		response.sendRedirect("ProductControl");
		return;
	}
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,code.ProductBean"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/admin/source/head.html"%>
<title>Pannello di Amministrazione | Prodotti</title>
</head>
<body>
	<%@ include file="/admin/source/header.jsp"%>
	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="panel panel-default">
			<div class="panel-heading">Lista Prodotti</div>

			<div class="panel-body">
				<a href="AddProduct" class="btn btn-primary pull-right">Inserisci
					prodotto</a>
				<%
					if (tablePro != null && tablePro.size() != 0) {
				%>
				<table class="tabler">
					<thead>
						<tr>
							<th>ID_PRODOTTO</th>
							<th>ID_CATEGORIA</th>
							<th>CATEGORIA</th>
							<th>NOME</th>
							<th>PREZZO</th>
							<th>AZIONI</th>
						</tr>
					</thead>
					<%
						Iterator<?> it = tablePro.iterator();
							while (it.hasNext()) {
								ProductBean bean = (ProductBean) it.next();
					%>
					<tr>
						<td><%=bean.getId_prodotto()%></td>
						<td><%=bean.getId_categoria()%></td>
						<td><%=bean.getNomepadre() + "/" + bean.getNomecategoria()%></td>
						<td><%=bean.getNome()%></td>
						<td><%=bean.getPrezzo()%></td>
						<td><a class="btn btn-danger"
							href="ProductControl?action=deleteProduct&id=<%=bean.getId_prodotto()%>">Elimina</a>
							- <a class="btn btn-primary"
							href="ProductEdit?id=<%=bean.getId_prodotto()%>">Modifica</a></td>
					</tr>
					<%
						}
					%>

				</table>
				<%
					} else {
				%>
				NON CI SONO PRODOTTI, AGGIUNGILI!
				<%
					}
				%>
			</div>
		</div>
	</div>
</body>
</html>