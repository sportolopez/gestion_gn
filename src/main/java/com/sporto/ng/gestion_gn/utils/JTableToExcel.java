package com.sporto.ng.gestion_gn.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 
 * Exportar datos de JTable a Excel
 * 
 * @author Administrator
 * 
 */
public class JTableToExcel {

	public static void export(File file, String heading, String inscribe, JTable table) {
		try {
			WritableWorkbook workbook = null; // crear libro de trabajo

			if (file.exists()) {// El archivo ya existe
				workbook = Workbook.createWorkbook(file, Workbook.getWorkbook(file));
			} else {// el archivo aún no existe
				workbook = Workbook.createWorkbook(file);
			}
			// crear hoja de trabajo
			WritableSheet sheet = workbook.createSheet(heading, workbook.getNumberOfSheets());

			// Obtenga el número de filas en la tabla (rowNum), el número de columnas
			// (colNum)
			int rowNum = table.getRowCount();
			int colNum = table.getColumnCount();

			// completa el título principal
			fillHeading(sheet, heading, colNum);

			// completa el nombre de la columna
			fillColumnName(sheet, table, colNum);

			// Completa el pago
			fillInscribe(sheet, inscribe, rowNum, colNum);

			// completa los datos
			fillCell(sheet, table, rowNum, colNum);

			// escribe en la hoja de trabajo
			workbook.write();
			workbook.close();

			// Exportar cuadro de solicitud de éxito
			int dialog = JOptionPane.showConfirmDialog(new JDialog(),
					"¡La tabla estadística se exportó correctamente! ¿Está abierta ahora?", "Solicitud",
					JOptionPane.YES_NO_OPTION);
			if (dialog == JOptionPane.YES_OPTION) {
				Runtime.getRuntime().exec("cmd /c start \"\" \"" + file + "\"");
			}

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(new JDialog(), "Cerrar la hoja de trabajo antes de importar datos");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JDialog(), "Sin filtrado");
		}
	}

	/**
           * Complete el título principal
     * @param sheet
     * @param heading
     * @param colNum
     * @throws WriteException
     */
    private static void fillHeading(WritableSheet sheet, String heading, int colNum) throws WriteException {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD,
                                 false, UnderlineStyle.NO_UNDERLINE, Colour.RED); // define la fuente
                 WritableCellFormat format = new WritableCellFormat (font); // crea un objeto formateado
 
                 format.setAlignment (Alignment.CENTRE); // Centro de visualización horizontal
                 format.setVerticalAlignment (VerticalAlignment.CENTRE); // Pantalla central vertical
                 sheet.mergeCells(0, 0, colNum-1, 0); // fusionar celdas
                 sheet.setRowView(0, 600); // Establecer altura de fila
                 sheet.addCell(new Label(0, 0, heading, format)); // Complete la hoja de trabajo
 
    }

	/**
	 * Complete el nombre de la columna
	 * 
	 * @param sheet
	 * @param table
	 * @param colNum
	 * @throws WriteException
	 */
	private static void fillColumnName(WritableSheet sheet, JTable table, int colNum) throws WriteException {
		WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK); // define la fuente

		WritableCellFormat format = new WritableCellFormat(font); // define el objeto de formato

		format.setAlignment(Alignment.CENTRE); // Centro de visualización horizontal

		sheet.setColumnView(0, 15); // Establecer el ancho de la columna

		for (int col = 0; col < colNum; col++) {

			Label colName = new Label(col, 1, table.getModel().getColumnName(col), format);

			sheet.addCell(colName);
		}
	}

	/**
           * Complete el pago
     * @param sheet
     * @param inscribe
     * @param colNum
     * @param rowNum
     * @throws WriteException
     */
    private static void fillInscribe(WritableSheet sheet, String inscribe, int rowNum, int colNum) throws WriteException {
    	if( inscribe == null || inscribe.length() < 1 ) {
    		
    		     inscribe = "Tiempo de exportación:" + new SimpleDateFormat ("aaaa-MM-dd HH: mm: ss"). format (new Date ());
    	}
    	
        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD,
                                 false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK); // define la fuente
 
                 WritableCellFormat format = new WritableCellFormat (font); // define el objeto de formato
 
                 format.setAlignment (Alignment.RIGHT); // Centro de visualización horizontal
 
                 sheet.mergeCells (0, rowNum + 3, colNum-1, rowNum + 3); // Combinar celdas
 
                 sheet.addCell (new Label(0, rowNum + 3, inscribe, format)); // Complete la hoja de trabajo
    }

	/**
	 * Rellene los datos
	 * 
	 * @param sheet
	 * @param talbe
	 * @param rowNum
	 * @param colNum
	 * @throws WriteException
	 */
	private static void fillCell(WritableSheet sheet, JTable table, int rowNum, int colNum) throws WriteException {
		WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK); // define la fuente

		WritableCellFormat format = new WritableCellFormat(font); // define el objeto de formato

		format.setAlignment(Alignment.CENTRE); // Centrar horizontalmente

		for (int i = 0; i < colNum; i++) {// columna

			for (int j = 1; j <= rowNum; j++) {// fila

				String str = table.getValueAt(j - 1, i).toString();

				Label labelN = new Label(i, j + 1, str);

				try {

					sheet.addCell(labelN);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}
}