package gestion_ng;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sporto.ng.gestion_ng.HomeForm;
import com.sporto.ng.gestion_ng.dao.ProductoDao;
import com.sporto.ng.gestion_ng.model.Lista;
import com.sporto.ng.gestion_ng.model.Producto;
import com.sporto.ng.gestion_ng.model.Precio;

@SpringBootTest(classes = HomeForm.class)
class ProductoTest {

	@Autowired
	private ProductoDao productoDao;
	
	@Test
	void test() {
//		Lista build = Lista.builder().nombre("Gremio").build();
//		Precio build2 = Precio.builder().precio(new Long(1234)).lista(build).build();
//		Collection<Precio> precios;
//		Producto unProducto = Producto.builder()
//				.descripcion("prueba")
//				.activo(true)
//				.fechaVencimiento(Calendar.getInstance().getTime())
//				.idProducto(123456789)
//				.precios(precios).build() ;
//		productoDao.save(unProducto );
//		Iterable<Producto> findAll = productoDao.findAll();
//		for (Producto producto : findAll) {
//			System.out.println(producto);
//		}
//		assertEquals(1, productoDao.count());
	}

}
