package es.getafe.examen.persistencia;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class Emf {
	private static EntityManagerFactory emf;
	
	private Emf(){};
	
	public static EntityManagerFactory getEmf(){
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("tienda");
		}
		return emf;
	}
}
