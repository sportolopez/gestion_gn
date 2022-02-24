package com.sporto.ng.gestion_gn.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.util.Pair;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Denominacion;

public class ArqueoCajaExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheetResumen;

	private List<Pair<Double, String>> gasto = new ArrayList<Pair<Double, String>>();
	private List<Pair<Double, String>> transferencia = new ArrayList<Pair<Double, String>>();
	private List<Pair<Double, String>> efectivo = new ArrayList<Pair<Double, String>>();
	private List<Pair<Double, String>> clientesdeudores = new ArrayList<Pair<Double, String>>();
	private XSSFSheet banco;
	private XSSFSheet caja;
	private XSSFSheet clientes;
	private XSSFSheet gastos;
	private CellStyle styleBorder;
	private CellStyle styleHEader;
	private CellStyle style;
	private Double montoUltimoCierre;
	
	

	public void addGasto(Double valor, String comentario) {
		gasto.add(Pair.of(valor, comentario));
	}
	
	public void addTransferencia(Double valor, String comentario) {
		transferencia.add(Pair.of(valor, comentario));
	}

	public void addEfectivo(Double valor, String comentario) {
		efectivo.add(Pair.of(valor, comentario));
	}

	public ArqueoCajaExporter() {
		workbook = new XSSFWorkbook();

		style = workbook.createCellStyle();
		styleHEader = workbook.createCellStyle();
		styleBorder = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		XSSFFont fontHeader = workbook.createFont();
		font.setFontHeight(14);
		fontHeader.setFontHeight(14);
		fontHeader.setBold(true);
		style.setFont(font);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		styleHEader.setFont(fontHeader);
		styleHEader.setBorderBottom(BorderStyle.MEDIUM);
		styleHEader.setBorderTop(BorderStyle.MEDIUM);
		styleBorder.setFont(font);
		styleBorder.setBorderTop(BorderStyle.MEDIUM);
		
	}

	private void writeHeaderLine() {
		sheetResumen = workbook.createSheet("Resumen");
		banco = workbook.createSheet("Banco");
		caja = workbook.createSheet("Caja");
		clientes = workbook.createSheet("Deudores");
		gastos = workbook.createSheet("Egresos");
		CellStyle style = workbook.createCellStyle();
		CellStyle styleTitulo = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		styleTitulo.setFont(font);
		styleTitulo.setAlignment(HorizontalAlignment.CENTER);
		sheetResumen.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		sheetResumen.addMergedRegion(new CellRangeAddress(10, 10, 0, 6));
		banco.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		caja.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		gastos.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		clientes.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		
		sheetResumen.setColumnWidth(0, 25 * 256);
		banco.setColumnWidth(0, 25 * 256);
		caja.setColumnWidth(0, 25 * 256);
		gastos.setColumnWidth(0, 25 * 256);
		clientes.setColumnWidth(0, 25 * 256);
		sheetResumen.setColumnWidth(1, 25 * 256);
		banco.setColumnWidth(1, 25 * 256);
		caja.setColumnWidth(1, 25 * 256);
		gastos.setColumnWidth(1, 25 * 256);
		clientes.setColumnWidth(1, 25 * 256);
		
		
		createCell(sheetResumen,sheetResumen.createRow(0), 0, "Arqueo " + " - Fecha: " + Constants.FORMATO_FECHA.format(new Date()), styleTitulo);
		createCell(sheetResumen,sheetResumen.createRow(10), 0, "Histórico al  " + " - Fecha: " + Constants.FORMATO_FECHA.format(new Date()), styleTitulo);
		createCell(banco,banco.createRow(0), 0, "Detalle de Transferencias y Depositos " + " - Fecha: " + Constants.FORMATO_FECHA.format(new Date()), styleTitulo);
		createCell(caja,caja.createRow(0), 0, "Detalle de Caja" + " - Fecha: " + Constants.FORMATO_FECHA.format(new Date()), styleTitulo);
		createCell(gastos,gastos.createRow(0), 0, "Detalle de Egresos" + " - Fecha: " + Constants.FORMATO_FECHA.format(new Date()), styleTitulo);
		createCell(clientes,clientes.createRow(0), 0, "Detalle de Deudores" + " - Fecha: " + Constants.FORMATO_FECHA.format(new Date()), styleTitulo);

	}

	private void createCell(XSSFSheet sheet, Row row, int columnCount, Object value, CellStyle style) {
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if (value instanceof Long) {
			cell.setCellValue((long) value);
		}else if (value instanceof Double) {
			cell.setCellValue((double) value);
		} else {
			cell.setCellValue((String) value);
		}

		cell.setCellStyle(style);
	}

	
	
	private void writeDataLines() {
		int rowCount = 2;

		Row row = sheetResumen.createRow(rowCount++);
		Denominacion[] values = Denominacion.values();
		
		for (int i = 0; i < values.length; i++) {
			rowTotalEfectivoDenominacion(row,values[i]);
			row = sheetResumen.createRow(rowCount++);
		}

		createCell(sheetResumen,row, 0, "Total Banco", style);
		Double totalTransferencia = getTotalLista(transferencia);
		createCell(sheetResumen,row, 1, totalTransferencia, style);
		
		row = sheetResumen.createRow(rowCount++);
		createCell(sheetResumen,row, 0, "Total Gastos", style);
		Double totalGastos = getTotalLista(gasto);
		createCell(sheetResumen,row, 1, totalGastos, style);		
		
		row = sheetResumen.createRow(rowCount++);
		createCell(sheetResumen,row, 0, "Saldo Caja", styleBorder);
		createCell(sheetResumen,row, 1, getSaldoCaja(), styleBorder);		

		row = sheetResumen.createRow(11);
		createCell(sheetResumen,row, 0, "Total Deudas de clientes", style);
		createCell(sheetResumen,row, 1, getTotalLista(clientesdeudores), style);	

		cargarMovimientos(banco,transferencia);
		cargarMovimientos(caja,efectivo);
		cargarMovimientos(gastos,gasto);
		cargarMovimientos(clientes,clientesdeudores,"CLIENTE");
	}

	public Double getSaldoCaja() {
		double d = Double.sum(getTotalLista(efectivo),getTotalLista(transferencia));
		double saldoCaja = d -  (double)getTotalLista(gasto);
		return saldoCaja;
	}
	
	public Double getSaldoEfectivo() {
		double d = getTotalLista(efectivo);
		double saldoCaja = d -  (double)getTotalLista(gasto);
		return saldoCaja;
	}

	private void rowTotalEfectivoDenominacion(Row row,Denominacion denominacion) {
		createCell(sheetResumen,row, 0, "Total en Billetes de "+denominacion.getStringValue(), style);
		Double totalEfectivo = getTotalListaDenominacion(denominacion);
		createCell(sheetResumen,row, 1, totalEfectivo, style);
	}

	private void cargarMovimientos(XSSFSheet caja,Collection<Pair<Double, String>> movimientos ) {
		cargarMovimientos(caja, movimientos, "COMENTARIO");
	}
	
	
	private void cargarMovimientos(XSSFSheet caja,Collection<Pair<Double, String>> movimientos, String titulo ) {
		int rowCount;
		Row row;
		rowCount = 1;
		row = caja.createRow(rowCount++);
		createCell(caja, row, 0, titulo, styleHEader);
		createCell(caja, row, 1, "MONTO", styleHEader);
		
		for (Pair<Double, String> unMovimiento : movimientos) {
			row = caja.createRow(rowCount++);
			int columnCount = 0;

			createCell(caja, row, columnCount++, unMovimiento.getSecond(), style);
			createCell(caja, row, columnCount++, unMovimiento.getFirst(), style);

		}
	}
	
	public Double getTotalBanco() {
		return getTotalLista(transferencia);
	}
	
	public Double getTotalGastos() {
		return getTotalLista(gasto);
	}
	
	public Double getTotalEfectivo() {
		return getTotalLista(efectivo);
	}
	
	
	private Double getTotalLista(List<Pair<Double, String>> lista) {
		Double total = (double) 0;
		for (Pair<Double, String> pair : lista) {
			total += pair.getFirst();
		}
		return total;
	}
	
	public  Double getTotalListaDenominacion(Denominacion denominacion) {
		return getTotalListaDenominacion(efectivo, denominacion.getStringValue());
	}

	private Double getTotalListaDenominacion(List<Pair<Double, String>> lista,String denominacion) {
		Double total = (double) 0;
		for (Pair<Double, String> pair : lista) {
			if(denominacion.equals(pair.getSecond()))
				total += pair.getFirst();
		}
		return total;
	}
	
	public void export(File file) throws IOException {

		if (file.exists()) {// El archivo ya existe
			int dialog = JOptionPane.showConfirmDialog(new JDialog(), "El archivo ya existe, ¿Quiere sobreescribirlo?",
					"Solicitud", JOptionPane.YES_NO_OPTION);
			if (dialog != JOptionPane.YES_OPTION)
				return;
		}

		writeHeaderLine();
		writeDataLines();

		FileOutputStream stream = new FileOutputStream(file);
		workbook.write(stream);
		workbook.close();

		stream.close();

		int dialog = JOptionPane.showConfirmDialog(new JDialog(), "¡Se exportó correctamente! ¿Quiere abrirlo ahora?",
				"Solicitud", JOptionPane.YES_NO_OPTION);
		if (dialog == JOptionPane.YES_OPTION) {
			Runtime.getRuntime().exec("cmd /c start \"\" \"" + file + "\"");
		}

	}

	public void addClienteConDeuda(Double saldo, String razonSocial) {
		clientesdeudores.add(Pair.of(saldo, razonSocial));
	}

	public Double getMontoUltimoCierre() {
		return montoUltimoCierre;
	}

	public void setMontoUltimoCierre(Double selectMontoUltimoCierre) {
		this.montoUltimoCierre = selectMontoUltimoCierre;
	}
	
	
}