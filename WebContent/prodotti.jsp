<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,code.ProductBean,code.CategoryBean"%>
<%
	int categoria = Integer.parseInt(request.getParameter("categoria"));
	String sort = request.getParameter("sort");
	if (sort == null) {
		sort = "";
	}
	Collection<?> prodotti = (Collection<?>) request.getAttribute("prodCat");
	CategoryBean infoCat = (CategoryBean) request.getAttribute("infoCat");
	if (prodotti == null) {
		response.sendRedirect("Prodotti?categoria=" + categoria);
	}
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/source/head.html"%>
<title>Now I WANT | e-commerce Abbigliamento</title>
<script>
	function ordina() {
		var e = document.getElementById("ordine");
		var ordine = e.options[e.selectedIndex].value;
		var categoria = get("categoria");
		location.href = "Prodotti?categoria=" + categoria + "&sort=" + ordine;
	}

	function get(name) {
		if (name = (new RegExp('[?&]' + encodeURIComponent(name) + '=([^&]*)'))
				.exec(location.search))
			return decodeURIComponent(name[1]);
	}
</script>
</head>
<body>
	<div class="contenitore">
		<%@ include file="/source/header.jsp"%>
		<div
			style="background: url(images/Secondaria2.jpg) no-repeat; background-position: 0px; background-size: auto 100%; background-attachment: fixed;"
			class="banner-top">
			<div class="container">
				<h1><%=infoCat.getNome()%></h1>
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
					<label>/</label><%=infoCat.getNome()%>
				</h2>
			</div>
		</div>
		<div class="product">
			<div class="container">
				<div class="row">
					<div class="col-md-3">
						<label> ORDINA PER </label> <br> <select
							style="width: 50%; display: inline;" class="form-control"
							id="ordine">

							<option></option>

							<option value="prezzo ASC"
								<%if (sort.equals("prezzo ASC"))
				out.print("selected");%>>Prezzo
								crescente</option>

							<option value="prezzo DESC"
								<%if (sort.equals("prezzo DESC"))
				out.print("selected");%>>Prezzo
								decrescente</option>
							<option value="date_add DESC"
								<%if (sort.equals("date_add DESC"))
				out.print("selected");%>>Ultimi
								Arrivi</option>
						</select>
						<button style="width: 40%; display: inline;" type="button"
							class="btn btn-default" onClick="ordina();">ORDINA</button>

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
								<li class="item"><a
									href="Prodotti?categoria=<%=cat.getId_categoria()%>"><%=cat.getNome()%></a>
									<%
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

					<div class="col-sm-9">
						<%
							if (prodotti != null && prodotti.size() != 0) {
								Iterator<?> it = prodotti.iterator();
								int c = 0;
								while (it.hasNext()) {
									ProductBean bean = (ProductBean) it.next();
									if (c == 0) {
						%>
						<div class="row">
							<%
								}
							%>
							<div class="col-sm-4" style="padding-bottom: 2em;">
								<div class="mid-pop">
									<a href="Prodotto?ref=<%=bean.getId_prodotto()%>"> <img
										style="margin-left: auto; margin-right: auto;"
										src="images/prodotti/<%=bean.getFirstImm()%>"
										class="img-responsive" alt="">
									</a>
									<div class="mid-1">
										<div>
											<div>
												<span> <%
 	if (bean.getNomepadre() != null) {
 %><%=bean.getNomepadre()%> / <%
 	}
 %><%=bean.getNomecategoria()%></span>
												<h6>
													<a href="Prodotto?ref=<%=bean.getId_prodotto()%>"><%=bean.getNome()%></a>

												</h6>
												<div class="clearfix"></div>
											</div>
											<div class="clearfix"></div>
										</div>
										<div class="mid-2">
											<p>
												<label>PREZZO</label><em class="item_price"><%=bean.getPrezzo()%>
													&euro;</em>
											</p>
											<div class="clearfix"></div>
										</div>
									</div>
								</div>
							</div>
							<%
								c++;
										if (c == 3) {
											c = 0;
							%>
						</div>
						<%
							}
						%>
						<%
							}
							} else {
						%><h3>Non ci sono prodotti in questa categoria.</h3>
						<%
							}
						%>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
		<!--products-->
	</div>
	<%@include file="/source/footer.html"%>
</body>
</html>