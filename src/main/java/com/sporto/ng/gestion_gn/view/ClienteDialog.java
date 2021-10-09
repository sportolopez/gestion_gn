package com.sporto.ng.gestion_gn.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.view.validations.DoubleVerifier;
import com.sporto.ng.gestion_gn.view.validations.FechaVerifier;
import com.sporto.ng.gestion_gn.view.validations.NumeroVerifier;
import com.sporto.ng.gestion_gn.view.validations.TextoVerifier;

import lombok.Getter;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ClienteDialog extends JDialog {

	private JPanel panelProductos;
	private JLabel lblEmail;
	private JLabel lblCodigoLabel;
	private JTextField textRazonSocial;
	private JTextField textEmail;
	private JLabel lblTeléfono;
	private JTextField textFieldTelefono;
	private JButton btnGuardar;
	private JTextField textFieldDireccion;
	private DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	private java.util.Set<String> camposInvalidos = new HashSet<String>();
	private JLabel lblCuit;
	private JTextField textFieldCUIT;
	private JTextField textFieldLimite;
	private JLabel lblCosto;

	/**
	 * Create the frame.
	 * 
	 * @param abstractAction
	 */

	public ClienteDialog() {
		URL resource = getClass().getClassLoader().getResource("icono.ico");
		setIconImage(Toolkit.getDefaultToolkit().getImage(resource));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		initComponents();
		configValidations();
	}

	public void cargarCampos(Producto unProducto) {
		this.textRazonSocial.setText(String.valueOf(unProducto.getId()));
		textRazonSocial.setEditable(false);
		this.textFieldTelefono.setText(String.valueOf(unProducto.getStock()));
		Date fechaVencimiento = unProducto.getFechaVencimiento();
		if (fechaVencimiento != null)
			this.textEmail.setText(formatoFecha.format(fechaVencimiento));
//		Set<Entry<String, Double>> precios = unProducto.getPreciosSet();
		// DefaultTableModel model = (DefaultTableModel) tablePrecios.getModel();
//		model.setRowCount(0);
//
//		for (Entry<String, Double> unPrecio : precios) {
//			model.addRow(new Object[] { unPrecio.getKey(), unPrecio.getValue() });
//		}
		textFieldDireccion.setText(unProducto.getDescripcion());
		textFieldCUIT.setText(unProducto.getCategoria());
		if (unProducto.getCosto() == null)
			textFieldLimite.setText("0");
		else
			textFieldLimite.setText(unProducto.getCosto().toString());

	}

	private void initComponents() {
		setTitle("Cliente");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 510, 261);
		panelProductos = new JPanel();
		panelProductos.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelProductos);
		panelProductos.setLayout(null);

		lblCodigoLabel = new JLabel("RAZÓN SOCIAL");
		lblCodigoLabel.setBounds(33, 17, 87, 14);
		lblCodigoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblCodigoLabel);

		textRazonSocial = new JTextField();
		textRazonSocial.setBounds(130, 17, 116, 20);
		textRazonSocial.setHorizontalAlignment(SwingConstants.RIGHT);
		textRazonSocial.setColumns(10);
		textRazonSocial.setFont(Constants.FUENTE);
		panelProductos.add(textRazonSocial);

		lblEmail = new JLabel("EMAIL");
		lblEmail.setBounds(33, 45, 87, 14);
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblEmail);

		textEmail = new JTextField();
		textEmail.setText("");
		textEmail.setBounds(130, 45, 116, 20);
		textEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		textEmail.setToolTipText("");
		textEmail.setFont(Constants.FUENTE);
		panelProductos.add(textEmail);
		textEmail.setColumns(10);

		lblTeléfono = new JLabel("TELÉFONO");
		lblTeléfono.setBounds(243, 45, 70, 14);
		lblTeléfono.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblTeléfono);

		textFieldTelefono = new JTextField();
		textFieldTelefono.setBounds(323, 45, 109, 20);
		textFieldTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldTelefono.setColumns(10);
		textFieldTelefono.setFont(Constants.FUENTE);
		;
		panelProductos.add(textFieldTelefono);

		// tablePrecios = new JTable();
