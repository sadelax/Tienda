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
<link rel="stylesheet" href="${css}/ofertas.css">
<script>

	var destino;
	var vacia;

	function borrarVacia(){
		if(vacia){
			destino.removeChild(vacia);
			vacia = null;
		}
	}

	function inicio(ev){
		ev.dataTransfer.setData('id', ev.currentTarget.id);
		console.log(ev.currentTarget.id);
	}
	
	function soltar(ev){
		if(validaDescuento()){
			ev.preventDefault();
			borrarVacia();
			var id = ev.dataTransfer.getData('id');
			console.log(id);
			if(id != ''){
				moverFila(ev.currentTarget, document.getElementById(id));
			}
		}
	}

	function mover(ev){
		if(validaDescuento()){
			borrarVacia();
			moverFila(destino, ev.currentTarget);
		}
	}
	
	function moverFila(body, tr){
		tr.removeEventListener('dragstart', inicio);
		tr.removeEventListener('dblclick', mover);
		// crear 1 td
		var tdDto = document.createElement('td');
		//crear 1 td
		var tdFinal = document.createElement('td');
		//hacer td1 hijo del tr
		tr.insertBefore(tdDto, tr.querySelector('td:nth-child(3)'));
		//hacer td2 hijo del tr
		tr.insertBefore(tdFinal, tr.querySelector('td:nth-child(4)'));
		//colocar el valor del input de dto en el td1
		var descuento = document.getElementById('input_descuento').value;
		tdDto.textContent = descuento + '%';
		//colocar el precio c dto enel td2
		var precioActual = tr.querySelector('td:nth-child(2)').textContent;
		tdFinal.textContent = (Math.round((precioActual - precioActual * descuento / 100) * 100 ) / 100).toFixed(2);
		// ev.currentTarget.appendChild(document.getElementBy(id));

		body.appendChild(tr);
	}

	function validaDescuento(){
		var iDto = document.getElementById('input_descuento').value;
		if(isNaN(iDto) || iDto <= 0 || iDto > 100){
			alert('el descuento es incorrecto');
			return false;
		} else {
			return true;
		}
	}


	function enviar(ev) {
		var idsOfertas = new Array();
		var descuentos = new Array();
		ev.preventDefault();
		var filas = document.querySelectorAll('#tabla_ofertas tbody tr');
		for (var i=0; i<filas.length; i++){
			idsOfertas[i] = filas[i].id;
			var tdDcto = filas[i].querySelector('td:nth-child(3)').textContent;
			descuentos[i] = tdDcto.substring(0, tdDcto.length - 1);
		}
		document.querySelector('#id_prods').value = idsOfertas;
		document.querySelector('#descuentos').value = descuentos;
		this.submit();
}


	window.onload = function(){
		vacia = document.querySelector('#vacia');
		
		var moviles = document.querySelectorAll('.moviles');

		for(var i = 0; i < moviles.length; i++){
			moviles[i].addEventListener('dragstart', inicio);
			moviles[i].addEventListener('dblclick', mover);
		}

		destino = document.querySelector('#tabla_ofertas tbody');
		destino.addEventListener('dragover', 
		function(ev){
			ev.preventDefault();
		});
		destino.addEventListener('drop', soltar);

		vacia = document.querySelector('#vacia');

		document.getElementById('frm_envio').addEventListener('submit', enviar);
	}

</script>
</head>
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
		<h2>ofertas</h2>
	</header>
	<main class="container">
		<div id="tabla_contenedor">
		  <table id="tabla_datos" class="datos">
			<thead>
			  <tr>
				<th>descripcion</th>
				<th>precio</th>
				<th>fabricante</th>
			  </tr>
			</thead>
			<tbody>
			  <c:forEach var="producto" items="${productos}">
				<tr id="${producto.idProducto}" draggable="true" class="moviles">
				  <td>${producto.producto}</td>
				  <td>${producto.precio}</td>
				  <td>${producto.fabricante}</td>
				</tr>
			  </c:forEach>
			</tbody>
		  </table>
	  
		  <div id="ofertas">
			<div>
			  <input type="text" id="input_descuento" placeholder="descuento" min="0" max="100" step="0.1">
			</div>
			<table id="tabla_ofertas" class="ofertas">
			  <thead>
				<tr>
				  <th>descripcion</th>
				  <th>precio</th>
				  <th>%</th>
				  <th>precio %</th>
				  <th>fabricante</th>
				</tr>
			  </thead>
			  <tbody>
				<tr id="vacia">
				  <td colspan="5">arrastrar hacia aquí...</td>
				</tr>
			  </tbody>
			</table>
				<form id="frm_envio" action="${home}/ofertas" method="post">
					<input id="id_prods" name="id_prods" type="hidden"> <input
						id="descuentos" name="descuentos" type="hidden">
					<button type="submit">enviar</button>
				</form>
			</div>
		</div>
	  </main>
	  
</body>
</html>
