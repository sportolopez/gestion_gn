package com.sporto.ng.gestion_gn.view.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.sporto.ng.gestion_gn.model.Pedido;

public class LiberarPedidoTable extends JTable {

	public LiberarPedidoTable() {
		super();
		setRowHeight(30);
		setModel(new LiberarPedidoTableModel());

		setPreferredScrollableViewportSize(new Dimension(450, 100));
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		setDefaultRenderer(Object.class, rightRenderer);
		
		
		getColumnModel().getColumn(0).setMaxWidth(50);
		getColumnModel().getColumn(0).setPreferredWidth(50);
	}

	public void addPedidos(List<Pedido> findByClienteAndEstado) {
		((LiberarPedidoTableModel) getModel()).addPedidos(findByClienteAndEstado);
		
	}

	public List<Integer> getPedidosSeleccionados() {
		
		List<Integer> lista = new ArrayList<Integer>();
		
		
		
		for (int i = 0; i < getRowCount(); i++) {
			Boolean tramiteSeleccionado = (Boolean) getValueAt(i, LiberarPedidoTableModel.COLUMN_LIBERAR);
			if(tramiteSeleccionado) {
				lista.add((Integer) getValueAt(i, LiberarPedidoTableModel.COLUMN_ID));
			}
			
		}
		return lista;
	}



}
