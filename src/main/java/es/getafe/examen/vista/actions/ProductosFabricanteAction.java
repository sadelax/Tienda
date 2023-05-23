package es.getafe.examen.vista.actions;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.getafe.examen.modelo.Fabricante;
import es.getafe.examen.negocio.Tienda;

@Component
public class ProductosFabricanteAction implements Action {

	@Autowired
	private Tienda neg;

	@Override
	public String get(String path, HttpServletRequest req, HttpServletResponse resp) {
		Set<Fabricante> fabs  = neg.getFabricantesActivos();
		req.setAttribute("fabs", fabs);
		return path;
	}

	@Override
	public String post(String path, HttpServletRequest req, HttpServletResponse resp) {
		
		HttpSession sesion = req.getSession();
		String vista = null;
		String idFabStr = req.getParameter("idFabricante");
		int id;
		if(idFabStr != null) {
			try {
				id = Integer.parseInt(idFabStr);
				Fabricante fab = neg.getFabricanteConProductos(id);
				sesion.setAttribute("fab", fab);
				vista = "redirect:productos_fabricante_html";
			} catch (NumberFormatException e) {
				vista = "redirect:" + path;
			}
		}
		return vista;
	}

}
