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
	<title>Document</title>
	<link rel="stylesheet" href="${css}/index.css">
	<link rel="stylesheet" href="${css}/formulario-reg.css">
	<script>

 		function validaForm(ev){

			ev.preventDefault();
 			console.log("has hecho submit");

			var nombre = document.getElementById("nombre").value;
 			var usuario = document.getElementById("user").value;
 			var pwd = document.getElementById("pwd").value;
 			var pwd2 = document.getElementById("pwd2").value;
 			var error = document.getElementById("error");

 			if(!nombre.trim() || !usuario.trim() || !pwd.trim() || !pwd2.trim()) {
 				error.textContent = "todos los campos son obligatorios";
 			} else if(pwd != pwd2) {
 				error.textContent = "las password no coinciden";
 			} else {
 				error.textContent = "";
 				this.submit();				
 			}
 		}

// 		window.onload = function(){
// 			document.getElementById("frm_reg").addEventListener("submit", validaForm);
// 		}
	</script>
</head>
<body>
	<header id="cabecera">
		<h2>&#127812; registro &#127812;</h2>
	</header>
	<div id="formulario_reg">
		<form id="frm_reg" action="${home}/registro" method="post" autocomplete="off">
			<input class="input_reg" id="nombre" type="text" name="nombre" placeholder="nombre (*)"></input>
			<input class="input_reg" id="user" type="text" name="usuario" placeholder="usuario(*)"></input>
			<input class="input_reg" id="email" type="email" name="email" placeholder="email(*)"></input>
			<input class="input_reg" id="pwd" type="password" name="password" placeholder="password(*)"></input>
			<input class="input_reg" id="pwd2" type="password" name="password-bis" placeholder="repita password(*)"></input>
			<div id="botones">
				<input id="frm_reg" class="boton" type="submit" value="enviar">
				<p><a href="${home}/login">volver a login</a></p>
			</div>
		</form>
		<p id="error">
			<c:choose>
				<c:when test="${error eq 'ya_existe'}">
					el usuario ya existe
				</c:when>
				<c:when test="${error eq 'no_match_pwd' or error eq 'campos_vacios'}">
					rellena los campos obligatorios (*)
				</c:when>
				
			</c:choose>
		</p>
	</div>
</body>
</html>
