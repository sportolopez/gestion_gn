package com.sporto.ng.gestion_gn.view.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Pedido;

public class LiberarPedidoTableModel extends DefaultTableModel {

	public static final int COLUMN_ID = 0;
	public static final int COLUMN_LIBERAR = 2;
	public static final int COLUMN_TOTAL = 1;
	
	public LiberarPedidoTableModel() {
		addColumn("ID");
		addColumn("TOTAL MONTO");
		addColumn("LIBERAR");

		
	}

	public void addPedido(Pedido pedido) {
		ArrayList<Object> lista = new ArrayList<Object>();
		lista.add(pedido.getId());
		lista.add(Constants.outDouble(pedido.getMonto()));
		lista.add(Boolean.FALSE);
		addRow(lista.toArray());

	}
	
	@Override
	public Class getColumnClass(int column) {
		if(column == COLUMN_LIBERAR)
			return Boolean.class;
		else
			return Object.class;
//		if(getValueAt(0, column) == null)
//			return Object.class;
//		return getValueAt(0, column).getClass();
    }
	
	public boolean isCellEditable(int row, int col) {
		if(col == COLUMN_LIBERAR)
			return true;
		else
			return false;
	}

	public void addPedidos(List<Pedido> findByClienteAndEstado) {
		
		for (Pedido pedido : findByClienteAndEstado) {
			addPedido(pedido);
		}
		
	}

}
