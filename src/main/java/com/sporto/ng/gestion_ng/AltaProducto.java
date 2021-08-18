package com.sporto.ng.gestion_ng;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AltaProducto extends JDialog  {

	private JPanel panelProductos;
	private JLabel lblFechaDeVencimiento;
	private JLabel lblCodigoLabel;
	private JTextField textFieldCodigo;
	private JTextField textFechaVencimiento;
	private JLabel lblStock;
	private JTextField textFieldStock;
	private JLabel lblDescripcion;
	private JTextField textFieldDescripcion;
	private JPanel mainPanel;
	private JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaProducto frame = new AltaProducto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AltaProducto() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		panelProductos = new JPanel();
		panelProductos.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelProductos);
        panelProductos.setLayout(null);
		
        lblCodigoLabel = new JLabel("Código");
        lblCodigoLabel.setBounds(159, 13, 33, 14);
        panelProductos.add(lblCodigoLabel);
        
        textFieldCodigo = new JTextField();
        textFieldCodigo.setBounds(196, 10, 233, 20);
        textFieldCodigo.setText("Codigo");
        panelProductos.add(textFieldCodigo);
        textFieldCodigo.setColumns(10);
        
        lblFechaDeVencimiento = new JLabel("Fecha de vencimiento");
        lblFechaDeVencimiento.setBounds(88, 38, 104, 14);
        panelProductos.add(lblFechaDeVencimiento);
        
        textFechaVencimiento = new JTextField();
        textFechaVencimiento.setBounds(196, 35, 233, 20);
        panelProductos.add(textFechaVencimiento);
        textFechaVencimiento.setColumns(10);
        
        lblStock = new JLabel("Stock");
        lblStock.setBounds(166, 64, 26, 14);
        panelProductos.add(lblStock);
        
        textFieldStock = new JTextField();
        textFieldStock.setBounds(196, 61, 233, 20);
        textFieldStock.setColumns(10);
        panelProductos.add(textFieldStock);
        
        lblDescripcion = new JLabel("Descripción");
        lblDescripcion.setBounds(138, 90, 54, 14);
        panelProductos.add(lblDescripcion);
        
        textFieldDescripcion = new JTextField();
        textFieldDescripcion.setBounds(196, 87, 233, 20);
        textFieldDescripcion.setColumns(10);
        panelProductos.add(textFieldDescripcion);
        
        table = new JTable();
        table.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null},
        		{null, null},
        		{null, null},
        		{null, null},
        	},
        	new String[] {
        		"Lista", "Precio"
        	}
        ) {
        	Class[] columnTypes = new Class[] {
        		String.class, Float.class
        	};
        	public Class getColumnClass(int columnIndex) {
        		return columnTypes[columnIndex];
        	}
        });
        table.setBounds(31, 122, 342, 100);
        panelProductos.add(table);
	}
}
