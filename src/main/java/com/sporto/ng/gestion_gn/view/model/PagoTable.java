package com.sporto.ng.gestion_gn.view.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.GastoCaja;
import com.sporto.ng.gestion_gn.model.MedioPago;
import com.sporto.ng.gestion_gn.model.MovimientoCaja;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.model.TipoMovimiento;

public class PagoTable extends JTable {

	public PagoTable() {
		super();
		setRowHeight(30);
		setModel(new PagoTableModel());

		setPreferredScrollableViewportSize(new Dimension(450, 100));
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		setDefaultRenderer(Object.class, rightRenderer);

		getColumnModel().getColumn(0).setMaxWidth(150);
		getColumnModel().getColumn(0).setPreferredWidth(150);

		getColumnModel().getColumn(1).setMaxWidth(100);
		getColumnModel().getColumn(1).setPreferredWidth(100);
	}

	public void registrarPago(Producto unProducto, int cantidad, Double precio, String descuento) {

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

	public String getTotal() {
		return Constants.outDouble(((PagoTableModel) getModel()).getTotal());
	}

	public void addPago(MovimientoCaja unMovimientoPago) {
		((PagoTableModel) getModel()).addPago(unMovimientoPago);
	}

	public List<MovimientoCaja> getPagos(Cliente cliente) {
		List<MovimientoCaja> listMovimientos = new ArrayList<MovimientoCaja>();

		for (int i = 0; i < getRowCount(); i++) {
			MovimientoCaja unMov = MovimientoCaja.builder().cliente(cliente)
					.comentario(getValueAt(i, PagoTableModel.COLUMN_COMENTARIO).toString()).fecha(new Date())
					.medioPago(MedioPago.valueOf(getValueAt(i, PagoTableModel.COLUMN_MEDIO_DE_PAGO).toString()))
					.monto(Constants.parseDouble(getValueAt(i, PagoTableModel.COLUMN_MONTO).toString()))
					.denominacion(getValueAt(i, PagoTableModel.COLUMN_DENOMINACION).toString())
					.tipoMovimiento(TipoMovimiento.INGRESO).build();
			listMovimientos.add(unMov);
		}
		return listMovimientos;

	}

	public List<GastoCaja> getGastos() {
		List<GastoCaja> listMovimientos = new ArrayList<GastoCaja>();

		for (int i = 0; i < getRowCount(); i++) {
			GastoCaja unMov = GastoCaja.builder().comentario(getValueAt(i, PagoTableModel.COLUMN_COMENTARIO).toString())
					.fecha(new Date())
					.monto(Constants.parseDouble(getValueAt(i, PagoTableModel.COLUMN_MONTO).toString())).build();
			listMovimientos.add(unMov);
		}
		return listMovimientos;

	}

}
