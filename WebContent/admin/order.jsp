
<%
	Collection<?> carrelli = (Collection<?>) request.getAttribute("carrelli");
	if (carrelli == null) {
		response.sendRedirect("OrderControl");
		return;
	}
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,code.CartBean"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/admin/source/head.html"%>
<title>Pannello di Amministrazione | Ordini</title>
</head>
<body>
	<%@ include file="/admin/source/header.jsp"%>
	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="panel panel-default">

			<div class="panel-heading">Lista Ordini</div>
			<div class="panel-body">
				<%
					if (carrelli != null && carrelli.size() != 0) {
				%>
				<table class="tabler">
					<thead>
						<tr>
							<td>UTENTE</td>
							<td>PRODOTTO</td>
							<td>TAGLIA</td>
							<td>QUANTITA</td>
							<td>PREZZO</td>
							<td>TOTALE</td>
						</tr>
					</thead>
					<%
						Iterator<?> it = carrelli.iterator();
							while (it.hasNext()) {
								CartBean bean = (CartBean) it.next();
					%>
					<tr>
						<td><%=bean.getUsername()%></td>
						<td><a href="Prodotto?ref=<%=bean.getId_prodotto()%>"> <%=bean.getNome()%></a></td>
						<td><%=bean.getTaglia()%></td>
						<td><%=bean.getQuantita()%></td>
						<td>&euro; <%=bean.getPrezzo()%></td>
						<td>&euro; <%=bean.getPrezzo() * bean.getQuantita()%></td>
					</tr>
					<%
						}
					%>

				</table>
				<%
					}
				%>

			</div>
		</div>
	</div>
</body>
</html>