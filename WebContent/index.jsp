<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,code.ProductBean"%>
<%
	Collection<?> prodotti = (Collection<?>) request.getAttribute("prodottiHome");
	if (prodotti == null) {
		response.sendRedirect("Home");
		return;
	}
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
		<div
			style="background: url(images/Principale.jpg) no-repeat; background-position: right top; background-size: auto 100%"
			class="banner">
			<div class="container">
				<h1 class="rw-sentence">
					<span> <img src="images/logobianco.png"
						style="float: right; width: 50%; margin-top: 100px;"
						class="img-responsive" alt=""></span>

				</h1>
			</div>
		</div>


		<div class="content-mid">
			<h3>Ultimi 4 Arrivi</h3>
			<label class="line"></label>
			<div class="mid-popular">
				<%
					if (prodotti != null && prodotti.size() != 0) {
						Iterator<?> it = prodotti.iterator();
						while (it.hasNext()) {
							ProductBean bean = (ProductBean) it.next();
				%>
				<div class="col-sm-3" style="padding-bottom: 2em;">
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
					}
					} else {
				%>
				<h3>Non abbiamo ultimi arrivi.</h3>

				<%
					}
				%>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<%@include file="/source/footer.html"%>
</body>
</html>