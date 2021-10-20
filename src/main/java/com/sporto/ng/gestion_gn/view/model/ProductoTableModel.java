package com.sporto.ng.gestion_gn.view.model;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Producto;

public class ProductoTableModel extends DefaultTableModel {

	public ProductoTableModel() {
		addColumn("CÓDIGO");
		addColumn("CATEGORIA");
		addColumn("DESCRIPCIÓN");
		addColumn("STOCK");
		addColumn("FECHA VENCIMIENTO");
		addColumn("DETALLE");
		addColumn("EDITAR");
		addColumn("ELIMINAR");
	}

	public void addProducto(Producto producto) {
		ArrayList<Object> lista = new ArrayList<Object>();
		lista.add(producto.getId());
		lista.add(producto.getCategoria());
		lista.add(producto.getDescripcion());
		lista.add(producto.getStock());
		lista.add(producto.getFechaString());

		lista.add(Constants.ICONO_DETALLE);
		lista.add(Constants.ICONO_EDITAR);
		lista.add(Constants.ICONO_ELIMINAR);

		addRow(lista.toArray());

	}

	public boolean isCellEditable(int row, int col) {
		if (col < 5) {
			return false;
		} else {
			return true;
		}
	}

}
