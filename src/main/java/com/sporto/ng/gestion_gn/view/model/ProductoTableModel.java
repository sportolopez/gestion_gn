package com.sporto.ng.gestion_gn.view.model;

import javax.swing.table.DefaultTableModel;

public class ProductoTableModel extends DefaultTableModel {

	public ProductoTableModel() {
		super(new Object[][] { { null, null, null, null, null }, { null, null, null, null, null },
				{ null, null, null, null, null }, { null, null, null, null, null }, },
				new String[] { "C\u00F3digo", "Descripci\u00F3n", "Stock", "", "" });

	}


}
