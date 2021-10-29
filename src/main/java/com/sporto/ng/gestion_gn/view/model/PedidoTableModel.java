package com.sporto.ng.gestion_gn.view.model;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Pedido;

public class PedidoTableModel extends DefaultTableModel {

	public static final int COLUMN_DETALLE = 4;
	
	public PedidoTableModel() {
		addColumn("NRO PEDIDO");
		addColumn("CLIENTE");
		addColumn("CUIT/CUIL");
		addColumn("ESTADO");
		addColumn("DETALLE");
		addColumn("ANULAR");
		addColumn("ENTREGADO");
	}

	public void addPedido(Pedido pedido) {
		ArrayList<Object> lista = new ArrayList<Object>();
		lista.add(pedido.getId());
		lista.add(pedido.getCliente().getRazonSocial());
		lista.add(pedido.getCliente().getCuit());
		lista.add(pedido.getEstado());
		lista.add(Constants.ICONO_DETALLE);
		lista.add(Constants.ICONO_ELIMINAR);
		lista.add(Constants.ICONO_AGREGAR);
		
		addRow(lista.toArray());

	}
	
	@Override
	public Class getColumnClass(int column) {
		if (column > COLUMN_DETALLE)
			return ImageIcon.class;
		return Object.class;
	}
	
	public boolean isCellEditable(int row, int col) {
			return false;
	}

}
