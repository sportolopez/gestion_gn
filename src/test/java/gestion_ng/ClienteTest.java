package gestion_ng;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sporto.ng.gestion_gn.GestionNG;
import com.sporto.ng.gestion_gn.dao.ClienteDao;
import com.sporto.ng.gestion_gn.dao.PrecioDao;
import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.model.Precio;
import com.sporto.ng.gestion_gn.utils.ExcelUtils;
import com.sporto.ng.gestion_gn.utils.PrecioExcelExporter;

@SpringBootTest(classes = GestionNG.class)
public class ClienteTest {

	@Autowired
	ClienteDao dao;
	@Autowired
	PrecioDao precioDao;

	@Test
	public void testInsert() {
		Lista build = Lista.builder().nombre("VIP").build();
		Cliente build2 = Cliente.builder().cuit("2323232").razonSocial("asdasdad").listaPrecio(build).build();
		dao.save(build2);
	}

	@Test
	public void importExcel() throws IOException {

		URL excelFilePath = getClass().getClassLoader().getResource("IMPORTAR_PRECIOS.xlsx");
		
		List<Precio> procesarExcelPrecios = ExcelUtils.procesarExcelPrecios(new File(excelFilePath.getFile()));
		
		precioDao.saveAll(procesarExcelPrecios);

	}
	@Test
	public void exportExcel() throws IOException {
		
		List<Precio> findByLista = precioDao.findByLista(Lista.builder().nombre("AMBA").build());
		
		PrecioExcelExporter precioExcelExporter = new PrecioExcelExporter(findByLista);
		
		precioExcelExporter.export(new File("C:\\Users\\sebap\\OneDrive\\Escritorio\\precios.xlsx"));
	}
}
