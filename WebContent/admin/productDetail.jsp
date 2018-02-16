
<%
	Collection<?> categorie = (Collection<?>) request.getAttribute("selectCat");
	Collection<?> taglie = (Collection<?>) request.getAttribute("selectTaglie");
	ProductBean prodotto = (ProductBean) request.getAttribute("prodotto");
	Collection<?> carPro = (Collection<?>) request.getAttribute("carPro");
	Collection<?> immagini = (Collection<?>) request.getAttribute("immagini");
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,code.CategoryBean,code.ProductBean,code.CarProBean,code.ImageBean"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/admin/source/head.html"%>
<title>Pannello di Amministrazione | Dettaglio prodotto</title>
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

	function anteprima(finput) {
		var countFiles = finput.files.length;
		var imgPath = finput.value;
		var extn = imgPath.substring(imgPath.lastIndexOf('.') + 1)
				.toLowerCase();
		var image_holder = document.getElementById("image-holder");
		//image_holder.empty();
		image_holder.innerHTML = "";
		if (extn == "gif" || extn == "png" || extn == "jpg" || extn == "jpeg") {
			if (typeof (FileReader) != "undefined") {
				//loop for each file selected for uploaded.
				for (var i = 0; i < countFiles; i++) {
					var reader = new FileReader();
					reader.onload = function(e) {
						//"<img src="+e.target.result+"class=\"thumb-image\" />".appendTo(image_holder);
						image_holder.innerHTML = image_holder.innerHTML
								+ ("<img src=\""+e.target.result+"\"class=\"thumb-image\" />");

					}
					//image_holder.show();
					reader.readAsDataURL(finput.files[i]);
				}
			} else {
				//alert("This browser does not support FileReader.");
			}
		} else {
			alert("Si prega di selezionare solo immagini.");
		}
	}
</script>
</head>
<body>
	<%@ include file="/admin/source/header.jsp"%>
	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">

		<div class="panel panel-default">
			<div class="panel-heading">Modifica Prodotto</div>
			<div class="panel-body">
				<form action="ProductEdit" method="post">
					<div class="form-group">
						<label>Nome</label> <input class="form-control" type="text"
							name="nome" placeholder="Inserisci il nome del prodotto"
							value="<%=prodotto.getNome()%>" required>
					</div>
					<div class="form-group">
						<label>Descrizione</label>
						<textarea class="form-control" name="descrizione"
							placeholder="Inserisci la descrizione del prodotto" required><%=prodotto.getDescrizione()%></textarea>
					</div>
					<div class="form-group">
						<label>Prezzo</label> <input class="form-control" type="number"
							step="any" min=0 name="prezzo"
							placeholder="Inserisci il prezzo del prodotto"
							value="<%=prodotto.getPrezzo()%>" required>
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
														if (figlio.getId_categoria() == prodotto.getId_categoria()) {
								%>
								<option selected value="<%=figlio.getId_categoria()%>"><%=bean.getNome()%>
									-
									<%=figlio.getNome()%></option>
								<%
									} else {
								%>
								<option value="<%=figlio.getId_categoria()%>"><%=bean.getNome()%>
									-
									<%=figlio.getNome()%></option>

								<%
									}
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
							<%
								if (carPro != null && !carPro.equals("")) {
									Iterator<?> it = carPro.iterator();
									while (it.hasNext()) {
										CarProBean bean = (CarProBean) it.next();
							%>
							<tr>
								<td><div class="form-group">
										<input class="form-control" type="text" name="taglia"
											value="<%=bean.getTaglia()%>" required>

									</div></td>
								<td><div class="form-group">
										<input class="form-control" type="number" name="quantita"
											value="<%=bean.getQuantita()%>">
									</div></td>
							</tr>
							<%
								}
								}
							%>
						</tbody>
					</table>
					<div class="pull-right">
						<input class="btn btn-default" type="button"
							value="Aggiungi taglia" onclick="addInput();" required> <input
							class="btn btn-danger" type="button" value="Rimuovi taglia"
							onclick="removeInput();" required>
					</div>

					<br> <input type="hidden" name="id"
						value="<%=request.getParameter("id")%>"> <input
						type="hidden" name="action" value="updateProduct"> <input
						type="submit" value="Aggiorna prodotto" class="btn btn-primary">
				</form>

				<table class="tabler">
					<thead>
						<tr>
							<th>Immagine</th>
							<th>Azioni</th>
						</tr>
					</thead>
					<tbody>
						<%
							if (immagini != null && immagini.size() != 0) {
								Iterator<?> it = immagini.iterator();
								int c = 0;
								while (it.hasNext()) {
									ImageBean image = (ImageBean) it.next();
						%>
						<tr>
							<td>
								<div
									style="text-align: center; border: 1px solid #D1CFCF; height: 150px; width: 32%; display: inline-block;">
									<img
										style="max-width: 100%; max-height: 100%; margin-left: auto; margin-right: auto;"
										src="images/prodotti/<%=image.getImmagine()%>"
										data-imagezoom="true" class="img-responsive">
								</div>
							</td>
							<td><a class="btn btn-danger"
								href="ProductEdit?id=<%=prodotto.getId_prodotto()%>&action=deleteImage&idImage=<%=image.getId_immagine()%>&image=<%=image.getImmagine()%>">Elimina</a></td>
						</tr>
						<%
							}
							}
						%>
					</tbody>
				</table>
				<form action="UploadImage" method="post"
					enctype="multipart/form-data">
					<div class="form-group">
						<label>Aggiungi immagini del prodotto</label>
						<div id="wrapper" style="margin-top: 20px;">
							<input onchange="anteprima(this);" id="fileUpload"
								multiple="multiple" type="file"
								name="immagini-<%=request.getParameter("id")%>" size="50"
								required />
							<div id="image-holder"></div>
						</div>
					</div>
					<input type="submit" value="Inserisci immagini"
						class="btn btn-primary">
				</form>
			</div>
		</div>
	</div>
</body>
</html>