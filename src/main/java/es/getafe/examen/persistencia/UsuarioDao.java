package es.getafe.examen.persistencia;

import java.util.Set;

import es.getafe.examen.modelo.Usuario;

public interface UsuarioDao {

	Usuario findById(Integer id);
	
	// Si no son validas, retorna null
	Usuario valida(String usuario, String password);
	
	boolean save(Usuario usuario);
	
	Set<Usuario> findAll();
}
