<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,code.ProductBean,code.CategoryBean,code.CartBean"%>
<%
	Collection<?> carrello = (Collection<?>) request.getSession().getAttribute("carrello");
	UserBean user = (UserBean) request.getSession().getAttribute("utente");
	int id_utente = user.getId_utente();
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
			String esatto = (String) request.getAttribute("esatto");
			if (esatto != null && !esatto.equals("")) {
		%>
		<div class="alert alert-success" role="alert">
			<strong><%=esatto%></strong>
		</div>

		<%
			}
		%>

		<%
			String errore = (String) request.getAttribute("erroreCarrello");

			if (errore != null && !errore.equals("")) {
		%>
		<div class="alert alert-danger" role="alert">
			<strong><%=errore%></strong>
		</div>

		<%
			}
		%>
		<div style="text-align: center;">
			<hr>
			<%
				if (carrello != null && carrello.size() > 0) {
			%>
			<form method="POST" style="display: inline;">
				<input type="hidden" name="action" value="deleteCart"> <input
					type="hidden" name="utente" value="<%=id_utente%>"> <input
					class="btn btn-danger" type="submit" value="Elimina carrello">
			</form>
			<form method="POST" style="display: inline;">
				<input type="hidden" name="action" value="saveCart"> <input
					type="hidden" name="utente" value="<%=id_utente%>"> <input
					class="btn btn-info" type="submit" value="Salva carrello">
			</form>
			<%
				}
			%>
			<a class="btn btn-warning" href="Cart?action=storico">Vedi
				Storico</a>
		</div>
		<%
			if (carrello != null && carrello.size() > 0) {
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
					<th></th>
				</tr>
			</thead>
			<tbody>
				<%
					float totCarrello = 0;
						Iterator<?> it = carrello.iterator();
						while (it.hasNext()) {
							CartBean bean = (CartBean) it.next();
				%>
				<tr>
					<td>
						<div
							style="text-align: center; border: 1px solid #D1CFCF; height: 100px; width: 100%; display: inline-block;">
							<a href="Prodotto?ref=<%=bean.getId_prodotto()%>"> <img
								style="max-width: 100%; max-height: 100%; margin-left: auto; margin-right: auto;"
								src="images/prodotti/<%=bean.getImmagine()%>"
								data-imagezoom="true" class="img-responsive">
							</a>
						</div>
					</td>
					<td><a href="Prodotto?ref=<%=bean.getId_prodotto()%>"><%=bean.getNome()%></a></td>
					<td>&euro; <%=bean.getPrezzo()%></td>
					<td><%=bean.getQuantita()%></td>
					<td><%=bean.getTaglia()%></td>
					<td>&euro; <%=bean.getTotprezzo()%></td>
					<td><a class="btn btn-danger"
						href="Cart?action=delProd&id=<%=bean.getId_prodotto()%>&taglia=<%=bean.getTaglia()%>&utente=<%=id_utente%>">Elimina</a></td>
				</tr>
				<%
					totCarrello = totCarrello + bean.getTotprezzo();
						}
				%>
			</tbody>
		</table>
		<div style="text-align: center;">
			<h1>
				Totale carrello: &euro;
				<%=totCarrello%></h1>
			<br>
			<form method="POST" style="display: inline;">
				<input type="hidden" name="action" value="acquista"> <input
					type="hidden" name="utente" value="<%=id_utente%>"> <input
					class="btn btn-primary" type="submit" value="Acquista prodotti">
			</form>
		</div>
		<%
			} else {
		%>
		<hr>
		<h1>Non hai prodotti nel carrello, aggiungi almeno un prodotto per acquistare</h1>
		<hr>
		<%
			}
		%>
	</div>
	<%@include file="/source/footer.html"%>
</body>
</html>