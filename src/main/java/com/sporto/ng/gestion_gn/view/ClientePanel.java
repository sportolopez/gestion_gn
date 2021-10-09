package com.sporto.ng.gestion_gn.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableRowSorter;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.view.model.ClienteTable;
import com.sporto.ng.gestion_gn.view.model.ClienteTableModel;
import com.sporto.ng.gestion_gn.view.model.ProductoTableModel;

public class ClientePanel extends JPanel {

	private static final String TITULO = "CLIENTES";
	private ClienteTableModel productoTableModel;
	private JButton btnNuevoCliente;
	private JTable tableClientes;
	JTextField textFieldBuscadorProductos;
	TableRowSorter<ProductoTableModel> sorter;
	private JPanel panelBotonera;
	JButton btnIngresoStock;
	private JLabel lblListadoActual;
	JButton btnEgresoStock;
	
	
	public ClientePanel(JFrame parent) {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel lblTituloProductos = new JLabel(TITULO);
		lblTituloProductos.setBorder(new EmptyBorder(10,0,10,0));
		lblTituloProductos.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTituloProductos.setFont(Constants.FUENTE_TITULO);
		lblTituloProductos.setBounds(10, 11, 125, 20);
		this.add(lblTituloProductos);

		crearBotonera();
		
		lblListadoActual = new JLabel("LISTADO ACTUAL");
		lblListadoActual.setBorder(new EmptyBorder(10,0,10,0));
		lblListadoActual.setHorizontalAlignment(SwingConstants.LEFT);
		lblListadoActual.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblListadoActual.setFont(Constants.FUENTE_SUB_TITULO);
		add(lblListadoActual);
		
				textFieldBuscadorProductos = new JTextField();
				add(textFieldBuscadorProductos);
				textFieldBuscadorProductos.setColumns(20);
		
		JScrollPane scrollPaneProductos = new JScrollPane();
		scrollPaneProductos.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		this.add(scrollPaneProductos);

		
		tableClientes = new ClienteTable();

		scrollPaneProductos.setViewportView(tableClientes);

	}

	private void crearBotonera() {
		panelBotonera = new JPanel();
		panelBotonera.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelBotonera.setMaximumSize(new Dimension(3500,50));
		panelBotonera.setAlignmentY(Component.TOP_ALIGNMENT);
		FlowLayout fl_panelBotonera = (FlowLayout) panelBotonera.getLayout();
		fl_panelBotonera.setAlignOnBaseline(true);
		fl_panelBotonera.setAlignment(FlowLayout.LEFT);
		add(panelBotonera);

		btnNuevoCliente = new JButton("NUEVO CLIENTE");
		btnNuevoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panelBotonera.add(btnNuevoCliente);
		btnNuevoCliente.setMnemonic(KeyEvent.VK_N);

////		btnImportar = new JButton("IMPORTAR EXCEL...");
////		panelBotonera.add(btnImportar);
////		btnImportar.setMnemonic(KeyEvent.VK_I);
//
//		btnIngresoStock = new JButton("INGRESO STOCK");
//		
//		panelBotonera.add(btnIngresoStock);
//		
//		
//		btnEgresoStock = new JButton("EGRESO STOCK");
//		panelBotonera.add(btnEgresoStock);
	}

}
