package gestion_ng;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sporto.ng.gestion_ng.GestionNG;
import com.sporto.ng.gestion_ng.dao.ProductoDao;
import com.sporto.ng.gestion_ng.model.Lista;
import com.sporto.ng.gestion_ng.model.Producto;
import com.sporto.ng.gestion_ng.model.Producto.ProductoBuilder;

@SpringBootTest(classes = GestionNG.class)
class ProductoTest {

	@Autowired
	private ProductoDao productoDao;

	@Test
	void test() {
		Lista build = Lista.builder().nombre("Gremio").build();
		Map<String, Double> precios = new HashMap<String, Double>();
		precios.put("CABA", Double.valueOf(200.22));
		Producto unProducto = Producto.builder().descripcion("prueba").activo(true)
				.fechaVencimiento(Calendar.getInstance().getTime()).id(1234111).precios(precios).build();

		productoDao.save(unProducto);
		Iterable<Producto> findAll = productoDao.findAll();
		for (Producto producto : findAll) {
			System.out.println(producto);
			System.out.println(producto.getPrecios().get("CABA"));
		}
		assertEquals(1, productoDao.count());
	}

	@Test
	void importar() throws IOException {
		
		
		
        URL excelFilePath = getClass().getClassLoader().getResource("IMPORTAR.xlsx");
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath.getFile()));
         
        Workbook workbook = new XSSFWorkbook(inputStream);
        org.apache.poi.ss.usermodel.Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        iterator.next();
        
        Map<Integer,String> listaPecios = new HashMap<Integer,String>();
        
        Row filaEncabezados = iterator.next();
        for (Cell cell : filaEncabezados) {
        	if(cell.getStringCellValue().contains("PRECIO")) {
        		listaPecios.put(cell.getColumnIndex(), cell.getStringCellValue().substring("PRECIO_".length()));
        	}
		}
        while (iterator.hasNext()) {
        	System.out.println("Entra en el rowset");
        	Row rowProducto = iterator.next();
            ProductoBuilder builder = Producto.builder();
            builder.id((int) rowProducto.getCell(0).getNumericCellValue());
            builder.activo(true);
            builder.descripcion( rowProducto.getCell(1).getStringCellValue());
            builder.stock((int) rowProducto.getCell(2).getNumericCellValue());
            builder.fechaVencimiento( rowProducto.getCell(3).getDateCellValue());
            
            Map<String, Double> preciosMap = new HashMap<String, Double>();
            
            if(!Double.isNaN(rowProducto.getCell(4).getNumericCellValue())){
            	preciosMap.put(listaPecios.get(4), rowProducto.getCell(4).getNumericCellValue());
            }
            
			builder.precios(preciosMap);
            
            Producto build = builder.build();
            System.out.println(build);
            productoDao.save(build);
            
        }
         
        workbook.close();
        inputStream.close();
    }
}
