<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,code.ProductBean"%>
<%
	Collection<?> prodotti = (Collection<?>) request.getAttribute("prodotti");
	String key = request.getParameter("key");
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

		<div class="content-mid">
			<h3>
				Risultati per
				<%=key%></h3>
			<label class="line"></label>
			<div class="mid-popular">
				<%
					if (prodotti != null && prodotti.size() != 0) {
						Iterator<?> it = prodotti.iterator();
						while (it.hasNext()) {
							ProductBean bean = (ProductBean) it.next();
				%>
				<div class="col-sm-3"  style="padding-bottom: 2em;">
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
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="mid-2">
								<p>
									<label>PREZZO</label><em class="item_price"><%=bean.getPrezzo()%>
										&euro;</em>
								</p>
								<div class="block">
									<div class="starbox small ghosting"></div>
								</div>
								<div class="clearfix"></div>
							</div>
						</div>
					</div>
				</div>
				<%
					}
					} else {
				%>
				<h3>Nessun prodotto trovato.</h3>
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