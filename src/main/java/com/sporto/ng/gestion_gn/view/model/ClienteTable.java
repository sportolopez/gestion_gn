package com.sporto.ng.gestion_gn.view.model;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Lista;

public class ClienteTable extends JTable {

	public ClienteTable() {

		setRowHeight(40);
		setFont(Constants.FUENTE);
		ClienteTableModel productoTableModel = new ClienteTableModel();
		TableRowSorter<ClienteTableModel> sorter = new TableRowSorter<ClienteTableModel>(productoTableModel);
		setRowSorter(sorter);
		setModel(productoTableModel);
		getColumnModel().getColumn(0).setMaxWidth(40);
		getColumnModel().getColumn(0).setPreferredWidth(40);
		getColumnModel().getColumn(1).setMaxWidth(200);
		getColumnModel().getColumn(1).setPreferredWidth(200);
		getColumnModel().getColumn(2).setMaxWidth(100);
		getColumnModel().getColumn(2).setPreferredWidth(100);
		getColumnModel().getColumn(ClienteTableModel.COLUMN_MAIL).setMaxWidth(100);
		getColumnModel().getColumn(ClienteTableModel.COLUMN_MAIL).setPreferredWidth(100);
		getColumnModel().getColumn(ClienteTableModel.COLUMN_DESCUBIERTO).setMaxWidth(100);
		getColumnModel().getColumn(ClienteTableModel.COLUMN_DESCUBIERTO).setPreferredWidth(100);
		getColumnModel().getColumn(ClienteTableModel.COLUMN_DEUDA).setMaxWidth(100); 		
		getColumnModel().getColumn(ClienteTableModel.COLUMN_DEUDA).setPreferredWidth(100);
		getColumnModel().getColumn(ClienteTableModel.COLUMN_LISTA).setMaxWidth(100); 		
		getColumnModel().getColumn(ClienteTableModel.COLUMN_LISTA).setPreferredWidth(100);
		getColumnModel().getColumn(ClienteTableModel.COLUMN_PEDIDO).setMinWidth(0);
		getColumnModel().getColumn(ClienteTableModel.COLUMN_PEDIDO).setMaxWidth(0);
		getColumnModel().getColumn(ClienteTableModel.COLUMN_PEDIDO).setPreferredWidth(0);
		

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component tableCellRendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (column == ClienteTableModel.COLUMN_LISTA) {
					Lista lista = Constants.getLista(table.getValueAt(row, ClienteTableModel.COLUMN_LISTA).toString());
					try {
						tableCellRendererComponent.setBackground(Color.decode("#"+lista.getColor()));
					} catch (NumberFormatException e) {
						tableCellRendererComponent.setBackground(Color.white);
					}
				} else {
					tableCellRendererComponent.setBackground(Color.white);
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
