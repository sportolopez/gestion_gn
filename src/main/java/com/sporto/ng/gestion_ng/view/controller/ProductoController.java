package com.sporto.ng.gestion_ng.view.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_ng.dao.ProductoDao;
import com.sporto.ng.gestion_ng.model.Producto;
import com.sporto.ng.gestion_ng.view.modal.AltaProductoDialog;
import com.sporto.ng.gestion_ng.view.model.ProductoTableModel;

@Component
public class ProductoController {

	private ProductoTableModel prodctoTableModel;
	private ProductoDao dao;

	@Autowired
	public ProductoController(ProductoDao dao) {
		super();
		this.dao = dao;

	}

	public void setProdctoTableModel(ProductoTableModel prodctoTableModel) {
		this.prodctoTableModel = prodctoTableModel;
	}

	public void cargarListaInicial() {
		prodctoTableModel.setRowCount(0);
		Iterable<Producto> findAll = dao.findAll();
		for (Producto producto : findAll) {

			prodctoTableModel.addRow(new Object[] { producto.getId(), producto.getDescripcion(), producto.getStock(),
					"editar", "eliminar" });
		}

	}

	public void editarProducto(Integer idProducto) {
		Optional<Producto> findById = dao.findById(idProducto);
		new AltaProductoDialog(findById.get()).setVisible(true);
		
	}

}