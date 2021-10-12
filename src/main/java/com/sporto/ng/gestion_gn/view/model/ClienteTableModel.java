package com.sporto.ng.gestion_gn.view.model;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Cliente;

public class ClienteTableModel extends DefaultTableModel {

	public final int COLUMN_EDITAR = 8;
	
	public ClienteTableModel() {
		addColumn("ID");
		addColumn("RAZÓN SOCIAL");
		addColumn("CUIT/CUIL");
		addColumn("MAIL");
		addColumn("TELÉFONO");
		addColumn("DOMICILIO");
		addColumn("DESCUBIERTO");
		addColumn("LISTA");
		addColumn("");
		//addColumn("");
	}

	public void addCliente(Cliente producto) {
		ArrayList<Object> lista = new ArrayList<Object>();
		lista.add(producto.getId());
		lista.add(producto.getRazonSocial());
		lista.add(producto.getCuit());
		lista.add(producto.getEmail());
		lista.add(producto.getTelefono());
		lista.add(producto.getDomicilio());
		lista.add(producto.getLimiteDeuda());
		lista.add(producto.getListaPrecio());
		lista.add(Constants.ICONO_EDITAR);
		//lista.add(Constants.ICONO_ELIMINAR);
		
		addRow(lista.toArray());

	}
	
	@Override
	public Class getColumnClass(int column) {
		if (column == COLUMN_EDITAR)
			return ImageIcon.class;
		return Object.class;
	}
	
	public boolean isCellEditable(int row, int col) {
			return false;
	}

}
