package com.sporto.ng.gestion_gn.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableRowSorter;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.view.model.ProductoTableModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoPanel extends JPanel {

	private ProductoTableModel productoTableModel;
	private JButton btnNuevoProducto;
	private JButton btnImportar;
	private JTable tableProductos;
	JTextField textFieldBuscadorProductos;
	TableRowSorter<ProductoTableModel> sorter;
	private JPanel panel;
	JButton btnIngresoStock;
	private JLabel lblListadoActual;
	
	public void filtrar() {
		RowFilter<ProductoTableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter("(?i)" + textFieldBuscadorProductos.getText(), 0, 1, 2);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}

	public ProductoPanel(JFrame parent) {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel lblTituloProductos = new JLabel("PRODUCTO - STOCK");
		lblTituloProductos.setBorder(new EmptyBorder(10,0,10,0));
		lblTituloProductos.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTituloProductos.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTituloProductos.setBounds(10, 11, 125, 20);
		this.add(lblTituloProductos);

		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setMaximumSize(new Dimension(3500,50));
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignOnBaseline(true);
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(panel);

		textFieldBuscadorProductos = new JTextField();
		panel.add(textFieldBuscadorProductos);
		textFieldBuscadorProductos.setColumns(20);

		btnNuevoProducto = new JButton("NUEVO");
		panel.add(btnNuevoProducto);
		btnNuevoProducto.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNuevoProducto.setMnemonic(KeyEvent.VK_N);

		btnImportar = new JButton("IMPORTAR EXCEL...");
		panel.add(btnImportar);
		btnImportar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnImportar.setMnemonic(KeyEvent.VK_I);

		btnIngresoStock = new JButton("INGRESO STOCK");
		
		btnIngresoStock.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(btnIngresoStock);
		
		
		JButton btnEgresoStock = new JButton("EGRESO STOCK");
		btnEgresoStock.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(btnEgresoStock);
		
		lblListadoActual = new JLabel("LISTADO ACTUAL");
		lblListadoActual.setBorder(new EmptyBorder(10,0,10,0));
		lblListadoActual.setHorizontalAlignment(SwingConstants.LEFT);
		lblListadoActual.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblListadoActual.setFont(new Font("Tahoma", Font.BOLD, 16));
		add(lblListadoActual);
		
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
