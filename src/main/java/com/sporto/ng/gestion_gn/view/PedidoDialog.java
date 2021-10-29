package com.sporto.ng.gestion_gn.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.logging.log4j.util.Strings;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.dao.PedidoDao;
import com.sporto.ng.gestion_gn.dao.PrecioDao;
import com.sporto.ng.gestion_gn.dao.ProductoDao;
import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.utils.Java2sAutoComboBox;
import com.sporto.ng.gestion_gn.utils.Java2sAutoTextField;
import com.sporto.ng.gestion_gn.view.model.PedidoProductoTable;
import com.sporto.ng.gestion_gn.view.validations.FechaVerifier;
import com.sporto.ng.gestion_gn.view.validations.NumeroVerifier;

public class PedidoDialog extends JDialog {
	private Integer columnaBorrar = 4;
	private PedidoProductoTable table;
	private JTextField textFieldCantidad;
	private JFormattedTextField textFieldVencimiento;
	private JButton botonGuardar;
	private java.util.Set<String> camposInvalidos = new HashSet<String>();
	private Java2sAutoComboBox textCodigoProducto;
	List<Producto> listaProductos;
	ProductoDao productoDao;
	private JTextField textFieldStockActual;
	private JTextField textFieldLista;
	private JTextField textFieldTipoCliente;
	private PrecioDao precioDao;
	private JTextField textFieldCliente;
	private JTextField textField;

	public PedidoDialog(List<Producto> listaProductos, PedidoDao pedidoDao, JFrame owner, PrecioDao precioDao) {
		super(owner);
		setResizable(false);
		this.precioDao = precioDao;
		this.listaProductos = listaProductos;
		
		URL resource = getClass().getClassLoader().getResource("icono.ico");
		setIconImage(Toolkit.getDefaultToolkit().getImage(resource));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 678, 533);
		setTitle("Ingresar Stock");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JLabel lblNewLabel = new JLabel("NUEVO PEDIDO");
		lblNewLabel.setFont(Constants.FUENTE_TITULO);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(lblNewLabel);
		
		JPanel panelCliente = new JPanel();
		getContentPane().add(panelCliente);
		
		JLabel lblNewLabel_1_1 = new JLabel("CLIENTE");
		panelCliente.add(lblNewLabel_1_1);
		
		textFieldCliente = new JTextField();
		textFieldCliente.setEditable(false);
		textFieldCliente.setColumns(8);
		panelCliente.add(textFieldCliente);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Lista:");
		panelCliente.add(lblNewLabel_3_1_1);
		
		textFieldLista = new JTextField();
		textFieldLista.setEditable(false);
		textFieldLista.setColumns(8);
		panelCliente.add(textFieldLista);
		
		JLabel lblNewLabel_3_2 = new JLabel("TIPO CUENTA:");
		panelCliente.add(lblNewLabel_3_2);
		
		textFieldTipoCliente = new JTextField();
		textFieldTipoCliente.setEditable(false);
		textFieldTipoCliente.setColumns(8);
		panelCliente.add(textFieldTipoCliente);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		
		JLabel lblProductos = new JLabel("PRODUCTOS");
		lblProductos.setFont(Constants.FUENTE_SUB_TITULO);
		lblProductos.setAlignmentX(0.5f);
		panel.add(lblProductos);

