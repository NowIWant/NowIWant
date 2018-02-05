<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,code.ProductBean,code.CategoryBean,code.CarProBean,code.ImageBean"%>
<%
	int idProd = Integer.parseInt(request.getParameter("ref"));
	CategoryBean infoCat = (CategoryBean) request.getAttribute("infoCat");
	ProductBean prodotto = (ProductBean) request.getAttribute("prodotto");
	Collection<?> immagini = (Collection<?>) request.getAttribute("immagini");
	System.out.println(prodotto);
	if (prodotto == null || prodotto.equals("")) {
		System.out.println("effettuato redirect");
		response.sendRedirect("Prodotto?ref=" + idProd);
		return;
	}
	List<?> CarPro = (List<?>) request.getAttribute("carPro");
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/source/head.html"%>
<title>Now I WANT | e-commerce Abbigliamento</title>
<!--quantity-->
<script>
	function imgclick(image) {
		var immagine = document.getElementById("princimg").src;
		document.getElementById("princimg").src = image.src;
		image.src = immagine;
	}

	function dispchange() {
		var quan = document.getElementById("selecttag").options[document
				.getElementById("selecttag").selectedIndex]
				.getAttribute('data-quant');
		if (quan == null) {
			document.getElementById("qDisp").innerHTML = "Selezionare una taglia";
			document.getElementById("quantita").disabled = true;
			document.getElementById("btnAdd").disabled = true;

		} else if (quan == 0) {
			document.getElementById("qDisp").innerHTML = "Non disponibile";
			document.getElementById("quantita").disabled = true;
			document.getElementById("btnAdd").disabled = true;
		} else {
			document.getElementById("qDisp").innerHTML = quan;
			document.getElementById("quantita").disabled = false;
			document.getElementById("btnAdd").disabled = false;
		}
		document.getElementById("quantita").max = quan;
		modqua();
	}

	function modqua() {
		var qua = parseFloat(document.getElementById("quantita").value);
		var prezzo = parseFloat(document.getElementById("prezzo").innerHTML);
		totale = qua * prezzo;
		if (isNaN(totale) || totale == 0) {
			totale = 0;
			//document.getElementById("btnAdd").disabled = true;
		} else {
			//document.getElementById("btnAdd").disabled = false;
		}
		document.getElementById("totpre").innerHTML = totale.toFixed(2);;
	}
	/*
	function addQ() {
		if (document.getElementById("quantita").value < 1) {
			document.getElementById("value").value = 1;
		} else {
			document.getElementById("value").value = parseInt(document
					.getElementById("value").value) + 1;
		}
	};

	function sottQ() {
		if (document.getElementById("value").value < 1) {
			document.getElementById("value").value = 1;
		} else if (document.getElementById("value").value != 1) {
			document.getElementById("value").value = parseInt(document
					.getElementById("value").value) - 1;
		}
	};
	 */
</script>
<style>
div.imgprinc {
	border: 1px solid #D1CFCF;
	height: 450px;
	width: 100%;
}

