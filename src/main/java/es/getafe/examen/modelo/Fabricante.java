package es.getafe.examen.modelo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "fabricantes")
public class Fabricante implements Serializable, Comparable<Fabricante> {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_fabricante")
	private int idFabricante;
	@Expose
	private String fabricante;
	
	@OneToMany(mappedBy = "fabricante")
	private Set<Producto> productos;
	
	public Fabricante() {
	}
	
	public Fabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	
	public int getIdFabricante() {
		return idFabricante;
	}

	public void setIdFabricante(int idFabricante) {
		this.idFabricante = idFabricante;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public Set<Producto> getProductos() {
		return productos;
	}

	public void setProductos(Set<Producto> productos) {
		this.productos = productos;
	}
	
	@Override
	public int hashCode() {
		return idFabricante;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fabricante other = (Fabricante) obj;
		return idFabricante == other.idFabricante;
	}

	@Override
	public int compareTo(Fabricante o) {
		return this.idFabricante - o.idFabricante;
	}
}
