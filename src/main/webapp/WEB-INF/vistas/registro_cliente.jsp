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
	<script>

 		function validaForm(ev){

			ev.preventDefault();
 			console.log("has hecho submit");

			var nombre = document.getElementById("nombre").value;
 			var apellido1 = document.getElementById("apellido1").value;
 			var nif = document.getElementById("nif").value;
 			var sexo = document.getElementById("sexo").value;
 			var municipio = document.getElementById("municipio").value;
 			var provincia = document.getElementById("provincia").value;
 			var error = document.getElementById("error");

 			if(!nombre.trim() || !apellido1.trim() || !nif.trim() || !sexo.trim() || !municipio.trim() || !provincia.trim()) {
 				error.textContent = "rellena los campos obligatorios";
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
	<header class="cabecera">
		<nav>
			<ul>
				<li><a href="${home}/menu_principal">&#127968;</a></li>
				<li><a href="${home}/listado_usuarios">listado de usuarios</a></li>
				<li><a href="${home}/listado_clientes">listado de clientes</a></li>
				<li id="nav-act"><a href="${home}/registro_cliente">registrar nuevo cliente</a></li>
				<li><a href="${home}/cerrar_sesion">cerrar sesión</a></li>
			</ul>
		</nav>
		<hr>
		<h2>registrar un nuevo cliente</h2>
	</header>
	<main class="cuerpo">
		<form id="frm_reg" action="${home}/registro_cliente" method="post" autocomplete="off">
			<input class="input_reg" id="nombre" type="text" name="nombre" placeholder="nombre (*)"></input>
			<input class="input_reg" id="apellido1" type="text" name="apellido1" placeholder="primer apellido (*)"></input>
			<input class="input_reg" id="apellido2" type="text" name="apellido2" placeholder="segundo apellido"></input>
			<input class="input_reg" id="nif" type="text" name="nif" placeholder="nif (*)"></input>
			<input class="input_reg" id="sexo" type="text" name="sexo" placeholder="sexo"></input>
			<input class="input_reg" id="municipio" type="text" name="municipio" placeholder="municipio (*)"></input>
			<input class="input_reg" id="provincia" type="text" name="provincia" placeholder="provincia (*)"></input>
			<div id="botones">
				<input id="registro_cliente" class="boton" type="submit" value="registro cliente">
			</div>
		</form>
		<p id="error">
			<c:choose>
				<c:when test="${error eq 'campos_vacios_cli'}">
					rellena los campos obligatorios (*)
				</c:when>
			</c:choose>
		</p>
	</main>
</body>
</html>
