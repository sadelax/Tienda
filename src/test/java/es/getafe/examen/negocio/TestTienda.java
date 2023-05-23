package es.getafe.examen.negocio;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.getafe.examen.modelo.Fabricante;
import es.getafe.examen.modelo.Producto;
import es.getafe.examen.persistencia.FuenteDatos;

class TestTienda {

	private static Tienda tienda;
	static Connection con;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		tienda = new TiendaImpl();
	}
	
	@Test
	void testSave() throws Exception{
		Fabricante f = new Fabricante();
		f.setFabricante("Fabricante Nuevo Test");
		int cantAnterior = cantidadFabricantes();
		tienda.addFabricante(f);
		assertEquals(cantidadFabricantes(), cantAnterior+1);
		con = FuenteDatos.getConnection();
		String sql = "delete from fabricantes where fabricante = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "Fabricante Nuevo Test");
		int borrado = ps.executeUpdate();
		assertEquals(1, borrado);
		con.close();
	}
	
	@Test
	void testGetProductos() throws Exception{
		Set<Producto> productos = tienda.getProductos();
		assertNotNull(productos);
		String sql = 
			"""
			SELECT id_producto FROM productos 
			order by producto;
			""";

		int[] idEsperados = todosId(sql);
		int[] idActuales = new int[productos.size()];
		int i = 0;
		for (Producto p : productos) {
			idActuales[i++] = p.getIdProducto();
		}
		assertArrayEquals(idEsperados, idActuales);
	}
	
	@Test
	void testGetProductosDescripcion() throws Exception{
		Set<Producto> productos = tienda.getProductos("disco");
		assertNotNull(productos);
		String sql = 
			"""
			SELECT id_producto FROM productos 
			where producto like '%disco%' 
			""";

		int[] idEsperados = todosId(sql);
		int[] idActuales = new int[productos.size()];
		int i = 0;
		for (Producto p : productos) {
			idActuales[i++] = p.getIdProducto();
		}
		assertArrayEquals(idEsperados, idActuales);
	}
	
	@Test
	void testGetProductosDescripcionNoExiste() throws Exception{
		Set<Producto> productos = tienda.getProductos("algo que no existe");
		assertNull(productos);
	}

	@Test
	void testGetMediaPrecioProductosByFabricante() throws Exception{
		double mediaActual = tienda.getMediaPrecioProductosByFabricante(6);
		String sql = 
			"""
			select avg(precio) from productos
			where fk_fabricante = 6
			""";
		double mediaEsperada = mediaEsperada(sql);
		assertEquals(mediaEsperada, mediaActual);
	}

	@Test
	void testGetMediaPrecioProductosByFabricanteNoExiste() throws Exception{
		double mediaActual = tienda.getMediaPrecioProductosByFabricante(666);
		assertEquals(0, mediaActual);
	}
	
	private int cantidadFabricantes() throws Exception {
		String sql = 
				"""
				select id_fabricante from fabricantes
				""";
		return todosId(sql).length;
	}
	
	private int[] todosId(String sql) throws Exception{
		con = FuenteDatos.getConnection();
		PreparedStatement ps = con.prepareStatement(
				sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = ps.executeQuery();

		int cont = 0;
		while (rs.next()) {
		    cont++;
		}
		rs.beforeFirst();

		int[] resu = new int[cont];
		int i = 0;
		while(rs.next()) {
			resu[i++] = rs.getInt(1);
		}
		con.close();
		return resu;
	}
	
	private double mediaEsperada(String sql) throws Exception{
		con = FuenteDatos.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		double resu = rs.getDouble(1);
		
		con.close();
		return resu;
	}


}
