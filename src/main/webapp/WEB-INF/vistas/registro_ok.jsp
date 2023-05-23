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
	<title>Felicidades!</title>
	<link rel="stylesheet" href="${css}/index.css">
</head>
<body>
	<div id="cabecera">
		<h2><c:out value="${usuarioNuevo.name}" />, te has registrado con éxito.</h2>
	</div>
	<div id="registro_ok">
		<p>valida tu información en el e-mail que te hemos enviado y vuelve a loguearte.</p>
		<a href="${home}/login">login</a>
	</div>
</body>
</html>
