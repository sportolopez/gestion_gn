package com.sporto.ng.gestion_gn.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.google.common.collect.Lists;
import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.utils.Java2sAutoComboBox;

public class MovimientoStock extends JDialog {
	private JTable table;
	private JTextField textFieldDescripcion;
	private JTextField textFieldCantidad;
	private JTextField textFieldVencimiento;
	private Iterable<Producto> listaProductos;
	private ImageIcon eliminarIcon;
	
	public MovimientoStock(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
		URL resource = getClass().getClassLoader().getResource("icono.ico");
		eliminarIcon= new ImageIcon(getClass().getClassLoader().getResource("iconos/Trash-empty-icon.png"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(resource));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 760, 261);
		setTitle("Ingresar Stock");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JLabel lblNewLabel = new JLabel("INGRESAR STOCK");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(lblNewLabel);

		JPanel panelAgregar = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelAgregar.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panelAgregar);

		JLabel lblNewLabel_1 = new JLabel("Codigo");
		panelAgregar.add(lblNewLabel_1);

		List<Integer> myWords = listaProductos.stream().map(Producto::getId).collect(Collectors.toList());
		List<String> transform = Lists.transform(myWords, com.google.common.base.Functions.toStringFunction());
		transform = new ArrayList<>(transform);
		transform.add(0, "");
		Java2sAutoComboBox textCodigoProducto = new Java2sAutoComboBox(transform);
		textCodigoProducto.setDataList(transform);
		
		
		panelAgregar.add(textCodigoProducto);

		JLabel lblNewLabel_2 = new JLabel("Descripcion");
		panelAgregar.add(lblNewLabel_2);

		textFieldDescripcion = new JTextField();
		textFieldDescripcion.setEditable(false);
		textFieldDescripcion.setEnabled(false);
		panelAgregar.add(textFieldDescripcion);
		textFieldDescripcion.setColumns(15);

		JLabel lblNewLabel_3 = new JLabel("Cantidad");
		panelAgregar.add(lblNewLabel_3);

		textFieldCantidad = new JTextField();
		panelAgregar.add(textFieldCantidad);
		textFieldCantidad.setColumns(8);

		JLabel lblNewLabel_4 = new JLabel("Vencimiento");
		panelAgregar.add(lblNewLabel_4);

		
		textFieldVencimiento = new JFormattedTextField(Constants.getMascaraFecha());
		textFieldVencimiento.setBounds(107, 45, 86, 20);
		textFieldVencimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldVencimiento.setToolTipText("DD/MM/AAAA");
		textFieldVencimiento.setFont(Constants.FUENTE);
		panelAgregar.add(textFieldVencimiento);
		textFieldVencimiento.setColumns(10);

		JButton btnNewButton_2 = new JButton("AGREGAR STOCK");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<?> rowData;
				((DefaultTableModel)table.getModel()).addRow(new Object[] { textCodigoProducto.getSelectedItem().toString(), textFieldDescripcion.getText(), textFieldCantidad.getText(),textFieldVencimiento.getText(),eliminarIcon });
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelAgregar.add(btnNewButton_2);

		JPanel panelBotones = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBotones.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		table = new JTable();
		table.setRowHeight(30);
		String[] columnNames = { "C\u00F3digo Producto", "Descrici\u00F3n","Vencimiento", "Cantidad","Cancelar" };
		Object[][] data = { { "000", "Producto XXX", "12/10/2023",123, eliminarIcon } };

		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public Class getColumnClass(int column) {
				if ( column == 4)
					return ImageIcon.class;
				return Object.class;
			}
		    @Override 
		       public boolean isCellEditable(int row, int col) {
	            if (col > 3 || col == 1) {
	                return false;
	            } else {
	                return true;
	            }
	        }
		    
		};
		table.setModel(model);
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if(col == 4) {
		        	((DefaultTableModel)table.getModel()).removeRow(row);
		        }
		        	
		    }
		});
		scrollPane.setViewportView(table);
		getContentPane().add(panelBotones);

		JButton btnNewButton = new JButton("CANCELAR");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelBotones.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("GUARDAR");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelBotones.add(btnNewButton_1);

		pack();

	}

}
