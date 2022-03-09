package com.sporto.ng.gestion_gn.view.model;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.EstadoPedido;
import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.model.Pedido;

public class PedidoTable extends JTable {

	public PedidoTable() {

		setRowHeight(40);
		setFont(Constants.FUENTE);
		PedidoTableModel productoTableModel = new PedidoTableModel();
		TableRowSorter<PedidoTableModel> sorter = new TableRowSorter<PedidoTableModel>(productoTableModel);
		setRowSorter(sorter);
		setModel(productoTableModel);
		getColumnModel().getColumn(0).setMaxWidth(80);
		getColumnModel().getColumn(0).setPreferredWidth(80);
		getColumnModel().getColumn(1).setMaxWidth(200);
		getColumnModel().getColumn(1).setPreferredWidth(200);
		getColumnModel().getColumn(2).setMaxWidth(200);
		getColumnModel().getColumn(2).setPreferredWidth(200);
		getColumnModel().getColumn(3).setMaxWidth(200);
		getColumnModel().getColumn(3).setPreferredWidth(200);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component tableCellRendererComponent = super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				if (column == PedidoTableModel.COLUMN_ESTADO) {
					String valueAt = getValueAt(row, PedidoTableModel.COLUMN_ESTADO).toString();
					tableCellRendererComponent.setBackground(EstadoPedido.valueOf(valueAt).getColor());
					tableCellRendererComponent.setFont(Constants.FUENTE_LABEL );
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
		RowFilter<PedidoTableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter("(?i)" + texto, 0, 1, 2);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		((TableRowSorter<PedidoTableModel>) getRowSorter()).setRowFilter(rf);
	}

	public void agregarPedido(Pedido pedido) {
		PedidoTableModel model = (PedidoTableModel) getModel();
		model.addPedido(pedido);

	}

}
