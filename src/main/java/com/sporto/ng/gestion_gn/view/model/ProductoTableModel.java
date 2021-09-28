package com.sporto.ng.gestion_gn.view.model;

import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.model.Producto;

public class ProductoTableModel extends DefaultTableModel {

	public ProductoTableModel() {

	}

	public void addProducto(Producto producto) {
		ArrayList<Object> lista = new ArrayList<Object>();
		lista.add(producto.getId());
		lista.add(producto.getCategoria());
		lista.add(producto.getDescripcion());
		lista.add(producto.getStock());
		lista.add(producto.getFechaString());
		
//		for (Entry<String, Double> object : producto.getPreciosSet()) {
//			lista.add(object.getValue());
//		}
//		
		lista.add("Editar");
		lista.add("Eliminar");
		
		addRow(lista.toArray());

	}

}
