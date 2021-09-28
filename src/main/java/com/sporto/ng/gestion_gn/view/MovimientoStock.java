package com.sporto.ng.gestion_gn.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Dialog.ModalityType;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.validation.view.ValidationResultViewFactory;

public class MovimientoStock extends JDialog {
	private JTable table;
	private JTextField textCodigoProducto;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	private ImageIcon errorIcon = (ImageIcon) ValidationResultViewFactory.getCheckIcon();

	public MovimientoStock() {
		URL resource = getClass().getClassLoader().getResource("icono.ico");
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

		textCodigoProducto = new JTextField();
		panelAgregar.add(textCodigoProducto);
		textCodigoProducto.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Descripcion");
		panelAgregar.add(lblNewLabel_2);

		textField = new JTextField();
		textField.setEditable(false);
		panelAgregar.add(textField);
		textField.setColumns(15);

		JLabel lblNewLabel_3 = new JLabel("Cantidad");
		panelAgregar.add(lblNewLabel_3);

		textField_1 = new JTextField();
		panelAgregar.add(textField_1);
		textField_1.setColumns(8);

		JLabel lblNewLabel_4 = new JLabel("Vencimiento");
		panelAgregar.add(lblNewLabel_4);

		textField_2 = new JTextField();
		panelAgregar.add(textField_2);
		textField_2.setColumns(10);

		JButton btnNewButton_2 = new JButton("AGREGAR STOCK");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelAgregar.add(btnNewButton_2);

		JPanel panelBotones = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBotones.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		table = new JTable();

		String[] columnNames = { "C\u00F3digo Producto", "Descrici\u00F3n", "Cantidad", " ", " " };
		Object[][] data = { { "000", "Producto XXX", 123, errorIcon } };

		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public Class getColumnClass(int column) {
				if (column == 3)
					return ImageIcon.class;
				return Object.class;
			}
		};
		table.setModel(model);
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
