
<%
	Collection<?> tableCat = (Collection<?>) request.getAttribute("tableCat");
	if (tableCat == null) {
		response.sendRedirect("CategoryControl");
		return;
	}
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,code.CategoryBean"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/admin/source/head.html"%>
<title>Pannello di Amministrazione | Categorie</title>
</head>
<body>
	<%@ include file="/admin/source/header.jsp"%>
	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="panel panel-default">

			<div class="panel-heading">Lista Categorie</div>
			<div class="panel-body">
				<%
					String selectCat = "";
					if (request.getAttribute("errore") != null) {
				%>
				<div class="alert bg-danger" role="alert"><%=request.getAttribute("errore")%></div>
				<%
					}
				%>
				<div class="row">
					<h3 class="col-md-12"></h3>
					<div class="col-md-6">
						<%
							if (tableCat != null && tableCat.size() != 0) {
						%>

						<ul class="menu-drop">

							<%
								Iterator<?> it = tableCat.iterator();
									while (it.hasNext()) {
										CategoryBean cat = (CategoryBean) it.next();
										if (cat.getId_cat_padre() == 0) {
											selectCat = selectCat + "<option value=\"" + cat.getId_categoria() + "\">" + cat.getNome()
													+ "</option>";
							%>
							<li>
								<form class="form-inline" action="CategoryControl" method="post">
									<input class="form-control" type="text" name="categoria"
										value="<%=cat.getNome()%>" required> <input
										type="hidden" name="id" value="<%=cat.getId_categoria()%>"
										required> <input type="hidden" name="action"
										value="updateCat" required>
									<div class="pull-right">
										<input class="btn btn-primary" type="submit"
											value="Salva Nome"> &middot; <a
											class="btn btn-danger"
											href="CategoryControl?action=deleteCat&id=<%=cat.getId_categoria()%>">Elimina</a>
									</div>
								</form>
								<hr> <%
 	Iterator<?> itera = tableCat.iterator();
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
									<li>
										<form class="form-inline" action="CategoryControl"
											method="post">
											<input class="form-control" type="text" name="categoria"
												value="<%=figlio.getNome()%>" required> <input
												type="hidden" name="id"
												value="<%=figlio.getId_categoria()%>" required> <input
												type="hidden" name="action" value="updateCat" required>
											<div class="pull-right">
												<input type="submit" value="Salva Nome"
													class="btn btn-default"> &middot; <a
													class="btn btn-danger"
													href="CategoryControl?action=deleteCat&id=<%=figlio.getId_categoria()%>">Elimina</a>
											</div>
										</form>
										<hr>
									</li>
									<%
										}
													}
													if (c != 0) {
									%>
								</ul> <%
 	}
 			}
 %>
							</li>
							<%
								}
							%>
						</ul>
						<%
							} else {
						%>
						NON CI SONO CATEGORIE, AGGIUNGILE!
						<%
							}
						%>
					</div>
					<div class="col-md-6">
						<h3>Inserisci categoria</h3>
						<form action="CategoryControl" method="post">

							<div class="form-group">
								<label>Nome categoria</label> <input class="form-control"
									type="text" name="categoria"
									placeholder="Inserisci il nome della categoria" required>
							</div>
							<div class="form-group">
								<label>Categoria padre</label> <select class="form-control"
									name="padre" required>
									<option value="0">Nessuna categoria padre (categoria
										principale)</option>
									<%=selectCat%>
								</select>
							</div>
							<input type="hidden" name="action" value="addCat" required>
							<input type="submit" value="Aggiungi categoria"
								class="btn btn-primary">
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>