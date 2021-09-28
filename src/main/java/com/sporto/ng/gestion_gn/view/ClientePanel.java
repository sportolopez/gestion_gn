package com.sporto.ng.gestion_gn.view;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.view.model.ProductoTableModel;

import lombok.Getter;
import lombok.Setter;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.border.MatteBorder;
import java.awt.Color;

@Getter
@Setter
public class ClientePanel extends JPanel {

	private ProductoTableModel productoTableModel;
	private JButton btnNuevoProducto;
	private JButton btnImportar;
	private JTable tableProductos;
	JTextField textFieldBuscadorProductos;
	TableRowSorter<ProductoTableModel> sorter;
	private JPanel panel;

	public void filtrar() {
		RowFilter<ProductoTableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter("(?i)" + textFieldBuscadorProductos.getText(), 0, 1, 2);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}

	public ClientePanel(JFrame parent) {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel lblTituloProductos = new JLabel("Clientes");
		lblTituloProductos.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTituloProductos.setBounds(10, 11, 125, 20);
		this.add(lblTituloProductos);

		panel = new JPanel();
		panel.setMaximumSize(new Dimension(1500,50));
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignOnBaseline(true);
		flowLayout.setAlignment(FlowLayout.LEADING);
		add(panel);

		textFieldBuscadorProductos = new JTextField();
		panel.add(textFieldBuscadorProductos);
		textFieldBuscadorProductos.setColumns(20);

		btnNuevoProducto = new JButton("Nuevo");
		panel.add(btnNuevoProducto);
		btnNuevoProducto.setMnemonic(KeyEvent.VK_N);
		btnNuevoProducto.setFont(Constants.FUENTE);

		btnImportar = new JButton("Importar");
		panel.add(btnImportar);
		btnImportar.setFont(Constants.FUENTE);
		btnImportar.setMnemonic(KeyEvent.VK_I);

		
		JScrollPane scrollPaneProductos = new JScrollPane();
		scrollPaneProductos.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		this.add(scrollPaneProductos);

		
		tableProductos = new JTable();
		tableProductos.setRowHeight(40);
		tableProductos.setFont(Constants.FUENTE);
		productoTableModel = new ProductoTableModel();
		sorter = new TableRowSorter<ProductoTableModel>(productoTableModel);
		tableProductos.setRowSorter(sorter);
		tableProductos.setModel(productoTableModel);

		scrollPaneProductos.setViewportView(tableProductos);

	}

}
