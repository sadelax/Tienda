package es.getafe.examen.vista.actions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionsFactory {

	private Map<String, Action> actions;
	private Set<String> urlPublica;

	public final String VISTA_PRE = "/WEB-INF/vistas/";
	public final String VISTA_POST = ".jsp";
	
	@Autowired
	private CerrarSesionAction cerrarSesion;
	@Autowired
	private ListadoProductosAction listadoProducto;
	@Autowired
	private LoginAction login;
	@Autowired
	private ProductosFabricanteAction productosFabricante;
	@Autowired
	private RegistroAction registro;
	@Autowired
	private MenuPrincipalAction menuPrincipal;

	public Action getAction(HttpServletRequest req) {
		String pathInfo = req.getServletPath().substring(5);
		String clave = req.getMethod() + pathInfo;
		/////////////// GET o POST //////menu_principal
		String clave2 = "GET-POST" + pathInfo;

		Action respuesta;

		if (urlPublica.contains(pathInfo.substring(1)) || isSessionActive(req)) {
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

	@PostConstruct
	private void creaActions() {
		actions = new HashMap<>();
		actions.put("GET-POST/login", login);
		actions.put("GET/menu_principal", menuPrincipal);
		actions.put("GET-POST/listado_productos", listadoProducto);
		actions.put("GET-POST/registro", registro);
		actions.put("GET/registro_ok", menuPrincipal);
		actions.put("GET/productos_fabricante", productosFabricante);
		actions.put("URL_INCORRECTA", cerrarSesion);
		actions.put("GET/cerrar_sesion", cerrarSesion);

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
