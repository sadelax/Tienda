package es.getafe.examen.persistencia;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import es.getafe.examen.modelo.Fabricante;

public class FabricanteDaoImpl implements FabricanteDao {

	private EntityManagerFactory emf;
	private EntityManager em;
	
	public FabricanteDaoImpl() {
		emf = Emf.getEmf();
	}
	
	@Override
	public void save(Fabricante fabricante) {
		em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(fabricante);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public Fabricante findByIdLazy(int idFabricante) {
		em = emf.createEntityManager();
		Fabricante buscado = em.find(Fabricante.class, idFabricante);
		em.close();
		return buscado;
	}

	@Override
	public Fabricante findById(int idFabricante) {
		em = emf.createEntityManager();
		String jpql = 
		"select f from Fabricante f left join fetch f.productos where f.idFabricante = :id";
		TypedQuery<Fabricante> resu = em.createQuery(jpql, Fabricante.class);
		resu.setParameter("id", idFabricante);
		Fabricante buscado = null;
		try {
			buscado = resu.getSingleResult();
		} catch (NoResultException e) {
//			buscado = null;
		}
		em.close();
		return buscado;
	}

	@Override
	public Set<Fabricante> findOnlyActive() {
		em = emf.createEntityManager();
		String jpql = "select distinct f from Fabricante f join f.productos";
		TypedQuery<Fabricante> q = em.createQuery(jpql, Fabricante.class);
		Set<Fabricante> resu = new TreeSet<>(q.getResultList());
		em.close();
		return resu;
	}

	@Override
	public Set<Fabricante> findAll() {
		em = emf.createEntityManager();
		String jpql = "select f from Fabricante f";
		TypedQuery<Fabricante> q = em.createQuery(jpql, Fabricante.class);
		Set<Fabricante> resu = new TreeSet<>(q.getResultList());
		em.close();
		return resu;
	}
}
