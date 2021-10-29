package com.sporto.ng.gestion_gn.view.model;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.sporto.ng.gestion_gn.config.Constants;

public class PedidoTable extends JTable {

	public PedidoTable() {

		setRowHeight(40);
		setFont(Constants.FUENTE);
		PedidoTableModel productoTableModel = new PedidoTableModel();
		TableRowSorter<PedidoTableModel> sorter = new TableRowSorter<PedidoTableModel>(productoTableModel);
		setRowSorter(sorter);
		setModel(productoTableModel);
		getColumnModel().getColumn(0).setMaxWidth(40);
		getColumnModel().getColumn(0).setPreferredWidth(20);
		getColumnModel().getColumn(1).setMaxWidth(200);
		getColumnModel().getColumn(1).setPreferredWidth(130);
		getColumnModel().getColumn(3).setMaxWidth(100);
		getColumnModel().getColumn(3).setPreferredWidth(80);
		getColumnModel().getColumn(4).setMaxWidth(200);
		getColumnModel().getColumn(4).setPreferredWidth(150);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		setDefaultRenderer(Object.class, rightRenderer);

	}

	public void filtrar(String texto) {
		RowFilter<PedidoTableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter("(?i)" + texto, 0, 1, 2);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		((TableRowSorter<PedidoTableModel>) getRowSorter()).setRowFilter(rf);
	}

}
