<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,code.ProductBean,code.CategoryBean,code.CartBean"%>
<%
	Collection<?> storico = (Collection<?>) request.getAttribute("storico");
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/source/head.html"%>
<title>Now I WANT | e-commerce Abbigliamento</title>
</head>
<body>
	<div class="contenitore">
		<%@ include file="/source/header.jsp"%>
		<%
			if (storico != null && storico.size() > 0) {
		%>
		<table class="table">

			<thead>
				<tr>
					<th>IMMAGINE</th>
					<th>PRODOTTO</th>
					<th>PREZZO SINGOLO</th>
					<th>QUANTITA</th>
					<th>TAGLIA</th>
					<th>PREZZO TOTALE</th>
				</tr>
			</thead>
			<tbody>
				<%
					Iterator<?> it = storico.iterator();
						while (it.hasNext()) {
							CartBean bean = (CartBean) it.next();
				%>
				<tr>
					<td>
						<div
							style="text-align: center; border: 1px solid #D1CFCF; height: 100px; width: 100%; display: inline-block;">
							<img
								style="max-width: 100%; max-height: 100%; margin-left: auto; margin-right: auto;"
								src="images/prodotti/<%=bean.getImmagine()%>"
								data-imagezoom="true" class="img-responsive">
						</div>
					</td>
					<td><a href="Prodotto?ref=<%=bean.getId_prodotto()%>"><%=bean.getNome()%></a></td>
					<td>&euro; <%=bean.getPrezzo()%></td>
					<td><%=bean.getQuantita()%></td>
					<td><%=bean.getTaglia()%></td>
					<td>&euro; <%=bean.getTotprezzo()%></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<%
			} else {
		%>
		<hr>
		<h1>Non hai ancora acquistato nulla! Dai spendi i soldini :)</h1>
		<hr>
		<%
			}
		%>
	</div>
	<%@include file="/source/footer.html"%>
</body>
</html>