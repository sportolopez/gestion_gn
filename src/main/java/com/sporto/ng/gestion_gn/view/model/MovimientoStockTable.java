package com.sporto.ng.gestion_gn.view.model;

import java.text.ParseException;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.sporto.ng.gestion_gn.config.Constants;
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

	public void registrarMovimiento(TipoMovimiento tipoMovimiento, int idProducto, String descripcion, int cantidad,
			String vencimiento) {
		final int COLUMNA_CANTIDAD = 2;
		final int COLUMNA_FECHAVENCIMIENTO = 3;

		MovimientoStockTableModel model = (MovimientoStockTableModel) getModel();
		boolean productoExistente = false;
		for (int i = 0; i < getRowCount(); i++) {
			Integer idProductoTable = (Integer) getValueAt(i, 0);
			if (idProductoTable.equals(idProducto)) {
				int cantidadActual = Integer.valueOf(getValueAt(i, COLUMNA_CANTIDAD).toString());
				setValueAt(cantidadActual + cantidad, i, COLUMNA_CANTIDAD);
				productoExistente = true;
				// Actualizo la fecha

				if (tipoMovimiento == TipoMovimiento.INGRESO) {
					try {
						Date fechaActual = Constants.FORMATO_FECHA.parse(getValueAt(i, COLUMNA_FECHAVENCIMIENTO).toString());
						Date fechaNueva = Constants.FORMATO_FECHA.parse(vencimiento);
						if(fechaNueva.after(fechaActual))
							setValueAt(vencimiento, i, COLUMNA_FECHAVENCIMIENTO);
						
					} catch (ParseException e) {
						throw new RuntimeException(e);
					}

				}
					

			}
		}
		if (!productoExistente)
			model.registrarMovimiento(tipoMovimiento, idProducto, descripcion, cantidad, vencimiento);

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