		JPanel panelAgregar = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelAgregar.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panelAgregar);

		JLabel lblNewLabel_1 = new JLabel("CÓDIGO");
		panelAgregar.add(lblNewLabel_1);

		List<String> myWords = listaProductos.stream().map(Producto::getDescripcionCombo).collect(Collectors.toList());
		myWords = new ArrayList<>(myWords);
		myWords.add(0, "");
		textCodigoProducto = new Java2sAutoComboBox(myWords);
		textCodigoProducto.getEditor().getEditorComponent().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				try {
					String idSelected = (String) ((Java2sAutoTextField) e.getSource()).getText();
					precargarStock(listaProductos, idSelected);
				} catch (Exception e1) {
					throw new RuntimeException(e1);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textCodigoProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String idSelected = (String) ((Java2sAutoComboBox) e.getSource()).getSelectedItem();
					precargarStock(listaProductos, idSelected);
				} catch (Exception e1) {
					throw new RuntimeException(e1);
				}
			}
		});

		panelAgregar.add(textCodigoProducto);

		JLabel lblNewLabel_3_1 = new JLabel("STOCK:");
		panelAgregar.add(lblNewLabel_3_1);

		textFieldStockActual = new JTextField();
		textFieldStockActual.setEditable(false);
		textFieldStockActual.setColumns(8);
		panelAgregar.add(textFieldStockActual);
		
		JLabel lblNewLabel_3_1_2 = new JLabel("PRECIO:");
		panelAgregar.add(lblNewLabel_3_1_2);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(8);
		panelAgregar.add(textField);

		JLabel lblNewLabel_3 = new JLabel("CANTIDAD");
		panelAgregar.add(lblNewLabel_3);

		textFieldCantidad = new JTextField();
		panelAgregar.add(textFieldCantidad);
		textFieldCantidad.setColumns(8);

		JLabel lblVencimiento = new JLabel("VENCIMIENTO");

		textFieldVencimiento = new JFormattedTextField(Constants.getMascaraFecha());
		textFieldVencimiento.setBounds(107, 45, 86, 20);
		textFieldVencimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldVencimiento.setToolTipText("DD/MM/AAAA");
		textFieldVencimiento.setFont(Constants.FUENTE);
		textFieldVencimiento.setColumns(10);

		JButton agregarStockBtn = new JButton("REGISTRAR ");
		agregarStockBtn.addActionListener(registrarMovimiento(listaProductos));
		
		JLabel lblNewLabel_3_3 = new JLabel("DESCUENTO");
		panelAgregar.add(lblNewLabel_3_3);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"0 %", "1 %", "2 %", "3 %", "4 %", "5 %", "6 %", "7 %", "8 %", "9 %"}));
		panelAgregar.add(comboBox);
		panelAgregar.add(agregarStockBtn);

		JPanel panelBotones = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBotones.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		table = new PedidoProductoTable();

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = table.rowAtPoint(evt.getPoint());
				int col = table.columnAtPoint(evt.getPoint());
				if (col == columnaBorrar) {
					((DefaultTableModel) table.getModel()).removeRow(row);
				}

			}
		});

		scrollPane.setViewportView(table);
		getContentPane().add(panelBotones);

		JButton btnNewButton = new JButton("CANCELAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panelBotones.add(btnNewButton);

		botonGuardar = new JButton("GUARDAR");
		botonGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarMovimientos();
			}
		});
		panelBotones.add(botonGuardar);
		configValidations();
		pack();

	}

	protected void precargarPrecio(List<Producto> listaProductos2, String idSelected) {
		if (Strings.isEmpty(idSelected)) {
			textFieldStockActual.setText("");
		} else {
			Producto unProducto = listaProductos.stream()
					.filter(producto -> producto.getId() == Integer.parseInt(idSelected.split(":")[0])).findAny().get();
			textFieldStockActual.setText(unProducto.getStock().toString());
		}
		
	}

	private void precargarStock(List<Producto> listaProductos, String idSelected) {
		if (Strings.isEmpty(idSelected)) {
			textFieldStockActual.setText("");
		} else {
			Producto unProducto = listaProductos.stream()
					.filter(producto -> producto.getId() == Integer.parseInt(idSelected.split(":")[0])).findAny().get();
			textFieldStockActual.setText(unProducto.getStock().toString());
		}
	}

	private ActionListener registrarMovimiento(List<Producto> listaProductos) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String selectedItem = (String) textCodigoProducto.getSelectedItem();
				if (Strings.isNotEmpty(selectedItem)) {

					if (validar()) {
						Producto unProducto = listaProductos.stream()
								.filter(producto -> producto.getId() == Integer.parseInt(selectedItem.split(":")[0]))
								.findAny().get();

						// Si es egreso valido que no quede negativo
 
						table.registrarMovimiento(unProducto, Integer.valueOf(textFieldCantidad.getText()),(double) 0);

						limpiarCampos();
					}

				}
			}
		};
	}

	public void limpiarCampos() {
		textFieldCantidad.setText("");
		textFieldVencimiento.setValue(null);
		textCodigoProducto.setSelectedIndex(0);
	}

	public JButton getBotonGuardar() {
		return botonGuardar;
	}

	public JTable getTablaMovimientos() {
		return table;
	}

	private void configValidations() {
		textFieldVencimiento.setInputVerifier(new FechaVerifier("Fecha de Vencimiento", camposInvalidos, true));
		textFieldCantidad.setInputVerifier(new NumeroVerifier("Cantidad", camposInvalidos, 0, 9999));
	}

	public boolean validar() {
		textFieldVencimiento.requestFocus();
		textFieldCantidad.requestFocus();
		botonGuardar.requestFocus();
		if (camposInvalidos.size() > 0) {
			JOptionPane.showMessageDialog(new JFrame(), "Campos invalidos:" + camposInvalidos, "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;

	}

	public void guardarMovimientos() {
		TableModel tableModel = table.getModel();
		int nRow = tableModel.getRowCount();

		if (nRow == 0) {
			JOptionPane.showMessageDialog(this, "Ingrese al menos un movimiento", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			for (int i = 0; i < nRow; i++) {
				int parseInt = Integer.parseInt(tableModel.getValueAt(i, 0).toString());
				Producto unProducto = listaProductos.stream().filter(producto -> producto.getId() == parseInt).findAny()
						.get();
				Date fechaV = null;
//				Integer cantidad = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
//				com.sporto.ng.gestion_gn.model.MovimientoStock movimiento = com.sporto.ng.gestion_gn.model.MovimientoStock
//						.builder().cantidad(cantidad).fecha(new Date()).tipoMovimiento(tipoMovimiento).fechaVencimiento(fechaV)
//						.producto(unProducto).remito(remitoInt).build();
//				movimientoDao.save(movimiento);

			}
			JOptionPane.showMessageDialog(this, "Los movimientos se registraron correctamente");
			setVisible(false);
		} catch (NumberFormatException e) {
			throw new RuntimeException(e);
		}

	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	public void nuevoPedido(Cliente unCliente) {
		textFieldCliente.setText(unCliente.getRazonSocial());
		textFieldLista.setText(unCliente.getListaPrecio().getNombre());
		textFieldTipoCliente.setText(unCliente.getTipoCuenta().name());
		this.setVisible(true);
	}

}
