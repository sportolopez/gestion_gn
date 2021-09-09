package com.sporto.ng.gestion_gn.view.model;

import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Producto;

public class ProductoTableModel extends DefaultTableModel {

	public ProductoTableModel() {
		super(new Object[][] {  },
				new String[] { "C\u00F3digo",  "Categoria","Descripci\u00F3n", "Stock", "Fecha Vencimiento", "Precios",
						"", "" });
	}

	public void addProducto(Producto producto) {
		addRow(new Object[] { producto.getId(), producto.getCategoria(),
				producto.getDescripcion(), producto.getStock(), producto.getFechaString(),
				producto.getPreciosString(), "Editar", "Eliminar" });
		
	}

}
