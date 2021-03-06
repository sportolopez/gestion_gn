package com.sporto.ng.gestion_gn.view.model;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.EstadoPedido;
import com.sporto.ng.gestion_gn.model.Pedido;

public class PedidoTableModel extends DefaultTableModel {

	public static final int COLUMN_ESTADO = 3;
	public static final int COLUMN_CANCELAR = 4;
	public static final int COLUMN_ENTREGADO = 5;
	public static final int COLUMN_DETALLE = 6;
	
	public PedidoTableModel() {
		addColumn("ID");
		addColumn("CLIENTE");
		addColumn("FECHA PEDIDO");
		addColumn("ESTADO");
		addColumn("ANULAR");
		addColumn("RETIRAR");
		addColumn("DETALLE");
	}

	public void addPedido(Pedido pedido) {
		ArrayList<Object> lista = new ArrayList<Object>();
		lista.add(pedido.getId());
		lista.add(pedido.getCliente().getRazonSocial());
		lista.add(Constants.outFecha(pedido.getFecha()));
		lista.add(pedido.getEstado());
		if(pedido.getEstado().equals(EstadoPedido.EMITIDO) || pedido.getEstado().equals(EstadoPedido.LIBERADO) || pedido.getEstado().equals(EstadoPedido.RETIRADO))
			lista.add(Constants.ICONO_CANCELAR);
		else
			lista.add("");
		
		if(pedido.getEstado().equals(EstadoPedido.LIBERADO))
			lista.add(Constants.ICONO_ENTREGADO);
		else
			lista.add("");
		
		lista.add(Constants.ICONO_DETALLE);

		addRow(lista.toArray());

	}
	
	@Override
	public Class getColumnClass(int column) {
		if (column >= COLUMN_CANCELAR)
			return ImageIcon.class;
		return Object.class;
	}
	
	public boolean isCellEditable(int row, int col) {
			return false;
	}

}
