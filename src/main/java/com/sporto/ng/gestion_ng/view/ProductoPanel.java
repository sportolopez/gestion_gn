package com.sporto.ng.gestion_ng.view;

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
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableRowSorter;

import com.sporto.ng.gestion_ng.view.model.ButtonColumn;
import com.sporto.ng.gestion_ng.view.model.ProductoTableModel;

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
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.setLayout(null);
		this.setBounds(0, 0, 654, 299);

		JScrollPane scrollPaneProductos = new JScrollPane();
		scrollPaneProductos.setBounds(10, 91, 634, 194);
		this.add(scrollPaneProductos);

		tableProductos = new JTable();
		//tableProductos.setAutoCreateRowSorter(true);
		productoTableModel = new ProductoTableModel();
		sorter = new TableRowSorter<ProductoTableModel>(productoTableModel);
		tableProductos.setRowSorter(sorter);
		tableProductos.setModel(productoTableModel);
		tableProductos.getColumnModel().getColumnCount();
		
		
		

		Action botonDelete = null;
		Action botonEditar = null;
		buttonEditar = new ButtonColumn(tableProductos, botonEditar, 3);
		buttonEliminar = new ButtonColumn(tableProductos, botonDelete, 4);

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
		btnImportar.setBounds(376, 59, 89, 23);
		this.add(btnImportar);

		btnNuevoProducto = new JButton("Nuevo");
		btnNuevoProducto.setBounds(475, 59, 109, 23);
		this.add(btnNuevoProducto);

	}

}
