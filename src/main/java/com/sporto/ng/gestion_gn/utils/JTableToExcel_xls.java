package com.sporto.ng.gestion_gn.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

/**
 * 
 * Exportar datos de JTable a Excel
 * 
 * @author Administrator
 * 
 */
public class JTableToExcel_xls {

	public static void export(File file, String heading, String inscribe, JTable table) {
		try {
			XSSFWorkbook workbook = null; // crear libro de trabajo

			if (file.exists()) {// El archivo ya existe
				int dialog = JOptionPane.showConfirmDialog(new JDialog(),
						"El archivo ya existe, ¿Quiere sobreescribirlo?", "Solicitud", JOptionPane.YES_NO_OPTION);
				if (dialog != JOptionPane.YES_OPTION)
					return;
			}
			workbook = new XSSFWorkbook();
			// crear hoja de trabajo
			XSSFSheet sheet = workbook.createSheet(heading);

			// Obtenga el número de filas en la tabla (rowNum), el número de columnas
			// (colNum)
			int rowNum = table.getRowCount();
			int colNum = table.getColumnCount() - 2;

			// completa el título principal
			fillHeading(sheet, heading, colNum);

			// completa el nombre de la columna
			fillColumnName(sheet, table, colNum);
//
//			// Completa el pago
//			fillInscribe(sheet, inscribe, rowNum, colNum);
//
//			// completa los datos
//			fillCell(sheet, table, rowNum, colNum);

			// escribe en la hoja de trabajo
			workbook.write(new FileOutputStream(file));
			workbook.close();

			// Exportar cuadro de solicitud de éxito
			int dialog = JOptionPane.showConfirmDialog(new JDialog(),
					"¡Se exportó correctamente! ¿Quiere abrirlo ahora?", "Solicitud", JOptionPane.YES_NO_OPTION);
			if (dialog == JOptionPane.YES_OPTION) {
				Runtime.getRuntime().exec("cmd /c start \"\" \"" + file + "\"");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JDialog(),
					"El archivo se encuentra abierto, cerrar para poder continuar.");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JDialog(), "Sin filtrado");
		}
	}

	/**
	 * Complete el título principal
	 * 
	 * @param sheet
	 * @param heading
	 * @param colNum
	 * @throws WriteException
	 */
	private static void fillHeading(XSSFSheet sheet, String heading, int colNum) throws WriteException {
		WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.RED); // define la fuente

		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 4)); // fusionar celdas
		XSSFRow createRow = sheet.createRow(0);
		createRow.setHeight((short) 600);
		XSSFCell createCell = createRow.createCell(0);
		createCell.setCellValue(heading);

	}

	/**
	 * Complete el nombre de la columna
	 * 
	 * @param sheet
	 * @param table
	 * @param colNum
	 * @throws WriteException
	 */
	private static void fillColumnName(XSSFSheet sheet, JTable table, int colNum) throws WriteException {
		WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK); // define la fuente

		WritableCellFormat format = new WritableCellFormat(font); // define el objeto de formato

		format.setAlignment(Alignment.CENTRE); // Centro de visualización horizontal

		sheet.setColumnWidth(0, 15); // Establecer el ancho de la columna
		sheet.setColumnWidth(1, 20); // Establecer el ancho de la columna
		sheet.setColumnWidth(2, 40); // Establecer el ancho de la columna
		sheet.setColumnWidth(3, 15); // Establecer el ancho de la columna
		sheet.setColumnWidth(4, 20); // Establecer el ancho de la columna

		XSSFRow createRow = sheet.createRow(1);
		for (int col = 0; col < colNum; col++) {

			XSSFCell createCell = createRow.createCell(colNum);
			createCell.setCellValue(table.getModel().getColumnName(col));
		}
	}

	/**
	 * Complete el pago
	 * 
	 * @param sheet
	 * @param inscribe
	 * @param colNum
	 * @param rowNum
	 * @throws WriteException
	 */
	private static void fillInscribe(WritableSheet sheet, String inscribe, int rowNum, int colNum)
			throws WriteException {
		if (inscribe == null || inscribe.length() < 1) {

			inscribe = "Fecha y hora: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		}

		WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK); // define la fuente

		WritableCellFormat format = new WritableCellFormat(font); // define el objeto de formato

		format.setAlignment(Alignment.RIGHT); // Centro de visualización horizontal

		sheet.mergeCells(0, rowNum + 3, colNum - 1, rowNum + 3); // Combinar celdas

		sheet.addCell(new Label(0, rowNum + 3, inscribe, format)); // Complete la hoja de trabajo
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