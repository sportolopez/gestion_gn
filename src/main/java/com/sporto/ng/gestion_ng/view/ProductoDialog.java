package com.sporto.ng.gestion_ng.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.sporto.ng.gestion_ng.model.Lista;
import com.sporto.ng.gestion_ng.model.Producto;
import com.sporto.ng.gestion_ng.view.validations.FechaVerifier;
import com.sporto.ng.gestion_ng.view.validations.NumeroVerifier;
import com.sporto.ng.gestion_ng.view.validations.TextoVerifier;

import lombok.Getter;

@Getter
public class ProductoDialog extends JDialog {

	private JPanel panelProductos;
	private JLabel lblFechaDeVencimiento;
	private JLabel lblCodigoLabel;
	private JTextField textFieldCodigo;
	private JTextField textFechaVencimiento;
	private JLabel lblStock;
	private JTextField textFieldStock;
	private JTable tablePrecios;
	private JScrollPane scrollPane;
	private JButton btnGuardar;
	private JTextField textAreaDescripcion;
	private DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	private java.util.Set<String> camposInvalidos = new HashSet<String>();

	/**
	 * Create the frame.
	 * 
	 * @param abstractAction
	 */

	public ProductoDialog() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\sebap\\git\\gestion_ng\\src\\main\\resources\\logo gn-02.png"));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		initComponents();
		configValidations();
	}

	public void cargarCampos(Producto unProducto, Iterable<Lista> iterable) {
		this.textFieldCodigo.setText(String.valueOf(unProducto.getId()));
		textFieldCodigo.setEditable(false);
		this.textFieldStock.setText(String.valueOf(unProducto.getStock()));
		Date fechaVencimiento = unProducto.getFechaVencimiento();
		if (fechaVencimiento != null)
			this.textFechaVencimiento.setText(formatoFecha.format(fechaVencimiento));
		Set<Entry<String, Double>> precios = unProducto.getPreciosSet();
		DefaultTableModel model = (DefaultTableModel) tablePrecios.getModel();
		model.setRowCount(0);

		for (Entry<String, Double> unPrecio : precios) {
			model.addRow(new Object[] { unPrecio.getKey(), unPrecio.getValue() });
		}
		textAreaDescripcion.setText(unProducto.getDescripcion());
	}

	private void initComponents() {
		setTitle("Producto");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 447, 350);
		panelProductos = new JPanel();
		panelProductos.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelProductos);
		panelProductos.setLayout(null);

		lblCodigoLabel = new JLabel("Código");
		lblCodigoLabel.setBounds(31, 17, 49, 14);
		lblCodigoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblCodigoLabel);

		textFieldCodigo = new JTextField();
		textFieldCodigo.setBounds(90, 14, 86, 20);
		textFieldCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCodigo.setColumns(10);
		panelProductos.add(textFieldCodigo);

		lblFechaDeVencimiento = new JLabel("Vencimiento");
		lblFechaDeVencimiento.setBounds(10, 45, 70, 14);
		lblFechaDeVencimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblFechaDeVencimiento);

		MaskFormatter mascara;
		try {
			mascara = new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		textFechaVencimiento = new JFormattedTextField(mascara);
		textFechaVencimiento.setBounds(90, 42, 86, 20);
		textFechaVencimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		textFechaVencimiento.setToolTipText("DD/MM/AAAA");
		panelProductos.add(textFechaVencimiento);
		textFechaVencimiento.setColumns(10);

		lblStock = new JLabel("Stock");
		lblStock.setBounds(205, 45, 49, 14);
		lblStock.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblStock);

		textFieldStock = new JTextField();
		textFieldStock.setBounds(264, 42, 86, 20);
		textFieldStock.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldStock.setColumns(10);
		panelProductos.add(textFieldStock);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 86, 374, 141);
		panelProductos.add(scrollPane);

		tablePrecios = new JTable();
		scrollPane.setViewportView(tablePrecios);
		tablePrecios.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Lista", "Precio" }) {
			Class[] columnTypes = new Class[] { String.class, Double.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		JTextField textBox = new JTextField();
		tablePrecios.getColumnModel().getColumn(1).setCellEditor(new DoubleEditor());
//		textBox.addKeyListener(new KeyAdapter() {
//			public void keyTyped(KeyEvent e) {
//				
//				
//				if (!Character.isDigit(e.getKeyChar()) ||  && e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
//					textBox.setEditable(false);
//					textBox.setBackground(Color.WHITE);
//				} else {
//					textBox.setEditable(true);
//				}
//			}
//			
//			@Override
//			public void keyReleased(KeyEvent e) {
//				// TODO Auto-generated method stub
//				super.keyReleased(e);
//			}
//			
//		});

		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGuardar.setBounds(316, 261, 89, 23);
		panelProductos.add(btnGuardar);

		JButton btnNewButton_1 = new JButton("Cancelar");
		btnNewButton_1.setBounds(215, 261, 89, 23);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panelProductos.add(btnNewButton_1);

		JLabel lblDescripcion = new JLabel("Descripción");
		lblDescripcion.setBounds(184, 17, 70, 14);
		lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblDescripcion);

		textAreaDescripcion = new JTextField();
		textAreaDescripcion.setBounds(264, 12, 143, 19);
		textAreaDescripcion.setColumns(10);
		panelProductos.add(textAreaDescripcion);

	}

	private void configValidations() {
		textFechaVencimiento.setInputVerifier(new FechaVerifier("Fecha de Vencimiento", camposInvalidos));
		textFieldCodigo.setInputVerifier(new NumeroVerifier("Código", camposInvalidos));
		textFieldStock.setInputVerifier(new NumeroVerifier("Stock", camposInvalidos));
		textAreaDescripcion.setInputVerifier(new TextoVerifier("Descripción", camposInvalidos));
	}

	private void setTabOrder() {
	}

	public Producto getProducto() {
		try {

			Map<String, Double> preciosMap = new HashMap<String, Double>();

			for (int count = 0; count < tablePrecios.getRowCount(); count++) {
				preciosMap.put(tablePrecios.getValueAt(count, 0).toString(),
						Double.parseDouble(tablePrecios.getValueAt(count, 1).toString()));
			}

			Producto build = Producto.builder().fechaVencimiento(formatoFecha.parse(textFechaVencimiento.getText()))
					.descripcion(textAreaDescripcion.getText()).activo(true)
					.id(Integer.parseInt(textFieldCodigo.getText())).stock(Integer.parseInt(textFieldStock.getText()))
					.precios(preciosMap).build();
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
		textFieldStock.setText("");
		((DefaultTableModel) tablePrecios.getModel()).setRowCount(0);
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
