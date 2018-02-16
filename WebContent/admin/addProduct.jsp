
<%
	Collection<?> categorie = (Collection<?>) request.getAttribute("selectCat");
	Collection<?> taglie = (Collection<?>) request.getAttribute("selectTaglie");
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,code.CategoryBean"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/admin/source/head.html"%>
<title>Pannello di Amministrazione | Add Prodotti</title>
<style type="text/css">
.thumb-image {
	float: left;
	width: 100px;
	position: relative;
	padding: 5px;
}
</style>
<script>
	var limit = 4;
	function addInput() {

		var table = document.getElementById("tabellaTaglie");

		if (table.rows.length - 1 == limit) {
			alert("Hai raggiunto il limite di " + limit + " taglie");
		} else {

			// Create an empty <tr> element and add it to the 1st position of the table:
			var row = table.insertRow(table.rows.length);

			// Insert new cells (<td> elements) at the 1st and 2nd position of the "new" <tr> element:
			var cell1 = row.insertCell(0);
			var cell2 = row.insertCell(1);
			//var cell3 = row.insertCell(2);

			// Add some text to the new cells:
			cell1.innerHTML = '<div class="form-group"> <input class="form-control" type="text" name="taglia" required> </div>';
			cell2.innerHTML = '<div class="form-group"> <input class="form-control" type="number" name="quantita" value="0" required> </div>';
		}
	}

	function removeInput() {
		var table = document.getElementById("tabellaTaglie");

		if (table.rows.length == 2) {
			//alert("!");
		} else {
			var rowCount = table.rows.length;

			table.deleteRow(rowCount - 1);
		}
	}
</script>
</head>
<body>
	<%@ include file="/admin/source/header.jsp"%>
	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">

		<div class="panel panel-default">
			<div class="panel-heading">Aggiungi Prodotto</div>
			<div class="panel-body">
				<%
					if (request.getAttribute("errore") != null) {
				%>
				<div class="alert bg-danger" role="alert"><%=request.getAttribute("errore")%></div>
				<%
					}
				%>
				<form action="AddProduct" method="post">
					<div class="form-group">
						<label>Nome</label> <input class="form-control" type="text"
							name="nome" placeholder="Inserisci il nome del prodotto" required>
					</div>
					<div class="form-group">
						<label>Descrizione</label>
						<textarea class="form-control" name="descrizione"
							placeholder="Inserisci la descrizione del prodotto" required></textarea>
					</div>
					<div class="form-group">
						<label>Prezzo in &euro;</label> <input class="form-control"
							type="number" step="any" min="0" name="prezzo"
							placeholder="Inserisci il prezzo del prodotto" value="1" required>
					</div>
					<div class="form-group">
						<label>Categoria</label> <select name="categoria"
							class="form-control" data-placeholder="Seleziona una categoria"
							required>

							<%
								if (categorie != null && !categorie.equals("")) {
									Iterator<?> it = categorie.iterator();
									while (it.hasNext()) {
										CategoryBean bean = (CategoryBean) it.next();
										if (bean.getId_cat_padre() == 0) {
							%>
							<optgroup label="<%=bean.getNome()%>">
								<%
									Iterator<?> itera = categorie.iterator();

												while (itera.hasNext()) {
													CategoryBean figlio = (CategoryBean) itera.next();
													if (figlio.getId_cat_padre() == bean.getId_categoria()) {
								%>
								<option value="<%=figlio.getId_categoria()%>"><%=bean.getNome()%>
									-
									<%=figlio.getNome()%></option>
								<%
									}
												}
											}
								%>
							</optgroup>
							<%
								}
								}
							%>

						</select>
					</div>

					<table class="tabler" id="tabellaTaglie"
						style="margin: auto; width: 50%">
						<thead>
							<tr>
								<th>TAGLIA</th>
								<!-- <th>COLORE</th>  -->
								<th>QUANTIT&Agrave;</th>
							</tr>
						</thead>
						<tbody id="corpoTabTaglie">
							<tr>
								<td><div class="form-group">
										<input class="form-control" type="text" name="taglia" required>
									</div></td>
								<td><div class="form-group">
										<input class="form-control" type="number" name="quantita"
											value="0" required>
									</div></td>
							</tr>
						</tbody>
					</table>
					<div class="pull-right">
						<input class="btn btn-default" type="button"
							value="Aggiungi taglia" onclick="addInput();" required> <input
							class="btn btn-danger" type="button" value="Rimuovi taglia"
							onclick="removeInput();" required>
					</div>
					<br> <input type="hidden" name="action" value="addProduct">
					<input type="submit" value="Inserisci prodotto"
						class="btn btn-primary">
				</form>
				<hr>
				<div class="alert bg-warning" role="alert">Per inserire le
					immagini inserire prima il prodotto!</div>

			</div>
		</div>
	</div>
</body>
</html>