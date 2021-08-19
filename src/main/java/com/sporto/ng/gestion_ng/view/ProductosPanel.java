package com.sporto.ng.gestion_ng.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.springframework.stereotype.Component;

@Component
public class ProductosPanel extends JPanel {

	public ProductosPanel() {
		this.setLayout(null);
		this.setBounds(0, 50, 625, 550);

		JScrollPane scrollPaneProductos = new JScrollPane();
		scrollPaneProductos.setBounds(10, 91, 574, 194);
		this.add(scrollPaneProductos);

		JTable tableProductos = new JTable();
		tableProductos.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null }, { null, null, null, null, null },
						{ null, null, null, null, null }, { null, null, null, null, null }, },
				new String[] { "C\u00F3digo", "Descripci\u00F3n", "Stock", " ", " " }));
		scrollPaneProductos.setViewportView(tableProductos);

		JTextField textFieldBuscadorProductos = new JTextField();
		textFieldBuscadorProductos.setBounds(10, 60, 254, 20);
		this.add(textFieldBuscadorProductos);
		textFieldBuscadorProductos.setColumns(10);

		JLabel lblTituloProductos = new JLabel("Productos");
		lblTituloProductos.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTituloProductos.setBounds(10, 11, 125, 20);
		this.add(lblTituloProductos);

		JButton btnBuscarProducto = new JButton("Buscar");
		btnBuscarProducto.setBounds(277, 59, 89, 23);
		this.add(btnBuscarProducto);

		JButton btnImportar = new JButton("Importar");
		btnImportar.setBounds(376, 59, 89, 23);
		this.add(btnImportar);

		JButton btnNuevoProducto = new JButton("Nuevo Producto");
		btnNuevoProducto.setBounds(475, 59, 109, 23);
		this.add(btnNuevoProducto);
	}
}
