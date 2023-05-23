package es.getafe.examen.vista;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.getafe.examen.modelo.Fabricante;
import es.getafe.examen.modelo.Producto;
import es.getafe.examen.modelo.Usuario;
import es.getafe.examen.negocio.Tienda;
import es.getafe.examen.negocio.TiendaImpl;

//@WebServlet("/home/*")
public class ControllerOld extends HttpServlet {

	private Tienda neg;
	private String context;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getPathInfo();

		if (req.getSession().getAttribute("usuario") != null) {
			switch (path) {
			case "/listado_productos":
				req.getRequestDispatcher("/WEB-INF/vistas/listado_productos.jsp").forward(req, resp);
				break;
			case "/productos_fabricante":
				Set<Fabricante> fabs = neg.getFabricantesActivos();
				req.setAttribute("fabs", fabs);
				req.getRequestDispatcher("/WEB-INF/vistas/productos_fabricante.jsp").forward(req, resp);
				break;
			case "/productos_fabricante_html":
				fabs = neg.getFabricantesActivos();
				req.setAttribute("fabs", fabs);
				req.getRequestDispatcher("/WEB-INF/vistas/productos_fabricante_html.jsp").forward(req, resp);
				break;
			case "/productos_fabricante_json":
				fabs = neg.getFabricantesActivos();
				req.setAttribute("fabs", fabs);
				req.getRequestDispatcher("/WEB-INF/vistas/productos_fabricante_json.jsp").forward(req, resp);
				break;
			case "/ofertas":
				Set<Producto> prods = neg.getProductos("");
				req.setAttribute("prods", prods);
				req.getRequestDispatcher("/WEB-INF/vistas/ofertas.jsp").forward(req, resp);
				break;
			case "/menu_principal":
				req.getRequestDispatcher("/WEB-INF/vistas/menu_principal.jsp").forward(req, resp);
				break;
			case "/cerrar_sesion":
				req.getSession().invalidate();
				req.getRequestDispatcher("/WEB-INF/vistas/login.jsp").forward(req, resp);
				break;
			}
		} else {
			switch (path) {
			case "/registro":
				req.getRequestDispatcher("/WEB-INF/vistas/registro_usuario.jsp").forward(req, resp);
				break;
			default:
				req.getRequestDispatcher("/WEB-INF/vistas/login.jsp").forward(req, resp);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();

		HttpSession sesion = req.getSession();

		if (sesion.getAttribute("usuario") != null) {
			switch (path) {
			case "/listado_productos":
				break;
			case "/productos_fabricante":
				String idFabStr = req.getParameter("idFabricante");
				int id;
				if (idFabStr != null) {
					try {
						id = Integer.parseInt(idFabStr);
						Fabricante fab = neg.getFabricanteConProductos(id);
						sesion.setAttribute("fab", fab);
						resp.sendRedirect(context + "/home/productos_fabricante");
					} catch (NumberFormatException e) {
						resp.sendRedirect(context + "/home/cerrar_sesion");
					}
				}
				break;
			case "/productos_fabricante_html_respuesta":
				idFabStr = req.getParameter("idFabricante");
				if (idFabStr != null) {
					try {
						id = Integer.parseInt(idFabStr);
						Fabricante fab = neg.getFabricanteConProductos(id);
						sesion.setAttribute("fab", fab);
						req.getRequestDispatcher("/WEB-INF/vistas/productos_fabricante_html_respuesta.jsp").forward(req,
								resp);
					} catch (NumberFormatException e) {
						resp.sendRedirect(context + "/home/cerrar_sesion");
					}
				}
				break;
			case "/productos_fabricante_json_respuesta":
				idFabStr = req.getParameter("idFabricante");
				if (idFabStr != null) {
					try {
						id = Integer.parseInt(idFabStr);
						Fabricante fab = neg.getFabricanteConProductos(id);
						sesion.setAttribute("fab", fab);

						Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
						String json = gson.toJson(fab.getProductos());

//					resp.getWriter().println(json);
						req.setAttribute("json", json);

						req.getRequestDispatcher("/WEB-INF/vistas/productos_fabricante_json_respuesta.jsp").forward(req,
								resp);
					} catch (NumberFormatException e) {
						resp.sendRedirect(context + "/home/cerrar_sesion");
					}
				}
				break;
			case "/ofertas":
				String idsParam = req.getParameter("id_prods");
				System.out.println(idsParam);

				String[] idsParamArray = req.getParameterValues("id_prods");
				System.out.println(idsParam);

				String descuentosParam = req.getParameter("descuentos");
				if (idsParam != null && descuentosParam != null) {
					String[] ids = idsParam.split(",");
					String[] descuentos = descuentosParam.split(",");
					try {
						int[] idsProductos = new int[ids.length];
						double[] descuentosProductos = new double[descuentos.length];
						for (int i = 0; i < ids.length; i++) {
							idsProductos[i] = Integer.parseInt(ids[i]);
							descuentosProductos[i] = Double.parseDouble(descuentos[i]);
						}

					} catch (NumberFormatException e) {

					}
				}
				break;
			}
		} else {
			switch (path) {
			case "/login": {
			}
				break;
			case "/registro": {
				String nombreP = req.getParameter("nombre");
				String usuarioP = req.getParameter("usuario");
				String emailP = req.getParameter("email");
				String passwordP = req.getParameter("password");
				String password2P = req.getParameter("password2");

				req.setAttribute("nombre", nombreP);
				req.setAttribute("usuario", usuarioP);

				if (isNotEmpty(nombreP) && isNotEmpty(usuarioP) && isNotEmpty(passwordP) && isNotEmpty(password2P)) {
					if (passwordP.equals(password2P)) {

						Usuario nuevo = new Usuario(nombreP, usuarioP, passwordP, emailP);

						if (neg.agregaUsuario(nuevo)) {
							req.getRequestDispatcher("/WEB-INF/vistas/registro_ok.jsp").forward(req, resp);
						} else {
							sesion.setAttribute("error", "existe");
							resp.sendRedirect(context + "/home/registro");
						}

					} else {
						sesion.setAttribute("error", "pass");
						resp.sendRedirect(context + "/home/registro");
					}
				} else {
					sesion.setAttribute("error", "obli");
					resp.sendRedirect(context + "/home/registro");
				}
			}
				break;
			}
		}

	}

	@Override
	public void init() throws ServletException {
		neg = new TiendaImpl();

		ServletContext app = getServletContext();
		context = app.getContextPath();

		app.setAttribute("context", context);
		app.setAttribute("home", context + "/home");
		app.setAttribute("css", context + "/css");
	}
	
	private boolean isNotEmpty(String param) {
		return param != null && param.length() > 0;
	}

}
