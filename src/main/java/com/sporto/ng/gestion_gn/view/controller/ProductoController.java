package com.sporto.ng.gestion_gn.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_gn.dao.MovimientoStockDao;
import com.sporto.ng.gestion_gn.dao.ProductoDao;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.model.Producto.ProductoBuilder;
import com.sporto.ng.gestion_gn.model.TipoMovimiento;
import com.sporto.ng.gestion_gn.view.HomeForm;
import com.sporto.ng.gestion_gn.view.MovimientoStock;
import com.sporto.ng.gestion_gn.view.ProductoDialog;
import com.sporto.ng.gestion_gn.view.ProductoPanel;
import com.sporto.ng.gestion_gn.view.model.ButtonColumn;
import com.sporto.ng.gestion_gn.view.model.ProductoTableModel;

@Component
public class ProductoController {

	private ProductoPanel productosPanel;
	private ProductoDialog productoDialog;
	private ProductoDao dao;
	private HomeForm homeForm;
	
	@Autowired
	public ProductoController(ProductoDao dao, HomeForm homeForm, MovimientoStockDao movimientoDao) {
		super();
		this.dao = dao;
		this.productoDialog = new ProductoDialog();
		this.homeForm = homeForm;
		this.productosPanel = homeForm.getProductosPanel();
		productoDialog.getBtnGuardar().addActionListener(e -> saveProducto());

		AbstractAction actionBorrar = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				int idProducto = Integer
						.valueOf(((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0).toString());
				int input = JOptionPane.showConfirmDialog(homeForm,
						"¿Confirmar el borrado del producto " + idProducto + "?", "Eliminar producto",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (input == 0) {
					try {
						dao.deleteById(idProducto);
						((DefaultTableModel) table.getModel()).removeRow(modelRow);
					} catch (DataIntegrityViolationException | ConstraintViolationException e1) {
						JOptionPane.showMessageDialog(homeForm,
							   "No se puede eliminar el producto ya que tiene movimientos de stock o pedidos realizados",
							    "Error",
							    JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		};

		productosPanel.getBtnNuevoProducto().addActionListener(e -> nuevoProducto());
		productosPanel.getBtnImportar().addActionListener(e -> importarExcel());

		productosPanel.getTextFieldBuscadorProductos().addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				productosPanel.filtrar();
			}
		});

		
		int columnCount = 5;


		Action botonDelete = null;
		Action botonEditar = null;

		
		new ButtonColumn(productosPanel.getTableProductos(), botonEditar, columnCount).setAction(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				Integer idProducto = (Integer) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
				editarProducto(idProducto);

			}
		});
		;
		new ButtonColumn(productosPanel.getTableProductos(), botonDelete, columnCount + 1).setAction(actionBorrar);

