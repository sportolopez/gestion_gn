package com.sporto.ng.gestion_ng.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_ng.model.Producto;

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
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnGuardar;
	private JTextArea textAreaDescripcion;
	private DateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
	private JButton btnAgregarPrecio;

	/**
	 * Create the frame.
	 * 
	 * @param abstractAction
	 */

	public ProductoDialog() {
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		initComponents();

	}

	public void cargarCampos(Producto unProducto) {
		this.textFieldCodigo.setText(String.valueOf(unProducto.getId()));
		textFieldCodigo.setEnabled(false);
		this.textFieldStock.setText(String.valueOf(unProducto.getStock()));
		Date fechaVencimiento = unProducto.getFechaVencimiento();
		if (fechaVencimiento != null)
			this.textFechaVencimiento.setText(formatoFecha.format(fechaVencimiento));
		Set<Entry<String, Double>> precios = unProducto.getPreciosSet();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (Entry<String, Double> unPrecio : precios) {
			model.addRow(new Object[] { unPrecio.getKey(), unPrecio.getValue() });

		}
		textAreaDescripcion.setText(unProducto.getDescripcion());
	}

	private void initComponents() {
		setTitle("Producto");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 434, 258);
		panelProductos = new JPanel();
		panelProductos.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelProductos);
		panelProductos.setLayout(null);

		lblCodigoLabel = new JLabel("Código");
		lblCodigoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigoLabel.setBounds(31, 17, 49, 14);
		panelProductos.add(lblCodigoLabel);

		textFieldCodigo = new JTextField();
		textFieldCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCodigo.setBounds(90, 14, 86, 20);
		panelProductos.add(textFieldCodigo);
		textFieldCodigo.setColumns(10);

		lblFechaDeVencimiento = new JLabel("Vencimiento");
		lblFechaDeVencimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFechaDeVencimiento.setBounds(10, 73, 70, 14);
		panelProductos.add(lblFechaDeVencimiento);

		textFechaVencimiento = new JTextField();
		textFechaVencimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		textFechaVencimiento.setToolTipText("12/12/1983");
		textFechaVencimiento.setBounds(90, 70, 86, 20);
		panelProductos.add(textFechaVencimiento);
		textFechaVencimiento.setColumns(10);

		lblStock = new JLabel("Stock");
		lblStock.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStock.setBounds(31, 45, 49, 14);
		panelProductos.add(lblStock);

		textFieldStock = new JTextField();
		textFieldStock.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldStock.setBounds(90, 42, 86, 20);
		textFieldStock.setColumns(10);
		panelProductos.add(textFieldStock);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(202, 10, 159, 91);
		panelProductos.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Lista", "Precio" }) {
			Class[] columnTypes = new Class[] { String.class, Float.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});

		btnGuardar = new JButton("Guardar");

		btnGuardar.setBounds(229, 192, 89, 23);
		panelProductos.add(btnGuardar);

		JButton btnNewButton_1 = new JButton("Cancelar");
		btnNewButton_1.setBounds(130, 192, 89, 23);
		panelProductos.add(btnNewButton_1);

		JLabel lblDescripcion = new JLabel("Descripción");
		lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescripcion.setBounds(10, 124, 70, 14);
		panelProductos.add(lblDescripcion);

		btnAgregarPrecio = new JButton("+");
		btnAgregarPrecio.setBounds(371, 41, 41, 23);
		panelProductos.add(btnAgregarPrecio);

		textAreaDescripcion = new JTextArea();
		textAreaDescripcion.setRows(5);
		textAreaDescripcion.setColumns(10);
		textAreaDescripcion.setBounds(90, 119, 271, 45);
		panelProductos.add(textAreaDescripcion);

		JButton btnNewButton_2_1 = new JButton("-");
		btnNewButton_2_1.setBounds(371, 69, 41, 23);
		panelProductos.add(btnNewButton_2_1);

	}

	public Producto getProducto()  {
		try {
			return Producto.builder()
				.fechaVencimiento(formatoFecha.parse(textFechaVencimiento.getText()))
				.descripcion(textAreaDescripcion.getText())
				.activo(true)
				.id(Integer.parseInt(textFieldCodigo.getText()))
				.stock(Integer.parseInt(textFieldStock.getText())).build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	public void limpiarCampos() {
		textFechaVencimiento.setText("");
		textAreaDescripcion.setText("");
		textFieldCodigo.setText("");
		textFieldStock.setText("");
	}
}
