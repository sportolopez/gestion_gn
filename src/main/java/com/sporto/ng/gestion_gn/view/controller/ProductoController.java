package com.sporto.ng.gestion_gn.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_gn.dao.ListaDao;
import com.sporto.ng.gestion_gn.dao.ProductoDao;
import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.model.Producto.ProductoBuilder;
import com.sporto.ng.gestion_gn.view.HomeForm;
import com.sporto.ng.gestion_gn.view.ProductoDialog;
import com.sporto.ng.gestion_gn.view.ProductoPanel;
import com.sporto.ng.gestion_gn.view.model.ProductoTableModel;

@Component
public class ProductoController {

	private ProductoPanel productosPanel;
	private ProductoDialog productoDialog;
	private ProductoDao dao;
	private ListaDao listaDao;
	private HomeForm homeForm;

	@Autowired
	public ProductoController(ProductoDao dao, HomeForm homeForm, ListaDao listaDao) {
		super();
		this.dao = dao;
		this.productoDialog = new ProductoDialog();
		this.homeForm = homeForm;
		this.listaDao = listaDao;
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

		AbstractAction actionBorrar = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				int idProducto = Integer
						.valueOf(((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0).toString());
				int input = JOptionPane.showConfirmDialog(homeForm,
						"¿Confirmar el borrado del producto " + idProducto + "?","Eliminar producto",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
				if (input == 0) {
					dao.deleteById(idProducto);
					((DefaultTableModel) table.getModel()).removeRow(modelRow);
				}
			}
		};
		
		productosPanel.getButtonEliminar().setAction(actionBorrar);

		productosPanel.getBtnNuevoProducto().addActionListener(e -> nuevoProducto());
		productosPanel.getBtnImportar().addActionListener(e -> importarExcel());
		
		//productosPanel.getBtnBuscarProducto().addActionListener(e -> buscar());
		productosPanel.getTextFieldBuscadorProductos().addKeyListener(new KeyAdapter() {
		    public void keyReleased(KeyEvent e) {
		    	productosPanel.filtrar();
		    }
		});
		cargarListaInicial();
	}

	
	private void buscar() {
		productosPanel.filtrar();
	}


	private void nuevoProducto() {
		productoDialog.limpiarCampos();
		DefaultTableModel model = (DefaultTableModel) productoDialog.getTablePrecios().getModel();
		Iterable<Lista> findAll = listaDao.findAll();
		for (Lista lista : findAll) {
			model.addRow(new Object[] { lista.getNombre(), 0 });

		}
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
		productoDialog.cargarCampos(findById.get(), listaDao.findAll());
		productoDialog.setVisible(true);

	}

	private void saveProducto() {
		if(productoDialog.validar()) {
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
				int procesarExcel = procesarExcel(fileChooser.getSelectedFile());
				JOptionPane.showMessageDialog(homeForm, "Se procesaron " + procesarExcel + " registros");
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

			for(int i=4;i<(4+listaPecios.size());++i) {
				if (!Double.isNaN(rowProducto.getCell(i).getNumericCellValue())) {
					preciosMap.put(listaPecios.get(i), rowProducto.getCell(i).getNumericCellValue());
				}
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