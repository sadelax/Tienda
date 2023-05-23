<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>login</title>
	<link rel="stylesheet" href="${css}/index.css">
	<script>

 		function validaForm(ev){

			ev.preventDefault();
 			console.log("has hecho submit");

 			var usuario = document.getElementById("user").value;
 			var pwd = document.getElementById("pwd").value;
 			var error = document.getElementById("error");

 			if(!usuario.trim() || !pwd.trim()) {
 				error.textContent = "todos los campos son obligatorios";
 			} else {
 				error.textContent = "";
 				this.submit(); // esto lo envÃ­a a java
 			}
 		}

// 		window.onload = function(){
// 			document.getElementById("frm_log").addEventListener("submit", validaForm);
// 		}
	</script>
</head>
<body>
	<header id="cabecera">
		<h2>&#11088; acceso usuarios &#11088;</h2>
	</header>
	<div id="formulario">
		<form id="frm_log" action="${home}/login" method="post" autocomplete="off">
			<input id="user" type="text" name="usuario" placeholder="usuario (*)"></input>
			<input id="pwd" type="password" name="password" placeholder="password (*)"></input>
			<div id="botones">
				<input id="login" class="boton" type="submit" value="login">
				<p>¿no estás registrado? <a href="${home}/registro">regístrate</a></p>
			</div>
		</form>
		<p id="error">
			<c:choose>
				<c:when test="${error eq 'no_user_reg'}">
					usuario y/o contraseña incorrectos
				</c:when>
				<c:when test="${error eq 'campos_vacios_login'}">
					(*) campos obligatorios
				</c:when>
			</c:choose>
		</p>
	</div>
</body>
</html>
