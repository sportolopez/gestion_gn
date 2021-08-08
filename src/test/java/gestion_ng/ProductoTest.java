package gestion_ng;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sporto.ng.gestion_ng.HomeForm;
import com.sporto.ng.gestion_ng.dao.ProductoDao;
import com.sporto.ng.gestion_ng.model.Producto;

@SpringBootTest(classes = HomeForm.class)
class ProductoTest {

	@Autowired
	private ProductoDao productoDao;
	
	@Test
	void test() {
		Producto unProducto = Producto.builder().descripcion("prueba").activo(true).fechaVencimiento(Calendar.getInstance().getTime()).idProducto(123456789).build() ;
		productoDao.save(unProducto );
		Iterable<Producto> findAll = productoDao.findAll();
		for (Producto producto : findAll) {
			System.out.println(producto);
		}
		assertEquals(1, productoDao.count());
	}

}
