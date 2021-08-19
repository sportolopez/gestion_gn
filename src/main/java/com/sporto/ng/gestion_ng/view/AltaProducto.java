package com.sporto.ng.gestion_ng.view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JButton;

public class AltaProducto extends JDialog  {

	private JPanel panelProductos;
	private JLabel lblFechaDeVencimiento;
	private JLabel lblCodigoLabel;
	private JTextField textFieldCodigo;
	private JTextField textFechaVencimiento;
	private JLabel lblStock;
	private JTextField textFieldStock;
	private JPanel mainPanel;
	private JTable table;
	private JScrollPane scrollPane;
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
		setTitle("Producto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 348, 300);
		panelProductos = new JPanel();
		panelProductos.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelProductos);
        panelProductos.setLayout(null);
		
        lblCodigoLabel = new JLabel("Código");
        lblCodigoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCodigoLabel.setBounds(10, 14, 70, 14);
        panelProductos.add(lblCodigoLabel);
        
        textFieldCodigo = new JTextField();
        textFieldCodigo.setBounds(90, 11, 86, 20);
        panelProductos.add(textFieldCodigo);
        textFieldCodigo.setColumns(10);
        
        lblFechaDeVencimiento = new JLabel("Vencimiento");
        lblFechaDeVencimiento.setHorizontalAlignment(SwingConstants.RIGHT);
        lblFechaDeVencimiento.setBounds(10, 67, 70, 14);
        panelProductos.add(lblFechaDeVencimiento);
        
        textFechaVencimiento = new JTextField();
        textFechaVencimiento.setToolTipText("12/12/1983");
        textFechaVencimiento.setBounds(90, 64, 86, 20);
        panelProductos.add(textFechaVencimiento);
        textFechaVencimiento.setColumns(10);
        
        lblStock = new JLabel("Stock");
        lblStock.setHorizontalAlignment(SwingConstants.RIGHT);
        lblStock.setBounds(10, 39, 70, 14);
        panelProductos.add(lblStock);
        
        textFieldStock = new JTextField();
        textFieldStock.setBounds(90, 36, 86, 20);
        textFieldStock.setColumns(10);
        panelProductos.add(textFieldStock);
        
        scrollPane = new JScrollPane();
        scrollPane.setBounds(39, 92, 279, 91);
        panelProductos.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null},
        		{null, null},
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
        
        JTextPane textPaneDescripcion = new JTextPane();
        textPaneDescripcion.setBounds(186, 13, 132, 68);
        panelProductos.add(textPaneDescripcion);
        
        JButton btnNewButton = new JButton("Guardar");
        btnNewButton.setBounds(229, 226, 89, 23);
        panelProductos.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Cancelar");
        btnNewButton_1.setBounds(130, 226, 89, 23);
        panelProductos.add(btnNewButton_1);
	}
}
