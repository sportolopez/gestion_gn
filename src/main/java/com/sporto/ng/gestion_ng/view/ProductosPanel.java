package com.sporto.ng.gestion_ng.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_ng.view.controller.ProductoController;
import com.sporto.ng.gestion_ng.view.model.ButtonColumn;
import com.sporto.ng.gestion_ng.view.model.ProductoTableModel;

@Component
public class ProductosPanel extends JPanel {

	@Autowired
	public ProductosPanel(ProductoController controller) {
		this.setLayout(null);
		this.setBounds(0, 50, 625, 550);

		JScrollPane scrollPaneProductos = new JScrollPane();
		scrollPaneProductos.setBounds(10, 91, 574, 194);
		this.add(scrollPaneProductos);

		JTable tableProductos = new JTable();
		ProductoTableModel productoTableModel = new ProductoTableModel();
		controller.setProdctoTableModel(productoTableModel);
		controller.cargarListaInicial();
		tableProductos.setModel(productoTableModel);
		tableProductos.getColumnModel().getColumnCount();

		Action delete = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
			}
		};
		Action editar = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				Integer idProducto = (Integer) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
				controller.editarProducto(idProducto);
				
			}
		};

		new ButtonColumn(tableProductos, editar, 3);
		new ButtonColumn(tableProductos, delete, 4);

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

		JButton btnNuevoProducto = new JButton("Nuevo");
		btnNuevoProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AltaProducto().setVisible(true);
			}
		});
		btnNuevoProducto.setBounds(475, 59, 109, 23);
		this.add(btnNuevoProducto);
	}

}
