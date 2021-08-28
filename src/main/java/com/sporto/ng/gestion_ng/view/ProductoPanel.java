package com.sporto.ng.gestion_ng.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_ng.view.model.ButtonColumn;
import com.sporto.ng.gestion_ng.view.model.ProductoTableModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoPanel extends JPanel {

	private ProductoTableModel productoTableModel;
	private ButtonColumn buttonEditar;
	private JButton btnNuevoProducto;
	private JButton btnImportar;

	public ProductoPanel(JFrame parent) {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.setLayout(null);
		this.setBounds(0, 0, 654, 299);

		JScrollPane scrollPaneProductos = new JScrollPane();
		scrollPaneProductos.setBounds(10, 91, 634, 194);
		this.add(scrollPaneProductos);

		JTable tableProductos = new JTable();
		productoTableModel = new ProductoTableModel();
		tableProductos.setModel(productoTableModel);
		tableProductos.getColumnModel().getColumnCount();

		Action botonDelete = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
			}
		};

		Action botonEditar = null;
		buttonEditar = new ButtonColumn(tableProductos, botonEditar, 3);
		new ButtonColumn(tableProductos, botonDelete, 4);

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

		btnImportar = new JButton("Importar");
		btnImportar.setBounds(376, 59, 89, 23);
		this.add(btnImportar);

		btnNuevoProducto = new JButton("Nuevo");
		btnNuevoProducto.setBounds(475, 59, 109, 23);
		this.add(btnNuevoProducto);
	}

}
