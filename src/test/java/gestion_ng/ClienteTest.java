package gestion_ng;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sporto.ng.gestion_gn.GestionNG;
import com.sporto.ng.gestion_gn.dao.ClienteDao;
import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.Lista;

@SpringBootTest(classes = GestionNG.class)
public class ClienteTest {

	@Autowired
	ClienteDao dao;
	
	@Test
	public void testInsert() {
		Lista build = Lista.builder().nombre("VIP").build();
		Cliente build2 = Cliente.builder().cuit("2323232").razonSocial("asdasdad").listaPrecio(build).build();
		dao.save(build2);
	}
}
