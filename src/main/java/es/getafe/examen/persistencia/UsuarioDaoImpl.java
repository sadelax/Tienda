package es.getafe.examen.persistencia;

import static es.getafe.examen.persistencia.Emf.getEmf;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import es.getafe.examen.modelo.Usuario;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

	private EntityManager em;
	
	@Override
	public Usuario findById(Integer id) {
		em = getEmf().createEntityManager();
		Usuario buscado = em.find(Usuario.class, id);
		em.close();
		return buscado;
	}

	@Override
	public Usuario valida(String usuario, String password) {
		Usuario buscado = null;
		em = getEmf().createEntityManager();
		String jpql = "select u from Usuario u where u.usuario = :usr";
		TypedQuery<Usuario> q = em.createQuery(jpql, Usuario.class);
		q.setParameter("usr", usuario);
		try {
			buscado = q.getSingleResult();
			if(!BCrypt.verifyer().verify(password.toCharArray(), buscado.getPassword()).verified) {
				buscado = null;
			}
		} catch (NoResultException e) {
			buscado = null;
		}
		em.close();
		return buscado;
	}
	
	@Override
	public boolean save(Usuario usuario) {
		boolean resu = false;
		
		char[] clave = BCrypt.withDefaults().hashToChar(12, usuario.getPassword().toCharArray());
		String pwdEnc = String.valueOf(clave);
		usuario.setPassword(pwdEnc);
		
		em = getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(usuario);
			em.getTransaction().commit();
			resu = true;
		} catch (Exception e) {
//			e.printStackTrace();
		}
		em.close();
		return resu;
	}

	@Override
	public Set<Usuario> findAll() {
		String jpql = "select u from Usuario u";
		em = Emf.getEmf().createEntityManager();
		TypedQuery<Usuario> q = em.createQuery(jpql, Usuario.class);
		Set<Usuario> resu = new TreeSet<>(q.getResultList());
		em.close();
		return resu;
	}

}
