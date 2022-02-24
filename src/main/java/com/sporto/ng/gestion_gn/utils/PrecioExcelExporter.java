package com.sporto.ng.gestion_gn.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Precio;

public class PrecioExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Precio> listPrecios;
     
    public PrecioExcelExporter(List<Precio> listPrecios) {
        this.listPrecios = listPrecios;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Precios");
        CellStyle style = workbook.createCellStyle();
        CellStyle styleTitulo = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        styleTitulo.setFont(font);
        styleTitulo.setAlignment(HorizontalAlignment.CENTER);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,2)); 
        Row row = sheet.createRow(0);
        createCell(row,0,"Lista "+listPrecios.get(0).getLista().getNombre() + " - Fecha: "+Constants.FORMATO_FECHA.format(new Date()),styleTitulo);
        
        Row titulos = sheet.createRow(1);
         
        createCell(titulos, 0, "Id Producto", style);      
        createCell(titulos, 1, "Descripcion", style);       
        createCell(titulos, 2, "Precio", style);    
         
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        cell.setCellStyle(style);
        
        CellStyle dollarStyle=workbook.createCellStyle();
        DataFormat df = workbook.createDataFormat();
        dollarStyle.setDataFormat(df.getFormat("$#,#0.00"));
        
        
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Long) {
            cell.setCellValue((long) value);
        }else if (value instanceof Double) {
            cell.setCellValue((double) value);
            cell.setCellStyle(dollarStyle);
        }else {
        	  cell.setCellValue(value.toString());
        }
        	
    }
     
    private void writeDataLines() {
        int rowCount = 2;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (Precio unPrecio : listPrecios) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, unPrecio.getProducto().getId(), style);
            createCell(row, columnCount++, unPrecio.getProducto().getDescripcion(), style);
            createCell(row, columnCount++, unPrecio.getPrecio(), style);
             
        }
    }
     
    public void export(File file) throws IOException {
    	
		if (file.exists()) {// El archivo ya existe
			int dialog = JOptionPane.showConfirmDialog(new JDialog(),
					"El archivo ya existe, ¿Quiere sobreescribirlo?", "Solicitud",
					JOptionPane.YES_NO_OPTION);
			if (dialog != JOptionPane.YES_OPTION)
				return;
		} 
		
        writeHeaderLine();
        writeDataLines();
         
        FileOutputStream stream = new FileOutputStream(file);
		workbook.write(stream);
        workbook.close();
         
        stream.close();
        
		int dialog = JOptionPane.showConfirmDialog(new JDialog(),
				"¡Se exportó correctamente! ¿Quiere abrirlo ahora?", "Solicitud",
				JOptionPane.YES_NO_OPTION);
		if (dialog == JOptionPane.YES_OPTION) {
			Runtime.getRuntime().exec("cmd /c start \"\" \"" + file + "\"");
		}

    }
}