div.imgsec {
	cursor: pointer;
	text-align: center;
	border: 1px solid #D1CFCF;
	height: 150px;
	width: 32%;
	display: inline-block;
}
</style>
</head>
<body>
	<div class="contenitore">
		<%@ include file="/source/header.jsp"%>
		<!--banner-->
		<div
			style="background: url(images/Secondaria2.jpg) no-repeat; background-position: 0px; background-size: auto 100%; background-attachment: fixed;"
			class="banner-top">
			<div class="container">
				<h1><%=prodotto.getNome()%></h1>
				<em></em>
				<h2>
					<a href="Home">Home</a>
					<%
						if (infoCat.getNomepadre() != null) {
					%><label>/</label><a
						href="Prodotti?categoria=<%=infoCat.getId_cat_padre()%>"><%=infoCat.getNomepadre()%></a>
					<%
						}
					%>
					<label>/</label><a
						href="Prodotti?categoria=<%=infoCat.getId_categoria()%>"><%=infoCat.getNome()%></a>
				</h2>
			</div>
		</div>

		<%
			String errore = (String) request.getAttribute("errore");

			if (errore != null && !errore.equals("")) {
		%>
		<div class="alert alert-danger" role="alert">
			<strong><%=errore%></strong>
		</div>

		<%
			}
		%>
		<%
			String aggiunto = (String) request.getAttribute("aggiunto");
			if (aggiunto != null) {
		%>
		<div class="alert alert-success" role="alert">
			<strong><%=aggiunto%></strong>
		</div>
		<%
			}
		%>


		<div class="single">
			<div class="container">
				<div class="col-md-9">
					<div class="col-md-5 grid">
						<%
							if (immagini != null && immagini.size() != 0) {
								Iterator<?> it = immagini.iterator();
								int c = 0;
								while (it.hasNext()) {
									ImageBean image = (ImageBean) it.next();
									if (c == 0) {
						%>
						<div class="imgprinc">
							<img id="princimg"
								style="max-width: 100%; max-height: 100%; margin-left: auto; margin-right: auto;"
								src="images/prodotti/<%=image.getImmagine()%>"
								data-imagezoom="true" class="img-responsive">
						</div>

						<%
							} else {
						%>
						<div class="imgsec">
							<img onClick="imgclick(this);"
								style="max-width: 100%; max-height: 100%; margin-left: auto; margin-right: auto;"
								src="images/prodotti/<%=image.getImmagine()%>"
								data-imagezoom="true" class="img-responsive">
						</div>
						<%
							}
									c++;
								}
							} else {
						%>

						<div class="imgprinc">
							<img id="princimg"
								style="max-width: 100%; max-height: 100%; margin-left: auto; margin-right: auto;"
								src="images/prodotti/noimage.png" data-imagezoom="true"
								class="img-responsive">
						</div>
						<%
							}
						%>

					</div>


					<div class="col-md-7 single-top-in">
						<div class="span_2_of_a1 simpleCart_shelfItem">
							<h3><%=prodotto.getNome()%></h3>

							<%
								if (utente != null && utente.getAdmin() == 1) {
							%>
							<p class="in-para">
								<a href="ProductEdit?id=<%=idProd%>">Amministrazione
									prodotto</a>
							</p>
							<%
								} else {
							%>
							<hr>
							<%
								}
							%>

							<div class="price_single">
								<span class="reducedfrom item_price">&euro; <span
									id="prezzo"><%=prodotto.getPrezzo()%></span></span>
								<div class="clearfix"></div>
							</div>
							<h4 class="quick">Descrizione dell'oggetto:</h4>
							<p class="quick_desc"><%=prodotto.getDescrizione()%></p>

							<form action="AddCart" method="post">
								<label>Seleziona la taglia</label> <select id="selecttag"
									name="taglia" onchange="dispchange();" class="form-control"
									required>
									<option></option>
									<%
										if (CarPro != null && CarPro.size() != 0) {
											Iterator<?> it = CarPro.iterator();
											while (it.hasNext()) {
												CarProBean carprobean = (CarProBean) it.next();
									%>
									<option data-quant="<%=carprobean.getQuantita()%>"><%=carprobean.getTaglia()%></option>
									<%
										}
										}
									%>
								</select> <label> Inserisci la quantità</label> <input id="quantita"
									name="quantita" oninput="modqua();" class="form-control"
									type="number" min="1" max="" value="1" disabled> <label>Disponibilità:</label>
								<span id="qDisp">Selezionare una taglia</span> <input
									type="hidden" name="ref" value="<%=idProd%>"> <br>
								<input id="btnAdd" class="btn btn-lg btn-success pull-right"
									type="submit" value="Aggiungi al carrello" disabled>
							</form>

							<label>Totale:</label><span
								style="color: #F67777; font-size: 1.5em;"> &euro; <span
								id="totpre">0</span></span>
							<div class="clearfix"></div>
						</div>

					</div>
				</div>
				<div class="col-md-3 product-bottom">

					<%
						Collection<?> categorie = (Collection<?>) request.getAttribute("catMenuTop");
					%>
					<!--categories-->
					<div class=" rsidebar span_1_of_left">
						<h4 class="cate">Categorie</h4>
						<ul class="menu-drop">

							<%
								if (categorie != null && categorie.size() != 0) {
									Iterator<?> it = categorie.iterator();
									while (it.hasNext()) {
										CategoryBean cat = (CategoryBean) it.next();
										if (cat.getId_cat_padre() == 0) {
							%>
							<li class="item"><a href="Prodotti?categoria=<%=cat.getId_categoria()%>"><%=cat.getNome()%></a> <%
 	Iterator<?> itera = categorie.iterator();
 				int c = 0;
 				while (itera.hasNext()) {
 					CategoryBean figlio = (CategoryBean) itera.next();
 					if (figlio.getId_cat_padre() == cat.getId_categoria()) {
 						c++;
 						if (c == 1) {
 %>
								<ul class="cute">
									<%
										}
									%>
									<li class="subitem"><a
										href="Prodotti?categoria=<%=figlio.getId_categoria()%>"><%=figlio.getNome()%>
									</a></li>
									<%
										}
													}
													if (c != 0) {
									%>
								</ul> <%
 	}
 			}
 %></li>
							<%
								}
								}
							%>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/source/footer.html"%>
</body>
</html>