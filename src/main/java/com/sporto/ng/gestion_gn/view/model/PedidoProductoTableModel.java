package com.sporto.ng.gestion_gn.view.model;

import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.TipoMovimiento;

public class PedidoProductoTableModel extends DefaultTableModel {

	public PedidoProductoTableModel() {
		List<String> list;

		list = Arrays.asList(new String[] { "CÓDIGO PRODUCTO", "DESCRIPCIÓN", "CANTIDAD", "PRECIO","DESCUENTO","SUBTOTAL", "ELIMINAR" });

		for (String columnName : list) {
			addColumn(columnName);
		}

	}

	@Override
	public Class getColumnClass(int column) {
		if (column == getColumnCount() - 1)
			return ImageIcon.class;
		return Object.class;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (col > (getColumnCount() - 2) || col == 1 || col == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void add(int idProducto,String descripcion,int cantidad, Double precio) {
			addRow(new Object[] { idProducto,descripcion, cantidad, precio, Constants.ICONO_ELIMINAR });
	}

}