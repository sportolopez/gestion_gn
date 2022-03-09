package com.sporto.ng.gestion_gn.view.model;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Cliente;

public class ClienteTableModel extends DefaultTableModel {

	public static final int COLUMN_CUIT = 2;
	public static final int COLUMN_MAIL = 3;
	public static final int COLUMN_DOMICILIO = 5;
	public static final int COLUMN_DESCUBIERTO = 6;
	public static final int COLUMN_DEUDA = 7;
	public static final int COLUMN_LISTA = 8;
	public static final int COLUMN_PEDIDO = 9;
	public static final int COLUMN_EXPORTAR = 10;
	public static final int COLUMN_EDITAR = 11;
	public static final int COLUMN_LIBERAR = 12;
	public static final int COLUMN_DETALLE = 13;
	
	public ClienteTableModel() {
		addColumn("ID");
		addColumn("RAZÓN SOCIAL");
		addColumn("CUIT/CUIL");
		addColumn("MAIL");
		addColumn("TELÉFONO");
		addColumn("DOMICILIO");
		addColumn("DESCUBIERTO");
		addColumn("SALDO");
		addColumn("LISTA");
		addColumn("NUEVO PEDIDO");
		addColumn("PRECIOS");
		addColumn("EDITAR");
		addColumn("OPERAR");
		addColumn("DETALLE");
	}

	public void addCliente(Cliente producto) {
		ArrayList<Object> lista = new ArrayList<Object>();
		lista.add(producto.getId());
		lista.add(producto.getRazonSocial());
		lista.add(producto.getCuit());
		lista.add(producto.getEmail());
		lista.add(producto.getTelefono());
		lista.add(producto.getDomicilio());
		lista.add(Constants.outDouble(producto.getLimiteDeuda()));
		lista.add(Constants.outDouble(producto.getSaldo()));
		lista.add(producto.getListaPrecio());
		lista.add(Constants.ICONO_PEDIDO);
		lista.add(Constants.ICONO_EXPORTAR);
		lista.add(Constants.ICONO_EDITAR);
		lista.add(Constants.ICONO_PAGO);
		lista.add(Constants.ICONO_DETALLE);
		
		addRow(lista.toArray());

	}
	
	@Override
	public Class getColumnClass(int column) {
		if (column == COLUMN_EDITAR || column == COLUMN_EXPORTAR || column == COLUMN_PEDIDO || column == COLUMN_LIBERAR || column == COLUMN_DETALLE)
			return ImageIcon.class;
		return Object.class;
	}
	
	public boolean isCellEditable(int row, int col) {
			return false;
	}

}
