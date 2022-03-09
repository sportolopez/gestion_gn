package com.sporto.ng.gestion_gn.view.model;

import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.TipoMovimiento;

public class MovimientoStockTableModel extends DefaultTableModel {
		
	
		public MovimientoStockTableModel(TipoMovimiento tipoMovimiento) {
			List<String> list;

			if (tipoMovimiento.equals(TipoMovimiento.INGRESO)) {
				list = Arrays
						.asList(new String[] { "CÓDIGO PRODUCTO", "DESCRIPCIÓN", "CANTIDAD", "VENCIMIENTO", "COMENTARIO", "CANCELAR" });
			} else {
				list = Arrays.asList(new String[] { "CÓDIGO PRODUCTO", "DESCRIPCIÓN", "CANTIDAD", "COMENTARIO", "CANCELAR" });
			}
			for (String columnName : list) {
				addColumn(columnName);
			}
			
			
		}

		@Override
		public Class getColumnClass(int column) {
			if (column == getColumnCount()-1)
				return ImageIcon.class;
			return Object.class;
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			if (col > (getColumnCount() - 2) || col == 1 || col == 0) {
				return false;
			} else {
				return true;
			}
		}
		
		public void registrarMovimiento(TipoMovimiento tipoMovimiento, int idProducto, String descripcion, int cantidad, String vencimiento, String comentario) {
			if (tipoMovimiento.equals(TipoMovimiento.INGRESO))
				addRow(new Object[] { idProducto,descripcion, cantidad,vencimiento,comentario, Constants.ICONO_ELIMINAR });
			else
				addRow(new Object[] { idProducto,descripcion, cantidad,comentario,  Constants.ICONO_ELIMINAR });
		}
		
		
	}