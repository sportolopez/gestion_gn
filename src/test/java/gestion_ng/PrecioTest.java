package gestion_ng;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sporto.ng.gestion_gn.GestionNG;
import com.sporto.ng.gestion_gn.dao.ListaDao;
import com.sporto.ng.gestion_gn.dao.PrecioDao;
import com.sporto.ng.gestion_gn.dao.ProductoDao;
import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.model.Precio;
import com.sporto.ng.gestion_gn.model.Producto;


@SpringBootTest(classes = GestionNG.class)
public class PrecioTest {
	@Autowired
	private PrecioDao precioDao;
	@Autowired
	private ProductoDao productoDao;
	@Autowired
	private ListaDao listaDao;
	@Test
	public void testInsert() {
		Producto unProducto = productoDao.findAll().get(0);
		Lista lista = listaDao.findAll().get(0);
		Precio entity = Precio.builder().lista(lista).producto(unProducto).precio((long) 150).build();
		precioDao.save(entity);
		assertEquals(1, precioDao.count());
	}

}
