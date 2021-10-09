package com.sporto.ng.gestion_gn.view;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.view.model.ProductoTableModel;

public class NuevoPanel extends JPanel {
	private JTable tableProductos;
	JTextField textFieldBuscadorProductos;
	TableRowSorter<ProductoTableModel> sorter;
	public NuevoPanel() {
		JScrollPane scrollPaneProductos = new JScrollPane();
		tableProductos = new JTable();
		tableProductos.setRowHeight(40);
		tableProductos.setFont(Constants.FUENTE);
		tableProductos.setRowSorter(sorter);
		tableProductos.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		scrollPaneProductos.setViewportView(tableProductos);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel lblTituloProductos = new JLabel("PRODUCTOS");
		lblTituloProductos.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(lblTituloProductos);
		add(scrollPaneProductos);
	}

}
