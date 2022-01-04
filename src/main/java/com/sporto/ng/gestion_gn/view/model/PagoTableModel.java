package com.sporto.ng.gestion_gn.view.model;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.MovimientoCaja;

public class PagoTableModel extends DefaultTableModel {

	public static final int COLUMN_MEDIO_DE_PAGO = 0;
	public static final int COLUMN_MONTO = 1;
	public static final int COLUMN_COMENTARIO = 2;
	public static final int COLUMN_ELIMINAR = 3;
	
	public PagoTableModel() {
		addColumn("MEDIO DE PAGO");
		addColumn("MONTO");
		addColumn("COMENTARIO");
		addColumn("BORRAR");
	}

	public void addPago(MovimientoCaja unPago) {
		ArrayList<Object> lista = new ArrayList<Object>();
		lista.add(unPago.getMedioPago());
		lista.add(Constants.outDouble(unPago.getMonto()));
		lista.add(unPago.getComentario());
		lista.add(Constants.ICONO_ELIMINAR);
		addRow(lista.toArray());

	}
	
	@Override
	public Class getColumnClass(int column) {
		if(column == COLUMN_ELIMINAR)
			return ImageIcon.class;
		return Object.class;
    }
	
	public boolean isCellEditable(int row, int col) {
			return false;
	}

	public Double getTotal() {
		double total = 0;
		for (int count = 0; count < getRowCount(); count++) {
			total += Constants.parseDouble(getValueAt(count, COLUMN_MONTO).toString());
		}
		return total;
	}

}
