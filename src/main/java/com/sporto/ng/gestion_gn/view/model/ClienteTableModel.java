package com.sporto.ng.gestion_gn.view.model;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.model.Cliente;

public class ClienteTableModel extends DefaultTableModel {

	ImageIcon editar = new ImageIcon(getClass().getClassLoader().getResource("iconos/Pencil-icon.png"));
	ImageIcon eliminarIcon = new ImageIcon(getClass().getClassLoader().getResource("iconos/Trash-empty-icon.png"));

	
	public ClienteTableModel() {
		addColumn("RAZÓN SOCIAL");
		addColumn("CUIT/CUIL");
		addColumn("MAIL");
		addColumn("TELÉFONO");
		addColumn("DOMICILIO");
		addColumn("DESCUBIERTO");
		addColumn("LISTA");
		addColumn("");
		addColumn("");
	}

	public void addCliente(Cliente producto) {
		ArrayList<Object> lista = new ArrayList<Object>();
		lista.add(producto.getRazonSocial());
		lista.add(producto.getCuit());
		lista.add(producto.getMail());
		lista.add(producto.getTelefono());
		lista.add(producto.getDomicilio());
		lista.add(producto.getLimiteDeuda());
		
		lista.add(editar);
		lista.add(eliminarIcon);
		
		addRow(lista.toArray());

	}
	
	public boolean isCellEditable(int row, int col) {
		if (col < 6) {
			return false;
		} else {
			return true;
		}
	}

}
