package com.sporto.ng.gestion_gn.view;

import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DetalleProducto extends JDialog {
	private JTable table;
	public DetalleProducto() {
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panelTitulo = new JPanel();
		getContentPane().add(panelTitulo);
		
		JLabel lblNewLabel = new JLabel("Detalle Producto");
		panelTitulo.add(lblNewLabel);
		
		JPanel panelDetalle = new JPanel();
		getContentPane().add(panelDetalle);
		
		JPanel panelMovimientos = new JPanel();
		getContentPane().add(panelMovimientos);
		
		JScrollPane scrollPane = new JScrollPane();
		panelMovimientos.add(scrollPane);
		
		table = new JTable();
		panelMovimientos.add(table);
	}

}