//		scrollPane.setViewportView(tablePrecios);
//		tablePrecios.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Lista", "Precio" }) {
//			Class[] columnTypes = new Class[] { String.class, Double.class };
//
//			public Class getColumnClass(int columnIndex) {
//				return columnTypes[columnIndex];
//			}
//
//			boolean[] columnEditables = new boolean[] { false, true };
//
//			public boolean isCellEditable(int row, int column) {
//				return columnEditables[column];
//			}
//		});
//		tablePrecios.getColumnModel().getColumn(1).setCellEditor(new DoubleEditor());

		btnGuardar = new JButton("GUARDAR");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGuardar.setBounds(276, 160, 127, 41);
		panelProductos.add(btnGuardar);

		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBounds(139, 160, 127, 41);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panelProductos.add(btnCancelar);

		JLabel lblDescripcion = new JLabel("DIRECCIÓN");
		lblDescripcion.setBounds(33, 73, 87, 14);
		lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblDescripcion);

		textFieldDireccion = new JTextField();
		textFieldDireccion.setBounds(130, 70, 302, 19);
		textFieldDireccion.setColumns(10);
		textFieldDireccion.setFont(Constants.FUENTE);
		panelProductos.add(textFieldDireccion);

		lblCuit = new JLabel("CUIT");
		lblCuit.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCuit.setBounds(243, 17, 70, 17);
		panelProductos.add(lblCuit);

		textFieldCUIT = new JTextField();
		textFieldCUIT.setColumns(10);
		textFieldCUIT.setBounds(323, 17, 109, 20);
		textFieldCUIT.setFont(Constants.FUENTE);
		panelProductos.add(textFieldCUIT);

		textFieldLimite = new JTextField();
		textFieldLimite.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldLimite.setColumns(10);
		textFieldLimite.setBounds(130, 98, 116, 20);
		panelProductos.add(textFieldLimite);

		lblCosto = new JLabel("LÍMITE DESCUBIERTO");
		lblCosto.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblCosto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCosto.setBounds(0, 100, 118, 14);
		panelProductos.add(lblCosto);
		
		JLabel lblListaDePrecio = new JLabel("LISTA");
		lblListaDePrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblListaDePrecio.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblListaDePrecio.setBounds(254, 100, 56, 14);
		panelProductos.add(lblListaDePrecio);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"VIP", "CABA", "lala", "lalal"}));
		comboBox.setBounds(323, 97, 109, 22);
		panelProductos.add(comboBox);

	}

	private void configValidations() {
		textEmail.setInputVerifier(new FechaVerifier("Fecha de Vencimiento", camposInvalidos));
		textRazonSocial.setInputVerifier(new NumeroVerifier("Código", camposInvalidos));
		textFieldTelefono.setInputVerifier(new NumeroVerifier("Stock", camposInvalidos));
		textFieldDireccion.setInputVerifier(new TextoVerifier("Descripción", camposInvalidos));
		textFieldLimite.setInputVerifier(new DoubleVerifier("Costo", camposInvalidos));
	}

	public Producto getProducto() {
		try {
//			if(tablePrecios.isEditing()) {
//				tablePrecios.getCellEditor().stopCellEditing();
//			}
//			Map<String, Double> preciosMap = new HashMap<String, Double>();
//
//			for (int count = 0; count < tablePrecios.getRowCount(); count++) {
//				System.out.println("Lista:"+tablePrecios.getValueAt(count, 0).toString() + "   PRECIO: "+tablePrecios.getValueAt(count, 1).toString());
//				preciosMap.put(tablePrecios.getValueAt(count, 0).toString(),
//						Double.parseDouble(tablePrecios.getValueAt(count, 1).toString()));
//			}

			Date parse = null;
			try {
				parse = formatoFecha.parse(textEmail.getText());
			} catch (Exception e) {
			}
			Producto build = Producto.builder().fechaVencimiento(parse).descripcion(textFieldDireccion.getText())
					.activo(true).id(Integer.parseInt(textRazonSocial.getText()))
					.stock(Integer.parseInt(textFieldTelefono.getText()))
					.costo(Double.parseDouble(textFieldLimite.getText()))
					// .precios(preciosMap)
					.categoria(textFieldCUIT.getText()).build();
			return build;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public void limpiarCampos() {
		textRazonSocial.setEditable(true);
		textEmail.setText("");
		textFieldDireccion.setText("");
		textRazonSocial.setText("");
		textFieldTelefono.setText("");
		textFieldCUIT.setText("");
		textFieldLimite.setText("");
	}

	public boolean validar() {

		if (camposInvalidos.size() > 0) {
			JOptionPane.showMessageDialog(new JFrame(), "Campos invalidos:" + camposInvalidos, "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;

	}
}
