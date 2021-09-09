package com.sporto.ng.gestion_gn.view;

import java.awt.Font;

import javax.swing.Action;
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
import com.sporto.ng.gestion_gn.view.model.ButtonColumn;
import com.sporto.ng.gestion_gn.view.model.ProductoTableModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoPanel extends JPanel {

	private ProductoTableModel productoTableModel;
	private ButtonColumn buttonEditar;
	private ButtonColumn buttonEliminar;
	private JButton btnNuevoProducto;
	private JButton btnImportar;
	private JTable tableProductos;
	JTextField textFieldBuscadorProductos;
	TableRowSorter<ProductoTableModel> sorter;
	
	public void filtrar() {
	    RowFilter<ProductoTableModel, Object> rf = null;
	    //If current expression doesn't parse, don't update.
	    try {
	        rf = RowFilter.regexFilter("(?i)" +textFieldBuscadorProductos.getText(), 0,1);
	    } catch (java.util.regex.PatternSyntaxException e) {
	        return;
	    }
	    sorter.setRowFilter(rf);
	}
	public ProductoPanel(JFrame parent) {
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.setLayout(null);
		this.setBounds(0, 0, 1290, 400);

		JScrollPane scrollPaneProductos = new JScrollPane();
		scrollPaneProductos.setBounds(10, 91, 1270, 398);
		this.add(scrollPaneProductos);

		tableProductos = new JTable();
		tableProductos.setRowHeight(40);
		tableProductos.setFont(Constants.FUENTE);
		productoTableModel = new ProductoTableModel();
		sorter = new TableRowSorter<ProductoTableModel>(productoTableModel);
		tableProductos.setRowSorter(sorter);
		tableProductos.setModel(productoTableModel);

		Action botonDelete = null;
		Action botonEditar = null;
		tableProductos.getColumnModel().getColumnCount();
		tableProductos.getColumnModel().getColumn(0).setPreferredWidth(15);
		tableProductos.getColumnModel().getColumn(1).setPreferredWidth(21);
		tableProductos.getColumnModel().getColumn(2).setPreferredWidth(30);
		tableProductos.getColumnModel().getColumn(3).setPreferredWidth(15);
		tableProductos.getColumnModel().getColumn(4).setPreferredWidth(15);
		tableProductos.getColumnModel().getColumn(5).setPreferredWidth(50);
		
		for(int i=0; i < tableProductos.getColumnCount(); i++){
			tableProductos.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
		}
		buttonEditar = new ButtonColumn(tableProductos, botonEditar, 6);
		buttonEliminar = new ButtonColumn(tableProductos, botonDelete, 7);

		scrollPaneProductos.setViewportView(tableProductos);

		textFieldBuscadorProductos = new JTextField();
		textFieldBuscadorProductos.setBounds(10, 60, 252, 20);
		this.add(textFieldBuscadorProductos);
		textFieldBuscadorProductos.setColumns(10);

		JLabel lblTituloProductos = new JLabel("Productos");
		lblTituloProductos.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTituloProductos.setBounds(10, 11, 125, 20);
		this.add(lblTituloProductos);

		btnImportar = new JButton("Importar");
		btnImportar.setFont(Constants.FUENTE);
		btnImportar.setBounds(572, 57, 89, 23);
		this.add(btnImportar);

		btnNuevoProducto = new JButton("Nuevo");
		btnNuevoProducto.setFont(Constants.FUENTE);
		btnNuevoProducto.setBounds(671, 59, 109, 23);
		this.add(btnNuevoProducto);

	}

}
