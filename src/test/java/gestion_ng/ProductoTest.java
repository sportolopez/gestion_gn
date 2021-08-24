package gestion_ng;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sporto.ng.gestion_ng.GestionNG;
import com.sporto.ng.gestion_ng.dao.ProductoDao;
import com.sporto.ng.gestion_ng.model.Lista;
import com.sporto.ng.gestion_ng.model.Producto;

@SpringBootTest(classes = GestionNG.class)
class ProductoTest {

	@Autowired
	private ProductoDao productoDao;
	
	@Test
	void test() {
		Lista build = Lista.builder().nombre("Gremio").build();
		Map<String, Double> precios = new HashMap<String, Double>();
		precios.put("CABA", Double.valueOf(200.22));
		Producto unProducto = Producto.builder()
				.descripcion("prueba")
				.activo(true)
				.fechaVencimiento(Calendar.getInstance().getTime())
				.id(1234111).precios(precios).build() ;
		
		productoDao.save(unProducto );
		Iterable<Producto> findAll = productoDao.findAll();
		for (Producto producto : findAll) {
			System.out.println(producto);
			System.out.println(producto.getPrecios().get("CABA"));
		}
		assertEquals(1, productoDao.count());
	}

}
