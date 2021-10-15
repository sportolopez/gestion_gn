package com.sporto.ng.gestion_gn.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_gn.dao.MovimientoStockDao;
import com.sporto.ng.gestion_gn.dao.ProductoDao;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.model.TipoMovimiento;
import com.sporto.ng.gestion_gn.utils.ExcelUtils;
import com.sporto.ng.gestion_gn.view.HomeForm;
import com.sporto.ng.gestion_gn.view.MovimientoStockDialog;
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
						final Optional<Producto> findById = dao.findById(idProducto);
						if (findById.get().getStock() > 0) {
							JOptionPane
									.showMessageDialog(homeForm,
											"No se puede eliminar el producto ya que tiene existencias de stock ("
													+ findById.get().getStock() + ")",
											"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						dao.deleteById(idProducto);
						((DefaultTableModel) table.getModel()).removeRow(modelRow);
					} catch (DataIntegrityViolationException | ConstraintViolationException e1) {
						JOptionPane.showMessageDialog(homeForm,
								"No se puede eliminar el producto ya que tiene movimientos de stock o pedidos realizados",
								"Error", JOptionPane.ERROR_MESSAGE);
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
				MovimientoStockDialog movimientoStock = new MovimientoStockDialog(dao.findAll(), movimientoDao, dao,
						TipoMovimiento.INGRESO,homeForm);
				movimientoStock.setVisible(true);
				cargarListaInicial();
			}
		});
		productosPanel.getBtnEgresoStock().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MovimientoStockDialog movimientoStock = new MovimientoStockDialog(dao.findAll(), movimientoDao, dao,
						TipoMovimiento.EGRESO,homeForm);
				movimientoStock.setVisible(true);
				cargarListaInicial();
			}
		});
		cargarListaInicial();
	}

	private void nuevoProducto() {
		productoDialog.limpiarCampos();
		;
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
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		fileChooser.setDialogTitle("Abrir");
		fileChooser.setFileFilter(new FileNameExtensionFilter(".xlsx", "xlsx"));
		int option = fileChooser.showOpenDialog(homeForm);
		if (option == JFileChooser.APPROVE_OPTION) {
			try {
				List<Producto> productosConError = new ArrayList<Producto>();
				List<Producto> procesarExcel = ExcelUtils.procesarExcelProductos(fileChooser.getSelectedFile());
				for (Producto producto : procesarExcel) {
					try {
						dao.save(producto);
					} catch (Exception e) {
						productosConError.add(producto);
					}
				}
				JOptionPane.showMessageDialog(homeForm, "Se procesaron " + (procesarExcel.size()-productosConError.size()) + " registros correctamente. Con error: "+productosConError.size());
				cargarListaInicial();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}



}