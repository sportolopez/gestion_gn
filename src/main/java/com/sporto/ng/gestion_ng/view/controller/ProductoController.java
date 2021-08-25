package com.sporto.ng.gestion_ng.view.controller;

import java.awt.event.ActionEvent;
import java.util.Optional;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_ng.dao.ProductoDao;
import com.sporto.ng.gestion_ng.model.Producto;
import com.sporto.ng.gestion_ng.view.ProductosPanel;
import com.sporto.ng.gestion_ng.view.modal.ProductoDialog;
import com.sporto.ng.gestion_ng.view.model.ProductoTableModel;

@Component
public class ProductoController {

	private ProductosPanel productosPanel;
	private ProductoDialog productoDialog;
	private ProductoDao dao;

	@Autowired
	public ProductoController(ProductoDao dao, ProductoDialog prodctoDialog, ProductosPanel productosPanel) {
		super();
		this.dao = dao;
		this.productoDialog = prodctoDialog;
		this.productosPanel = productosPanel;
		prodctoDialog.getBtnGuardar().addActionListener(e -> saveProducto());
		productosPanel.getButtonEditar().setAction(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				Integer idProducto = (Integer) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
				editarProducto(idProducto);

			}
		});
		productosPanel.getBtnNuevoProducto().addActionListener(e -> nuevoProducto());
		prodctoDialog.getBtnAgregarPrecio().addActionListener(e -> nuevoPrecio());
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

}