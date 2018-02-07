<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,code.UserBean,code.StatBean,code.ProductBean"%>
<%
	StatBean stat = (StatBean) request.getAttribute("stat");
	Collection<?> prodotti = (Collection<?>) request.getAttribute("prodotti");
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/admin/source/head.html"%>
<title>Pannello di Amministrazione</title>
</head>
<body>
	<%@ include file="/admin/source/header.jsp"%>
	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="panel panel-default">
			<div class="panel-heading">Dashboard</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">
							Benvenuto nel pannello di amministrazione
							<%=user.getCognome()%>
							<%=user.getNome()%></h1>
					</div>
				</div>
				<%
					if (stat != null) {
				%>

				<div class="col-xs-12 col-md-6 col-lg-3">
					<div class="panel panel-teal panel-widget">
						<div class="row no-padding">
							<div class="col-sm-3 col-lg-5 widget-left">
								<i style="color: white;" class="fa fa-shopping-cart fa-4x"></i>
							</div>
							<div class="col-sm-9 col-lg-7 widget-right">
								<div class="large"><%=stat.getNumAcqui()%></div>
								<div class="text-muted">Ordini totali</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-xs-12 col-md-6 col-lg-3">
					<div class="panel panel-blue panel-widget">
						<div class="row no-padding">
							<div class="col-sm-3 col-lg-5 widget-left">
								<i style="color: white;" class="fa fa-eur fa-4x"></i>
							</div>
							<div class="col-sm-9 col-lg-7 widget-right">
								<div class="large">
									&euro;
									<%=stat.getTotAcqui()%></div>
								<div class="text-muted">Ricavi totali</div>
							</div>
						</div>
					</div>
				</div>
				<%
					}
				%>

				<%
					if (prodotti != null && prodotti.size() != 0) {
				%>
				<br> <br> <br> <br> <br>
				<div class="panel-heading">Rimanenze</div>
				<table class="tabler">
					<thead>
						<tr>
							<td>PRODOTTO</td>
							<td>TAGLIA</td>
							<td>QUANTITA</td>
						</tr>
					</thead>
					<%
						Iterator<?> it = prodotti.iterator();
							while (it.hasNext()) {
								ProductBean bean = (ProductBean) it.next();
					%>
					<tr>
						<td><a href="ProductEdit?id=<%=bean.getId_prodotto()%>">
								<%=bean.getNome()%></a></td>
						<td><%=bean.getTaglia()%></td>
						<td><%=bean.getQuantita()%></td>
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
		<!--/.row-->
	</div>
</body>
</html>