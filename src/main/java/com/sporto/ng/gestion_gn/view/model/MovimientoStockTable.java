package com.sporto.ng.gestion_gn.view.model;

import java.text.ParseException;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.model.TipoMovimiento;

public class MovimientoStockTable extends JTable {
	
	TableRowSorter<MovimientoStockTableModel> tableRowSorter;

	public MovimientoStockTable() {
		super();
	}

	public MovimientoStockTable(TipoMovimiento tipoMovimiento) {
		super();
		setRowHeight(30);
		setModel(new MovimientoStockTableModel(tipoMovimiento));

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		setDefaultRenderer(Object.class, rightRenderer);

		tableRowSorter = new TableRowSorter<MovimientoStockTableModel>((MovimientoStockTableModel) getModel());
		setRowSorter(tableRowSorter);
	}

	public void registrarMovimiento(TipoMovimiento tipoMovimiento, Producto unProducto, int cantidad,
			String vencimiento, String comentario) {

		final int COLUMNA_CANTIDAD = 2;
		

		MovimientoStockTableModel model = (MovimientoStockTableModel) getModel();

		Integer indexProducto = getIndexProducto(unProducto.getId());
		if (indexProducto != null) {
			Integer cantidadEnTabla = (Integer) getValueAt(indexProducto, COLUMNA_CANTIDAD);
			int nuevaCantidad = cantidadEnTabla + cantidad;

			if (tipoMovimiento == TipoMovimiento.EGRESO) {
				if(unProducto.getStock()-nuevaCantidad<0) {
					JOptionPane.showMessageDialog(new JFrame(), "Supera al stock existente", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}

			setValueAt(nuevaCantidad, indexProducto, COLUMNA_CANTIDAD);
			

			if (tipoMovimiento == TipoMovimiento.INGRESO) {
				actualizarFechaSiCorresponde(vencimiento, indexProducto);
			}
		} else {
			
			if (tipoMovimiento == TipoMovimiento.EGRESO) {
				if(unProducto.getStock()-cantidad<0) {
					JOptionPane.showMessageDialog(new JFrame(), "Supera al stock existente", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			model.registrarMovimiento(tipoMovimiento, unProducto.getId(), unProducto.getDescripcion(), cantidad,
					vencimiento,comentario);
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
			Integer idProductoTable = (Integer) getValueAt(i, COLUMNA_CANTIDAD);
			if (idProductoTable.equals(idProducto)) {
				return Integer.valueOf(getValueAt(i, COLUMNA_CANTIDAD).toString());
			}
		}
		return 0;
	}

	public void filtrar(String idProducto) {
		RowFilter<MovimientoStockTableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter("(?i)" + idProducto, 0);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		tableRowSorter.setRowFilter(rf);
	}

}
