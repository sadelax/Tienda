package es.getafe.examen.vista.actions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class ActionsFactory {

	private Map<String, Action> actions;
	private Set<String> urlPublica;

	public final String VISTA_PRE = "/WEB-INF/vistas/";
	public final String VISTA_POST = ".jsp";

	public ActionsFactory() {
		creaActions();
	}

	public Action getAction(HttpServletRequest req) {
		String clave = req.getMethod() + req.getPathInfo();
		/////////////// GET o POST //////menu_principal
		String clave2 = "GET-POST" + req.getPathInfo();

		Action respuesta;

		if (urlPublica.contains(req.getPathInfo().substring(1)) || isSessionActive(req)) {
			if (actions.containsKey(clave)) {
				respuesta = actions.get(clave);				
			}
			else if (actions.containsKey(clave2)) {
				respuesta = actions.get(clave2);				
			}
			else {
				respuesta = actions.get("URL_INCORRECTA");				
			}
		} else {
			respuesta = actions.get("URL_INCORRECTA");			
		}
		return respuesta;
	}

	private void creaActions() {
		actions = new HashMap<>();
		actions.put("GET-POST/login", new LoginAction());
		actions.put("GET/menu_principal", new MenuPrincipalAction());
		actions.put("GET-POST/listado_productos", new ListadoProductosAction());
		actions.put("GET-POST/registro", new RegistroAction());
		actions.put("GET/registro_ok", actions.get("GET/menu_principal"));
		actions.put("GET/productos_fabricante", new ProductosFabricanteAction());
		actions.put("URL_INCORRECTA", new CerrarSesionAction());
		actions.put("GET/cerrar_sesion", actions.get("URL_INCORRECTA"));

		urlPublica = new HashSet<>();
		urlPublica.add("login");
		urlPublica.add("cerrar_sesion");
		urlPublica.add("registro");
		urlPublica.add("registro_ok");
	}

	private boolean isSessionActive(HttpServletRequest req) {
		return req.getSession().getAttribute("usuario") != null;
	}
}
