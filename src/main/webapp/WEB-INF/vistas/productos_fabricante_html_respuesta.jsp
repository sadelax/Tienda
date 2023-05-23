<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<table id="tabla_datos">
	<thead>
		<tr>
			<th>descripción</th>
			<th>precio</th>
			<th>fabricante</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="producto" items="${fab.productos}">
			<tr id="${producto.idProducto}">
				<td>${producto.producto}</td>
				<td>${producto.precio}</td>
				<td>${producto.fabricante}</td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<td colspan=7>cantidad productos: ${fab.productos.size()}</td>
		</tr>
	</tfoot>
</table>