package es.getafe.examen.vista.actions;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.getafe.examen.modelo.Producto;
import es.getafe.examen.negocio.Tienda;
import es.getafe.examen.negocio.TiendaImpl;

@Component
public class ListadoProductosAction implements Action {

	@Autowired
	private Tienda neg;
	
	@Override
	public String get(String path, HttpServletRequest req, HttpServletResponse resp) {
		return path;
	}

	@Override
	public String post(String path, HttpServletRequest req, HttpServletResponse resp) {
		HttpSession sesion = req.getSession();
		
		String desc = req.getParameter("descripcion");
		Set<Producto> prods;
		if (desc != null) {
			prods = neg.getProductos(desc.trim());
		} else {
			prods = neg.getProductos("");
		}
		sesion.setAttribute("productos", prods);
		return "redirect:" + path;
	}

}
