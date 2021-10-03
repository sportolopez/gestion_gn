package com.sporto.ng.gestion_gn.view.model;

import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.model.Producto;

public class ProductoTableModel extends DefaultTableModel {

	ImageIcon editar = new ImageIcon(getClass().getClassLoader().getResource("iconos/Pencil-icon.png"));
	ImageIcon eliminarIcon = new ImageIcon(getClass().getClassLoader().getResource("iconos/Trash-empty-icon.png"));

	
	public ProductoTableModel() {

	}

	public void addProducto(Producto producto) {
		ArrayList<Object> lista = new ArrayList<Object>();
		lista.add(producto.getId());
		lista.add(producto.getCategoria());
		lista.add(producto.getDescripcion());
		lista.add(producto.getStock());
		lista.add(producto.getFechaString());
		
		lista.add(editar);
		lista.add(eliminarIcon);
		
		addRow(lista.toArray());

	}
	
	public boolean isCellEditable(int row, int col) {
		if (col < 4) {
			return false;
		} else {
			return true;
		}
	}

}
