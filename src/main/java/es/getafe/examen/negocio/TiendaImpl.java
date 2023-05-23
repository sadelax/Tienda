package es.getafe.examen.negocio;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import es.getafe.examen.modelo.Fabricante;
import es.getafe.examen.modelo.Producto;
import es.getafe.examen.modelo.Usuario;
import es.getafe.examen.persistencia.FabricanteDao;
import es.getafe.examen.persistencia.FabricanteDaoImpl;
import es.getafe.examen.persistencia.ProductoDao;
import es.getafe.examen.persistencia.ProductoDaoImpl;
import es.getafe.examen.persistencia.UsuarioDao;
import es.getafe.examen.persistencia.UsuarioDaoImpl;

public class TiendaImpl implements Tienda {

	private ProductoDao pDao;
	private FabricanteDao fDao;
	private UsuarioDao uDao;
	
	public TiendaImpl() {
		pDao = new ProductoDaoImpl();
		fDao = new FabricanteDaoImpl();
		uDao = new UsuarioDaoImpl();
	}
	
	@Override
	public Set<Producto> getProductos() {
		Set<Producto> resu = new TreeSet<>(new Comparator<Producto>() {
			@Override
			public int compare(Producto o1, Producto o2) {
				Collator col = Collator.getInstance(new Locale("es"));
				String s1 = o1.getProducto() + o1.getIdProducto();
				String s2 = o2.getProducto() + o2.getIdProducto();
				return col.compare(s1, s2);
			}
		});
		resu.addAll(pDao.findAll());
		return resu;
	}

	@Override
	public Set<Producto> getProductos(String descripcion) {
		List<Producto> resu = pDao.findByDescripcion(descripcion);
		return resu.size() > 0 ? new TreeSet<>(resu) : null;
	}

	@Override
	public double getMediaPrecioProductosByFabricante(int idFabricante) {
		Fabricante f = fDao.findById(idFabricante);
		double media = 0;
		if(f != null) {
			for (Producto p : f.getProductos()) {
				media += p.getPrecio();
			}
			if(f.getProductos().size() > 0)
				media /= f.getProductos().size();
		}
		return media;
	}

	@Override
	public void addFabricante(Fabricante fabricante) {
		fDao.save(fabricante);
	}

	@Override
	public void addProducto(Producto producto) {
		pDao.save(producto);
	}

	@Override
	public Set<Fabricante> getFabricantes() {
		Set<Fabricante> resu = new TreeSet<>(comparatorFabNombre());
		resu.addAll(fDao.findAll());
		return resu;
	}

	@Override
	public Set<Fabricante> getFabricantesActivos() {
		Set<Fabricante> resu = new TreeSet<>(comparatorFabNombre());
		resu.addAll(fDao.findOnlyActive());
		return resu;
	}

	@Override
	public Fabricante getFabricante(int idFabricante) {
		return fDao.findByIdLazy(idFabricante);
	}

	@Override
	public Fabricante getFabricanteConProductos(int idFabricante) {
		return fDao.findById(idFabricante);
	}
	
	public Comparator<Fabricante> comparatorFabNombre(){
		return new Comparator<Fabricante>() {
			@Override
			public int compare(Fabricante f1, Fabricante f2) {
				Collator col = Collator.getInstance(new Locale("es"));
				String s1 = f1.getFabricante() + f1.getIdFabricante();
				String s2 = f2.getFabricante() + f2.getIdFabricante();
				return col.compare(s1, s2);
			}
		};
	}

	@Override
	public Usuario validaLogin(String usr, String pwd) {
		return uDao.valida(usr, pwd);
	}

	@Override
	public boolean agregaUsuario(Usuario usuario) {
		return uDao.save(usuario);
	}
}
