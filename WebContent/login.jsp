<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.lang.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/source/head.html"%>
<title>Now I WANT | e-commerce Abbigliamento</title>
<script>
	function controlloLogin(form) {
		var user = form.elements.namedItem("username").value.trim();
		var pass = form.elements.namedItem("password").value.trim();
		if (user !== null && user !== "" && pass !== null && pass !== "") {

			return true;
		} else {
			document.getElementById("alert").style.display = "block";
			return false;
		}
	}

	function controlloReg(form) {
		var user = form.elements.namedItem("user").value.trim();
		var pass = form.elements.namedItem("pass").value.trim();
		var nome = form.elements.namedItem("nome").value.trim();
		var cognome = form.elements.namedItem("cognome").value.trim();
		if (user !== null && user !== "" && pass !== null && pass !== ""
				&& nome !== null && nome !== "" && cognome !== null
				&& cognome !== "") {

			return true;
		} else {
			document.getElementById("alert").style.display = "block";
			return false;
		}
	}
</script>
</head>
<body>
	<div class="contenitore">
		<%@ include file="/source/header.jsp"%>

		<%
			String errore = (String) request.getAttribute("erroreLogin");
			if (errore == null) {
				errore = "";
			}
			if (!errore.equals("")) {
		%>
		<div class="alert alert-danger" role="alert">
			<strong><%=errore%></strong>
		</div>
		<%
			}
		%>
		<div style="display: none;" id="alert" class="alert alert-danger"
			role="alert">
			<strong>Compilare tutti i campi obbligatori</strong>
		</div>
		<div class="col-md-6">

			<h3 style="padding: 1em 1em;">Effettua il login</h3>
			<form action="Login" onsubmit="return controlloLogin(this);"
				method="post">
				<div class="form-group">
					<label class="col-sm-2 control-label">Username:</label> <input
						class="form-control" type="text" name="username" value="" required>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">Password:</label> <input
						class="form-control" type="password" name="password" value=""
						required>
				</div>
				<div style="text-align: center;">
					<input class="btn btn-lg btn-primary" type="submit" value="Accedi">
				</div>
			</form>
		</div>
		<div class="col-md-6">
			<h3 style="padding: 1em 1em;">Registrati come nuovo utente</h3>
			<form action="Register" onsubmit="return controlloReg(this);"
				method="post">
				<div class="form-group">
					<label class="col-sm-2 control-label">Nome:</label> <input
						class="form-control" type="text" name="nome" value="" required>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">Cognome:</label> <input
						class="form-control" type="text" name="cognome" value="" required>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">Username:</label> <input
						class="form-control" type="text" name="user" value="" required>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">Password:</label> <input
						class="form-control" type="password" name="pass" value="" required>
				</div>

				<div style="text-align: center;">
					<input class="btn btn-lg btn-success" type="submit"
						value="Registrati">
				</div>
			</form>
		</div>
	</div>
	<%@include file="/source/footer.html"%>
</body>
</html>