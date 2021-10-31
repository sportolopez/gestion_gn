package gestion_ng;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
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

import com.sporto.ng.gestion_gn.GestionNG;
import com.sporto.ng.gestion_gn.dao.ProductoDao;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.model.Producto.ProductoBuilder;

@SpringBootTest(classes = GestionNG.class)
class ProductoTest {

	@Autowired
	private ProductoDao productoDao;

	@Test
	void test() {
		productoDao.findAll();
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
            builder.fechaVencimiento( rowProducto.getCell(3).getDateCellValue());
            
            Map<String, Double> preciosMap = new HashMap<String, Double>();
            
            if(!Double.isNaN(rowProducto.getCell(4).getNumericCellValue())){
            	preciosMap.put(listaPecios.get(4), rowProducto.getCell(4).getNumericCellValue());
            }
            
			//builder.precios(preciosMap);
            
            Producto build = builder.build();
            System.out.println(build);
            productoDao.save(build);
            
        }
         
        workbook.close();
        inputStream.close();
    }
	
	@Test
	void testCalculoDescuento() {
		
		String descuento = "5 %";
		
		double precio = 100;
		
		int descuentoParsed = Integer.parseInt(descuento.substring(0, 1));
		
		System.out.println(precio - precio*descuentoParsed/100);
		
		
	}

}
