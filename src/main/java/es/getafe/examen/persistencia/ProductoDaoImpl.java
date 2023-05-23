package es.getafe.examen.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import es.getafe.examen.modelo.Producto;

@Repository
public class ProductoDaoImpl implements ProductoDao {

	private EntityManagerFactory emf;
	private EntityManager em;
	
	public ProductoDaoImpl() {
		emf = Emf.getEmf();
	}
	
	@Override
	public Producto findById(int idProducto) {
		em = emf.createEntityManager();
		Producto buscado = em.find(Producto.class, idProducto);
		em.close();
		return buscado;
	}

	@Override
	public List<Producto> findByDescripcion(String descripcion) {
		em = emf.createEntityManager();
		String jpql = "select p from Producto p where p.producto like :desc";
		TypedQuery<Producto> q = em.createQuery(jpql, Producto.class);
		q.setParameter("desc", "%" + descripcion + "%");
		List<Producto> resu = q.getResultList();
		em.close();
		return resu;
	}

	@Override
	public List<Producto> findAll() {
		em = emf.createEntityManager();
		String jpql = "select p from Producto p";
		TypedQuery<Producto> q = em.createQuery(jpql, Producto.class);
		List<Producto> resu = q.getResultList();
		em.close();
		return resu;
	}

	@Override
	public void save(Producto p) {
		em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(p);
		em.getTransaction().commit();
		em.close();
	}

}
