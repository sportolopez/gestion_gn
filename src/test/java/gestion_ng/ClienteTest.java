package gestion_ng;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sporto.ng.gestion_gn.GestionNG;
import com.sporto.ng.gestion_gn.dao.ClienteDao;
import com.sporto.ng.gestion_gn.dao.GastoCajaDao;
import com.sporto.ng.gestion_gn.dao.MovimientoCajaDao;
import com.sporto.ng.gestion_gn.dao.PrecioDao;
import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.GastoCaja;
import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.model.MovimientoCaja;
import com.sporto.ng.gestion_gn.model.Precio;
import com.sporto.ng.gestion_gn.utils.ExcelUtils;
import com.sporto.ng.gestion_gn.utils.PrecioExcelExporter;

@SpringBootTest(classes = GestionNG.class)
public class ClienteTest {

	@Autowired
	ClienteDao dao;
	@Autowired
	PrecioDao precioDao;
	@Autowired
	MovimientoCajaDao movimientoCajaDao;
	@Autowired
	GastoCajaDao gastoCajaDao;

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
	
	@Test
	public void exportArquo() throws IOException {
		Calendar calendar = Calendar.getInstance();
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println("DIA "+dia);
		int mes = calendar.get(Calendar.MONTH);
		System.out.println("mes "+mes);
		int year = calendar.get(Calendar.YEAR);
		System.out.println("year "+year);
		List<MovimientoCaja> ingresos = movimientoCajaDao.findByFecha(dia,mes+1,year);
		List<GastoCaja> gastos = gastoCajaDao.findByFecha(dia,mes+1,year);
		System.out.println("Cantidad ingresos:"+ingresos.size());
		System.out.println("Cantidad gastos:"+gastos.size());
		for (MovimientoCaja movimientoCajaDao : ingresos) {
			System.out.println(movimientoCajaDao);
		}
		
		for (GastoCaja gastoCaja : gastos) {
			System.out.println(gastoCaja);
		}
		
//		ArqueoCajaExporter precioExcelExporter = new ArqueoCajaExporter();
//		
//		precioExcelExporter.addEfectivo((double) 100, "Efectivo");
//		precioExcelExporter.addEfectivo((double) 100, "Efectivo");
//		precioExcelExporter.addEfectivo((double) 100, "Efectivo");
//		precioExcelExporter.addGasto((double) 5400, "Nafta");
//		precioExcelExporter.addTransferencia((double) 100, "Efectivo");
//		precioExcelExporter.addTransferencia((double) 300.50, "Transfencia 23242342");
//		precioExcelExporter.addTransferencia((double) 21422, "Comprobante 232");
//		
//		precioExcelExporter.export(new File("C:\\Users\\sebap\\OneDrive\\Escritorio\\precios.xlsx"));
//		
		
	}
}
