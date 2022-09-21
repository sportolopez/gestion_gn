package com.sporto.ng.gestion_gn.view.model;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Producto;

public class ProductoTableModel extends DefaultTableModel {

	public static final int COL_DETALLE = 8;
	public static final int COL_EDITAR = 9;
	
	public ProductoTableModel() {
		addColumn("CÓDIGO");
		addColumn("CATEGORIA");
		addColumn("DESCRIPCIÓN");
		addColumn("STOCK");
		addColumn("STOCK BLOQUEADO");
		addColumn("VENCIMIENTO");
		addColumn("ULT. COMPRA");
		addColumn("ULT. OC");
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
		lista.add(producto.getBloqueadoEmitido());
		lista.add(producto.getFechaString());
		lista.add(producto.getFechaUltimoRemito());
		lista.add(producto.getUltima_oc());
		lista.add(Constants.ICONO_DETALLE);
		lista.add(Constants.ICONO_EDITAR);
		lista.add(Constants.ICONO_ELIMINAR);

		addRow(lista.toArray());

	}

	public boolean isCellEditable(int row, int col) {
		if (col < 8) {
			return false;
		} else {
			return true;
		}
	}

}
