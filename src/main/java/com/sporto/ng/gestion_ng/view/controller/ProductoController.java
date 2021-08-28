package com.sporto.ng.gestion_ng.view.controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_ng.dao.ProductoDao;
import com.sporto.ng.gestion_ng.model.Producto;
import com.sporto.ng.gestion_ng.model.Producto.ProductoBuilder;
import com.sporto.ng.gestion_ng.view.HomeForm;
import com.sporto.ng.gestion_ng.view.ProductoDialog;
import com.sporto.ng.gestion_ng.view.ProductoPanel;
import com.sporto.ng.gestion_ng.view.model.ProductoTableModel;

@Component
public class ProductoController {

	private ProductoPanel productosPanel;
	private ProductoDialog productoDialog;
	private ProductoDao dao;
	private HomeForm homeForm;

	@Autowired
	public ProductoController(ProductoDao dao, HomeForm homeForm) {
		super();
		this.dao = dao;
		this.productoDialog = new ProductoDialog();
		this.homeForm = homeForm;
		this.productosPanel = homeForm.getProductosPanel();
		productoDialog.getBtnGuardar().addActionListener(e -> saveProducto());
		productosPanel.getButtonEditar().setAction(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				Integer idProducto = (Integer) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
				editarProducto(idProducto);

			}
		});
		productosPanel.getBtnNuevoProducto().addActionListener(e -> nuevoProducto());
		productoDialog.getBtnAgregarPrecio().addActionListener(e -> nuevoPrecio());
		productosPanel.getBtnImportar().addActionListener(e -> importarExcel());
		cargarListaInicial();
	}

	private Object nuevoPrecio() {
		// TODO Auto-generated method stub
		return null;
	}

	private void nuevoProducto() {
		productoDialog.limpiarCampos();
		productoDialog.setVisible(true);
	}

	public void cargarListaInicial() {
		ProductoTableModel prodctoTableModel = productosPanel.getProductoTableModel();
		prodctoTableModel.setRowCount(0);
		Iterable<Producto> findAll = dao.findAll();
		for (Producto producto : findAll) {

			prodctoTableModel.addRow(new Object[] { producto.getId(), producto.getDescripcion(), producto.getStock(),
					"Editar", "Eliminar" });
		}

	}

	public void editarProducto(Integer idProducto) {
		Optional<Producto> findById = dao.findById(idProducto);
		productoDialog.cargarCampos(findById.get());
		productoDialog.setVisible(true);

	}

	private void saveProducto() {
		dao.save(productoDialog.getProducto());
		productoDialog.setVisible(false);
		cargarListaInicial();
	}

	public void importarExcel() {
		JFileChooser fileChooser = new JFileChooser();
		int option = fileChooser.showOpenDialog(homeForm);
		if (option == JFileChooser.APPROVE_OPTION) {
			try {
				int procesarExcel = procesarExcel(fileChooser.getSelectedFile());
				JOptionPane.showMessageDialog(homeForm, "Se procesaron "+procesarExcel+" registros");
				cargarListaInicial();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private int procesarExcel(File excel) throws IOException {
		FileInputStream inputStream = new FileInputStream(excel);

		// TODO: Validar Excel

		Workbook workbook = new XSSFWorkbook(inputStream);
		org.apache.poi.ss.usermodel.Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		iterator.next();

		Map<Integer, String> listaPecios = new HashMap<Integer, String>();

		Row filaEncabezados = iterator.next();
		for (Cell cell : filaEncabezados) {
			if (cell.getStringCellValue().contains("PRECIO")) {
				listaPecios.put(cell.getColumnIndex(), cell.getStringCellValue().substring("PRECIO_".length()));
			}
		}
		int contador = 0;
		while (iterator.hasNext()) {
			Row rowProducto = iterator.next();
			ProductoBuilder builder = Producto.builder();
			builder.id((int) rowProducto.getCell(0).getNumericCellValue());
			builder.activo(true);
			builder.descripcion(rowProducto.getCell(1).getStringCellValue());
			builder.stock((int) rowProducto.getCell(2).getNumericCellValue());
			builder.fechaVencimiento(rowProducto.getCell(3).getDateCellValue());

			Map<String, Double> preciosMap = new HashMap<String, Double>();

			if (!Double.isNaN(rowProducto.getCell(4).getNumericCellValue())) {
				preciosMap.put(listaPecios.get(4), rowProducto.getCell(4).getNumericCellValue());
			}

			builder.precios(preciosMap);

			Producto build = builder.build();
			dao.save(build);
			contador++;
		}

		workbook.close();
		inputStream.close();
		return contador;
	}

}