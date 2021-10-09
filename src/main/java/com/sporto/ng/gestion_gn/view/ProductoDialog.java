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

@Getter
public class ProductoDialog extends JDialog {

	private JPanel panelProductos;
	private JLabel lblFechaDeVencimiento;
	private JLabel lblCodigoLabel;
	private JTextField textFieldCodigo;
	private JTextField textFechaVencimiento;
	private JButton btnGuardar;
	private JTextField textAreaDescripcion;
	private DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	private java.util.Set<String> camposInvalidos = new HashSet<String>();
	private JLabel lblCategoria;
	private JTextField textFieldCategoria;
	private JTextField textFieldCosto;
	private JLabel lblCosto;

	/**
	 * Create the frame.
	 * 
	 * @param abstractAction
	 */

	public ProductoDialog() {
		URL resource = getClass().getClassLoader().getResource("icono.ico");
		setIconImage(Toolkit.getDefaultToolkit().getImage(resource));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		initComponents();
		configValidations();
	}

	public void cargarCampos(Producto unProducto) {
		this.textFieldCodigo.setText(String.valueOf(unProducto.getId()));
		textFieldCodigo.setEditable(false);
		Date fechaVencimiento = unProducto.getFechaVencimiento();
		if (fechaVencimiento != null)
			this.textFechaVencimiento.setText(formatoFecha.format(fechaVencimiento));
		else 
			this.textFechaVencimiento.setText("");
		
//		Set<Entry<String, Double>> precios = unProducto.getPreciosSet();
		// DefaultTableModel model = (DefaultTableModel) tablePrecios.getModel();
//		model.setRowCount(0);
//
//		for (Entry<String, Double> unPrecio : precios) {
//			model.addRow(new Object[] { unPrecio.getKey(), unPrecio.getValue() });
//		}
		textAreaDescripcion.setText(unProducto.getDescripcion());
		textFieldCategoria.setText(unProducto.getCategoria());
		if (unProducto.getCosto() == null)
			textFieldCosto.setText("0");
		else
			textFieldCosto.setText(unProducto.getCosto().toString());

		
		textFieldCosto.setVisible(true);
		lblCosto.setVisible(true);
		lblFechaDeVencimiento.setVisible(true);
		textFechaVencimiento.setVisible(true);
		
	}

	private void initComponents() {
		setTitle("Producto");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 454, 218);
		panelProductos = new JPanel();
		panelProductos.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelProductos);
		panelProductos.setLayout(null);

		lblCodigoLabel = new JLabel("CÓDIGO");
		lblCodigoLabel.setBounds(31, 17, 66, 14);
		lblCodigoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblCodigoLabel);

		textFieldCodigo = new JTextField();
		textFieldCodigo.setBounds(107, 17, 86, 20);
		textFieldCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCodigo.setColumns(10);
		textFieldCodigo.setFont(Constants.FUENTE);
		panelProductos.add(textFieldCodigo);

		lblFechaDeVencimiento = new JLabel("VENCIMIENTO");
		lblFechaDeVencimiento.setVisible(false);
		lblFechaDeVencimiento.setBounds(10, 81, 87, 14);
		lblFechaDeVencimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblFechaDeVencimiento);

		textFechaVencimiento = new JFormattedTextField(Constants.getMascaraFecha());
		textFechaVencimiento.setVisible(false);
		textFechaVencimiento.setBounds(107, 78, 86, 20);
		textFechaVencimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		textFechaVencimiento.setToolTipText("DD/MM/AAAA");
		textFechaVencimiento.setFont(Constants.FUENTE);
		panelProductos.add(textFechaVencimiento);
		textFechaVencimiento.setColumns(10);
		;

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
		btnGuardar.setBounds(276, 126, 127, 41);
		panelProductos.add(btnGuardar);

		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBounds(139, 126, 127, 41);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panelProductos.add(btnCancelar);

		JLabel lblDescripcion = new JLabel("DESCRIPCIÓN");
		lblDescripcion.setBounds(10, 51, 87, 14);
		lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblDescripcion);

		textAreaDescripcion = new JTextField();
		textAreaDescripcion.setBounds(107, 48, 296, 19);
		textAreaDescripcion.setColumns(10);
		panelProductos.add(textAreaDescripcion);

		lblCategoria = new JLabel("CATEGORÍA");
		lblCategoria.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCategoria.setBounds(203, 17, 81, 17);
		panelProductos.add(lblCategoria);

		textFieldCategoria = new JTextField();
		textFieldCategoria.setColumns(10);
		textFieldCategoria.setBounds(294, 14, 109, 20);
		panelProductos.add(textFieldCategoria);

		textFieldCosto = new JTextField();
		textFieldCosto.setVisible(false);
		textFieldCosto.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCosto.setColumns(10);
		textFieldCosto.setBounds(294, 78, 109, 20);
		panelProductos.add(textFieldCosto);

		lblCosto = new JLabel("COSTO");
		lblCosto.setVisible(false);
		lblCosto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCosto.setBounds(218, 81, 66, 14);
		panelProductos.add(lblCosto);

	}

	private void configValidations() {
		textFieldCodigo.setInputVerifier(new NumeroVerifier("Código", camposInvalidos));
		textAreaDescripcion.setInputVerifier(new TextoVerifier("Descripción", camposInvalidos));
		textFieldCategoria.setInputVerifier(new TextoVerifier("Categoria", camposInvalidos));
		textFieldCosto.setInputVerifier(new DoubleVerifier("Costo", camposInvalidos,false));
		textFechaVencimiento.setInputVerifier(new FechaVerifier("Fecha de Vencimiento", camposInvalidos));
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
				String text = textFechaVencimiento.getText();
				parse = formatoFecha.parse(text);
			} catch (Exception e) {
			}
			Producto build = Producto.builder().fechaVencimiento(parse).descripcion(textAreaDescripcion.getText())
					.activo(true).id(Integer.parseInt(textFieldCodigo.getText()))
					.costo(Double.parseDouble(textFieldCosto.getText()))
					.categoria(textFieldCategoria.getText()).build();
			return build;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public void limpiarCampos() {
		textFieldCodigo.setEditable(true);
		textFechaVencimiento.setText("");
		textAreaDescripcion.setText("");
		textFieldCodigo.setText("");
		textFieldCategoria.setText("");
		textFieldCosto.setText("");
		
		textFieldCosto.setVisible(false);
		lblCosto.setVisible(false);
		lblFechaDeVencimiento.setVisible(false);
		textFechaVencimiento.setVisible(false);
		
		camposInvalidos.clear();
	}

	public boolean validar() {
		textFechaVencimiento.requestFocus();
		textFieldCategoria.requestFocus();
		textFieldCosto.requestFocus();
		textAreaDescripcion.requestFocus();
		textFieldCodigo.requestFocus();
		if (camposInvalidos.size() > 0) {
			JOptionPane.showMessageDialog(new JFrame(), "Campos invalidos:" + camposInvalidos, "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;

	}
}
