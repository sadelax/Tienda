<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>listado productos</title>
<link rel="stylesheet" href="${css}/index.css">
<script>
	function solicitud() {
		// preparamos los parámetros de la petición. Ejemplo: idFabricante=17
		var params = "idFabricante="
				+ encodeURIComponent(document.getElementById("idFabricante").value);

		// creamos req, objeto q nos permitirá hacer la petición
		// xmlhttpreq es lo q lo hace una PETICIÓN AJAX :)
		var req = new XMLHttpRequest();

		// definimos el método y la URL
		req.open("post", "productos_fabricante_json_respuesta");

		// req.addEventListener('readystatechange', function(){});

		// es lo mismo que req.addEventListener('readystatechange', function(){});
		// esto es registrar el evento
		req.onreadystatechange = function() {
			if (req.readyState == 4 && req.status == 200) {
				cargaTabla(req);
			}
		};

		//cabecera para decirle qué le estamos mandado
		// si F12 en navegador se puede ver en cabeceras qué tipo de Content-type
		req.setRequestHeader('Content-type',
				'application/x-www-form-urlencoded');

		// esto es realizar la petición, con previo encabezado ^
		req.send(params);
	}

	function cargaTabla(req) {
		var productos = JSON.parse(req.responseText);
		
		document.querySelector("#tabla_datos tbody").innerHTML='';
		
		for(var i = 0; i < productos.length; i++){
			insertaFila(productos[i]);
		}
	}

	function insertaFila(producto){
		var tr = document.createElement('tr');
		var td = document.createElement('td');
		tr.appendChild(td);
		td.textContent = producto.producto;
		
		td = document.createElement('td');
		tr.appendChild(td);
		td.textContent = producto.precio;
		
		td = document.createElement('td');
		tr.appendChild(td);
		td.textContent = producto.fabricante.fabricante;

		document.querySelector("#tabla_datos tbody").appendChild(tr);
	}

	window.onload = function() {
		document.getElementById("idFabricante").addEventListener("change",
				solicitud);
	}
</script>
</head>
<body>
	<header class="cabecera">
		<nav>
			<ul>
				<li><a href="${home}/listado_productos">listado de
						productos</a></li>
				<li><a href="${home}/registro_fabricante">alta nuevo
						fabricante</a></li>
				<li><a href="${home}/registro_producto">agregar nuevo
						producto</a></li>
				<li><a href="${home}/productos_fabricante">productos por
						fabricante</a></li>
				<li><a href="${home}/productos_fabricante_html">productos
						por fabricante HTML</a></li>
				<li><a href="${home}/productos_fabricante_json">productos
						por fabricante JSON</a></li>
				<li><a href="${home}/ofertas">ofertones</a></li>
				<li><a href="${home}/cerrar_sesion">cerrar sesión</a></li>
			</ul>
		</nav>
		<hr>
		<h2>búsqueda de productos por fabricante</h2>
	</header>
	<main class="cuerpo">
		<form id="buscar-prod" action="${home}/productos_fabricante"
			method="post">
			<select id="idFabricante" name="idFabricante">
				<!-- <option value="1">fabricante1</option> -->
				<!-- <option value="2">fabricante2</option> -->
				<!-- <option value="3">fabricante3</option> -->
				<!-- <option value="4">fabricante4</option> -->
				<c:if test="${empty fab}">
					<option></option>
				</c:if>
				<!-- la primera opción vacía, a no ser q elija una -->
				<c:forEach var="fabricante" items="${fabs}">
					<option value="${fabricante.idFabricante}"
						${fabricante.idFabricante==fab.idFabricante ? "selected='selected'" : ""}>${fabricante.fabricante}</option>
				</c:forEach>
			</select>
		</form>


		<table id="tabla_datos">
			<thead>
				<tr>
					<th>descripción</th>
					<th>precio</th>
					<th>fabricante</th>
				</tr>
			</thead>
			<tbody>

			</tbody>
			<tfoot>
				<tr>
					<td colspan=7>cantidad productos: ${fab.productos.size()}</td>
				</tr>
			</tfoot>
		</table>
	</main>
</body>
</html>
