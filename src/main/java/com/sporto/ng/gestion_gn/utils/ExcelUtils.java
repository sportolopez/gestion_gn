package com.sporto.ng.gestion_gn.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.model.Precio;
import com.sporto.ng.gestion_gn.model.Precio.PrecioBuilder;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.model.Producto.ProductoBuilder;

public class ExcelUtils {

	public static List<Producto> procesarExcelProductos(File excel) throws IOException {
		/**
		 * Diseño del excel: CODIGO CATEGORIA DESCRIPCION COSTO VENCIMIENTO
		 */

		FileInputStream inputStream = new FileInputStream(excel);

		// TODO: Validar Excel

		Workbook workbook = new XSSFWorkbook(inputStream);
		org.apache.poi.ss.usermodel.Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		iterator.next();
		iterator.next();
		List<Producto> productos = new ArrayList<Producto>();
		while (iterator.hasNext()) {
			Row rowProducto = iterator.next();
			ProductoBuilder builder = Producto.builder();
			builder.activo(true);
			builder.id((int) rowProducto.getCell(0).getNumericCellValue());
			builder.categoria(rowProducto.getCell(1).getStringCellValue());
			builder.descripcion(rowProducto.getCell(2).getStringCellValue());
			Cell cellCosto = rowProducto.getCell(3);
			if (cellCosto != null)
				builder.costo(cellCosto.getNumericCellValue());

			Cell cellFecha = rowProducto.getCell(4);
			if (cellFecha != null) {
				builder.fechaVencimiento(cellFecha.getDateCellValue());
			}
			Producto build = builder.build();
			productos.add(build);
		}

		workbook.close();
		inputStream.close();
		return productos;
	}

	public static List<Precio> procesarExcelPrecios(File excel) {

		
		/**
		 * CODIGO DESCRIPCION AMBA GENERAL INTERIOR VIP
		 */
		int ROW_INICIAL = 2;
		int ROW_LISTA = 2;
		int COL_CODIGO = 0;
		int COL_DESCRIPCION = 1;
		int COL_INITLISTA = 2;

		int COL_LASTLISTA = 5;

		// TODO: Validar Excel

		Workbook workbook;
		FileInputStream inputStream;
		List<Precio> listaPecios = new ArrayList<Precio>();
		try {
			inputStream = new FileInputStream(excel);
			workbook = new XSSFWorkbook(inputStream);

			org.apache.poi.ss.usermodel.Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
			iterator.next();
			iterator.next();
			Row rowListas = iterator.next();
			while (iterator.hasNext()) {
				Row row = iterator.next();

				ProductoBuilder builder = Producto.builder();
				builder.id((int) row.getCell(COL_CODIGO).getNumericCellValue());
				builder.descripcion(row.getCell(COL_DESCRIPCION).getStringCellValue());
				Producto unProducto = builder.build();
				PrecioBuilder unPrecio = Precio.builder();
				unPrecio.producto(unProducto);

				for (int i = COL_INITLISTA; i <= COL_LASTLISTA; i++) {

					Cell cell = row.getCell(i);
					unPrecio.lista(Lista.builder().nombre(rowListas.getCell(i).getStringCellValue()).build());
					if (cell != null) {
						double numericCellValue = cell.getNumericCellValue();
						unPrecio.precio((long) numericCellValue);
					} else {
						unPrecio.precio((long) 0);
					}
					listaPecios.add(unPrecio.build());
				}

			}

			workbook.close();
			inputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return listaPecios;
	}

	public static void exportarListaPrecios(Collection<Lista> lista) {
		
	}



}