		productosPanel.getBtnIngresoStock().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MovimientoStock movimientoStock = new MovimientoStock(dao.findAll(),movimientoDao,dao,TipoMovimiento.INGRESO);
				movimientoStock.setVisible(true);
				cargarListaInicial();
			}
		});
		productosPanel.getBtnEgresoStock().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MovimientoStock movimientoStock = new MovimientoStock(dao.findAll(),movimientoDao,dao,TipoMovimiento.EGRESO);
				movimientoStock.setVisible(true);
				cargarListaInicial();
			}
		});
		cargarListaInicial();
	}

	private void nuevoProducto() {
		productoDialog.limpiarCampos();;
		productoDialog.setVisible(true);
	}

	public void cargarListaInicial() {
		ProductoTableModel prodctoTableModel = productosPanel.getProductoTableModel();
		prodctoTableModel.setRowCount(0);
		Iterable<Producto> findAll = dao.findAll();
		for (Producto producto : findAll) {

			prodctoTableModel.addProducto(producto);
		}

	}


	public void editarProducto(Integer idProducto) {
		Optional<Producto> findById = dao.findById(idProducto);
		productoDialog.cargarCampos(findById.get());
		productoDialog.setVisible(true);

	}

	private void saveProducto() {
		if (productoDialog.validar()) {
			dao.save(productoDialog.getProducto());
			productoDialog.setVisible(false);
			cargarListaInicial();
		}
	}

	public void importarExcel() {
		JFileChooser fileChooser = new JFileChooser();
		int option = fileChooser.showOpenDialog(homeForm);
		if (option == JFileChooser.APPROVE_OPTION) {
			try {
				int procesarExcel = procesarExcelProductos(fileChooser.getSelectedFile());
				JOptionPane.showMessageDialog(homeForm, "Se procesaron " + procesarExcel + " registros");
				cargarListaInicial();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private int procesarExcelProductos(File excel) throws IOException {
		/** Diseño del excel:
		 * CODIGO	CATEGORIA	DESCRIPCION	COSTO	VENCIMIENTO
		 */
		
		FileInputStream inputStream = new FileInputStream(excel);

		// TODO: Validar Excel

		Workbook workbook = new XSSFWorkbook(inputStream);
		org.apache.poi.ss.usermodel.Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		iterator.next();


		Row filaEncabezados = iterator.next();
		int contador = 0;
		while (iterator.hasNext()) {
			Row rowProducto = iterator.next();
			ProductoBuilder builder = Producto.builder();
			builder.activo(true);
			builder.id((int) rowProducto.getCell(0).getNumericCellValue());
			builder.categoria(rowProducto.getCell(1).getStringCellValue());
			builder.descripcion(rowProducto.getCell(2).getStringCellValue());
			Cell cellCosto = rowProducto.getCell(3);
			if(cellCosto != null)
				builder.costo(cellCosto.getNumericCellValue());

			builder.stock(0);
			Cell cellFecha = rowProducto.getCell(4);
			if (cellFecha != null) {
				builder.fechaVencimiento(cellFecha.getDateCellValue());
			}
			Producto build = builder.build();
			dao.save(build);
			contador++;
		}

		workbook.close();
		inputStream.close();
		return contador;
	}

//	private int procesarExcel(File excel) throws IOException {
//		FileInputStream inputStream = new FileInputStream(excel);
//
//		// TODO: Validar Excel
//
//		Workbook workbook = new XSSFWorkbook(inputStream);
//		org.apache.poi.ss.usermodel.Sheet firstSheet = workbook.getSheetAt(0);
//		Iterator<Row> iterator = firstSheet.iterator();
//		iterator.next();
//
//		Map<Integer, String> listaPecios = new HashMap<Integer, String>();
//
//		Row filaEncabezados = iterator.next();
//		for (Cell cell : filaEncabezados) {
//			if (cell.getStringCellValue().contains("PRECIO")) {
//				listaPecios.put(cell.getColumnIndex(), cell.getStringCellValue().substring("PRECIO_".length()));
//			}
//		}
//		int contador = 0;
//		while (iterator.hasNext()) {
//			Row rowProducto = iterator.next();
//			ProductoBuilder builder = Producto.builder();
//			builder.activo(true);
//			builder.id((int) rowProducto.getCell(0).getNumericCellValue());
//			builder.categoria(rowProducto.getCell(1).getStringCellValue());
//			builder.descripcion(rowProducto.getCell(2).getStringCellValue());
//			Cell cell = rowProducto.getCell(3);
//			if (cell != null) {
//				double numericCellValue = cell.getNumericCellValue();
//				builder.stock((int) numericCellValue);
//			} else {
//				builder.stock(0);
//			}
//			Cell cellFecha = rowProducto.getCell(4);
//			if (cellFecha != null) {
//				builder.fechaVencimiento(cellFecha.getDateCellValue());
//			}
//			Map<String, Double> preciosMap = new HashMap<String, Double>();
//
//			for (int i = 5; i < (5 + listaPecios.size()); ++i) {
//				Cell cellPrecio = rowProducto.getCell(i);
//				if (cellPrecio != null && !Double.isNaN(cellPrecio.getNumericCellValue())) {
//					preciosMap.put(listaPecios.get(i), cellPrecio.getNumericCellValue());
//				} else {
//					preciosMap.put(listaPecios.get(i), (double) 0);
//				}
//			}
//
//			builder.precios(preciosMap);
//
//			Producto build = builder.build();
//			dao.save(build);
//			contador++;
//		}
//
//		workbook.close();
//		inputStream.close();
//		return contador;
//	}

}