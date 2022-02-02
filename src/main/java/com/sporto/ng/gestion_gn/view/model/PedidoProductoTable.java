package com.sporto.ng.gestion_gn.view.model;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Pedido;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.utils.MyTablePrintable;

public class PedidoProductoTable extends JTable {
	TableRowSorter<PedidoProductoTableModel> tableRowSorter;
	private MessageFormat[] header;
	private MessageFormat[] footer;

	public PedidoProductoTable() {
		super();
		setRowHeight(30);
		setModel(new PedidoProductoTableModel());

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		setDefaultRenderer(Object.class, rightRenderer);

		tableRowSorter = new TableRowSorter<PedidoProductoTableModel>((PedidoProductoTableModel) getModel());
		setRowSorter(tableRowSorter);

		getColumnModel().getColumn(PedidoProductoTableModel.COLUMNA_CODIGO).setMinWidth(60);
		getColumnModel().getColumn(PedidoProductoTableModel.COLUMNA_CODIGO).setMaxWidth(60);
		getColumnModel().getColumn(PedidoProductoTableModel.COLUMNA_CODIGO).setPreferredWidth(60);
		getColumnModel().getColumn(PedidoProductoTableModel.COLUMNA_CANTIDAD).setMinWidth(60);
		getColumnModel().getColumn(PedidoProductoTableModel.COLUMNA_CANTIDAD).setMaxWidth(60);
		getColumnModel().getColumn(PedidoProductoTableModel.COLUMNA_CANTIDAD).setPreferredWidth(60);

	}
	
	public void ocultarColumnaEliminar() {
		getColumnModel().getColumn(PedidoProductoTableModel.COLUMNA_ELIMINAR).setMinWidth(0);
		getColumnModel().getColumn(PedidoProductoTableModel.COLUMNA_ELIMINAR).setMaxWidth(0);
		getColumnModel().getColumn(PedidoProductoTableModel.COLUMNA_ELIMINAR).setPreferredWidth(0);	
	}

	public void registrarMovimiento(Producto unProducto, int cantidad, Double precio, String descuento) {

		final int COLUMNA_CANTIDAD = 2;
		final int COLUMNA_DESCUENTO = 4;
		final int COLUMNA_PRECIO = 3;
		final int COLUMNA_SUBTOTAL = 5;
		PedidoProductoTableModel model = (PedidoProductoTableModel) getModel();

		Integer indexProducto = getIndexProducto(unProducto.getId());
		if (indexProducto != null) {
			Integer cantidadEnTabla = (Integer) getValueAt(indexProducto, COLUMNA_CANTIDAD);
			double precioEnTabla = Constants.parseDouble(getValueAt(indexProducto, COLUMNA_PRECIO).toString());

			int nuevaCantidad = cantidadEnTabla + cantidad;

			double nuevoSubtotal =  model.calcularSubtotal(precioEnTabla, descuento, nuevaCantidad);

			if (unProducto.getStock() - nuevaCantidad < 0) {
				JOptionPane.showMessageDialog(new JFrame(), "Supera al stock existente", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			setValueAt(nuevaCantidad, indexProducto, COLUMNA_CANTIDAD);
			setValueAt(descuento + " %", indexProducto, COLUMNA_DESCUENTO);
			setValueAt(Constants.outDouble(nuevoSubtotal), indexProducto, COLUMNA_SUBTOTAL);

		} else {

			if (unProducto.getStock() - cantidad < 0) {
				JOptionPane.showMessageDialog(new JFrame(), "Supera al stock existente", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			model.add(unProducto.getId(), unProducto.getDescripcion(), cantidad, precio, descuento);
		}

	}

	private void actualizarFechaSiCorresponde(String vencimiento, Integer indexProducto) {
		final int COLUMNA_FECHAVENCIMIENTO = 4;
		try {
			Date fechaActual = Constants.FORMATO_FECHA
					.parse(getValueAt(indexProducto, COLUMNA_FECHAVENCIMIENTO).toString());
			Date fechaNueva = Constants.FORMATO_FECHA.parse(vencimiento);
			if (fechaNueva.after(fechaActual))
				setValueAt(vencimiento, indexProducto, COLUMNA_FECHAVENCIMIENTO);

		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public Integer getIndexProducto(int idProducto) {
		for (int i = 0; i < getRowCount(); i++) {
			Integer idProductoTable = (Integer) getValueAt(i, 0);
			if (idProductoTable.equals(idProducto)) {
				return i;
			}
		}
		return null;
	}

	public Integer getCantidadEnTabla(int idProducto) {
		final int COLUMNA_CANTIDAD = 2;
		for (int i = 0; i < getRowCount(); i++) {
			Integer idProductoTable = (Integer) getValueAt(i, PedidoProductoTableModel.COLUMNA_CODIGO);
			if (idProductoTable.equals(idProducto)) {
				return Integer.valueOf(getValueAt(i, COLUMNA_CANTIDAD).toString());
			}
		}
		return 0;
	}

	public void filtrar(String idProducto) {
		RowFilter<PedidoProductoTableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter("(?i)" + idProducto, 0);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		tableRowSorter.setRowFilter(rf);
	}


	


}
