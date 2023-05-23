package es.getafe.examen.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Usuario implements Serializable, Comparable<Usuario> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private int idUsuario;

	@Column(name = "name")
	private String nombre;

	@Column(name = "username")
	private String usuario;
	private String password;
	private String email;
	private boolean enabled;
	
	public Usuario() {
	}
	
	public Usuario(String nombre, String usuario, String password, String email) {
		this.nombre = nombre;
		this.usuario = usuario;
		this.password = password;
		this.email = email;
		this.enabled = true;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		if(idUsuario == 0)
			return Objects.hash(usuario);
		else
			return Objects.hash(idUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if(idUsuario == 0 || other.idUsuario == 0)
			return usuario.equals(other.usuario);
		else
			return idUsuario == other.idUsuario;
	}

	@Override
	public int compareTo(Usuario o) {
		return this.idUsuario - o.idUsuario;
	}
}
