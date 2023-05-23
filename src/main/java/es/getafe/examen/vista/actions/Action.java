package es.getafe.examen.vista.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {

	default String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String vista = null;
		
		String path = req.getPathInfo() != null ? req.getPathInfo().substring(1) : "";
		
		switch(req.getMethod().toLowerCase()) {
		case "get":
			vista = get(path, req, resp);
			break;
		case "post":
			vista = post(path, req, resp);
			break;
		}
		return vista;
	}
	
	String get(String path, HttpServletRequest req, HttpServletResponse resp);
	String post(String path, HttpServletRequest req, HttpServletResponse resp);
}
