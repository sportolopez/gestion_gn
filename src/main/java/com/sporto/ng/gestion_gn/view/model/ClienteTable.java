package com.sporto.ng.gestion_gn.view.model;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Lista;

public class ClienteTable extends JTable {

	private static final int COLUMNA_LISTA = 7;

	public ClienteTable() {

		setRowHeight(40);
		setFont(Constants.FUENTE);
		ClienteTableModel productoTableModel = new ClienteTableModel();
		TableRowSorter<ClienteTableModel> sorter = new TableRowSorter<ClienteTableModel>(productoTableModel);
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

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component tableCellRendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (column == COLUMNA_LISTA) {
					Lista lista = Constants.getLista(table.getValueAt(row, COLUMNA_LISTA).toString());
					try {
						tableCellRendererComponent.setBackground(Color.decode("#"+lista.getColor()));
					} catch (NumberFormatException e) {
						tableCellRendererComponent.setBackground(table.getBackground());
					}
				} else {
					tableCellRendererComponent.setBackground(table.getBackground());
				}
				return tableCellRendererComponent;
			}

		};

		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		setDefaultRenderer(Object.class, rightRenderer);

	}

	public void filtrar(String texto) {
		RowFilter<ClienteTableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter("(?i)" + texto, 0, 1, 2);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		((TableRowSorter<ClienteTableModel>) getRowSorter()).setRowFilter(rf);
	}

}
