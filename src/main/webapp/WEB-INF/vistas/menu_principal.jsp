<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>menú principal</title>
<link rel="stylesheet" href="${css}/index.css">
</head>
<body>
<body>
	<header class="cabecera">
		<nav>
			<ul>
				<li><a href="${home}/listado_productos">listado de productos</a></li>
				<li><a href="${home}/registro_fabricante">alta nuevo fabricante</a></li>
				<li><a href="${home}/registro_producto">agregar nuevo producto</a></li>
				<li><a href="${home}/productos_fabricante">productos por fabricante</a></li>
				<li><a href="${home}/productos_fabricante_html">productos por fabricante HTML</a></li>
				<li><a href="${home}/productos_fabricante_json">productos por fabricante JSON</a></li>
				<li><a href="${home}/ofertas">ofertones</a></li>
				<li><a href="${home}/cerrar_sesion">cerrar sesión</a></li>
			</ul>
		</nav>
		<hr>
		<h2>menú principal</h2>
	</header>
	<main class="cuerpo">
	</main>
</body>
</body>
</html>