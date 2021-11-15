package com.sporto.ng.gestion_gn.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.logging.log4j.util.Strings;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.dao.MovimientoStockDao;
import com.sporto.ng.gestion_gn.dao.ProductoDao;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.model.TipoMovimiento;
import com.sporto.ng.gestion_gn.utils.Java2sAutoComboBox;
import com.sporto.ng.gestion_gn.utils.Java2sAutoTextField;
import com.sporto.ng.gestion_gn.view.model.MovimientoStockTable;
import com.sporto.ng.gestion_gn.view.validations.FechaVerifier;
import com.sporto.ng.gestion_gn.view.validations.NumeroVerifier;

public class MovimientoStockDialog extends JDialog {
	private int columnaBorrar = 4;
	private MovimientoStockTable table;
	private JTextField textFieldCantidad;
	private JFormattedTextField textFieldVencimiento;
	private JButton botonGuardar;
	private java.util.Set<String> camposInvalidos = new HashSet<String>();
	private Java2sAutoComboBox textCodigoProducto;
	List<Producto> listaProductos;
	MovimientoStockDao movimientoDao;
	ProductoDao productoDao;
	private JTextField textFieldStockActual;
	private JTextField textFieldComentario;

	public MovimientoStockDialog(List<Producto> listaProductos, MovimientoStockDao movimientoDao,
			ProductoDao productoDao, TipoMovimiento tipoMovimiento, JFrame owner) {
		super(owner);
		setResizable(false);
		this.listaProductos = listaProductos;
		this.movimientoDao = movimientoDao;
		this.productoDao = productoDao;
		URL resource = getClass().getClassLoader().getResource("icono.ico");
		setIconImage(Toolkit.getDefaultToolkit().getImage(resource));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 678, 533);
		setTitle("Ingresar Stock");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JLabel lblNewLabel = new JLabel(tipoMovimiento.name() + " STOCK");
		lblNewLabel.setFont(Constants.FUENTE_TITULO);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(lblNewLabel);

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

		JLabel lblNewLabel_3_1 = new JLabel("Stock:");
		panelAgregar.add(lblNewLabel_3_1);

		textFieldStockActual = new JTextField();
		textFieldStockActual.setEditable(false);
		textFieldStockActual.setColumns(8);
		panelAgregar.add(textFieldStockActual);

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
		if (tipoMovimiento.equals(TipoMovimiento.INGRESO)) {
			panelAgregar.add(lblVencimiento);
			panelAgregar.add(textFieldVencimiento);
			columnaBorrar = 4;
		} else
			columnaBorrar = 3;
		textFieldVencimiento.setColumns(10);

		JButton agregarStockBtn = new JButton("REGISTRAR " + tipoMovimiento.name());
		agregarStockBtn.addActionListener(registrarMovimiento(listaProductos, tipoMovimiento));
		
		JLabel lblNewLabel_3_2 = new JLabel("COMENTARIO:");
		panelAgregar.add(lblNewLabel_3_2);
		
		textFieldComentario = new JTextField();
		textFieldComentario.setColumns(10);
		panelAgregar.add(textFieldComentario);
		panelAgregar.add(agregarStockBtn);

		JPanel panelBotones = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBotones.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		table = new MovimientoStockTable(tipoMovimiento);

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
				guardarMovimientos(tipoMovimiento);
			}
		});
		panelBotones.add(botonGuardar);
		configValidations();
		pack();

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

	private ActionListener registrarMovimiento(List<Producto> listaProductos, TipoMovimiento tipoMovimiento) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String selectedItem = (String) textCodigoProducto.getSelectedItem();
				if (Strings.isNotEmpty(selectedItem)) {

					if (validar()) {
						Producto unProducto = listaProductos.stream()
								.filter(producto -> producto.getId() == Integer.parseInt(selectedItem.split(":")[0]))
								.findAny().get();

						// Si es egreso valido que no quede negativo

						table.registrarMovimiento(tipoMovimiento, unProducto,
								Integer.valueOf(textFieldCantidad.getText()), textFieldVencimiento.getText(),textFieldComentario.getText());

						limpiarCampos();
					}

				}
			}
		};
	}

	private void limpiarCampos() {
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

	public void guardarMovimientos(TipoMovimiento tipoMovimiento) {
		TableModel tableModel = table.getModel();
		int nRow = tableModel.getRowCount();

		if (nRow == 0) {
			JOptionPane.showMessageDialog(this, "Ingrese al menos un movimiento", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Integer remitoInt = null;
		if (tipoMovimiento.equals(TipoMovimiento.INGRESO)) {
			String nroRemito = JOptionPane.showInputDialog(this, "Ingrese el número de remito", "Remito",
					JOptionPane.QUESTION_MESSAGE);
			if (nroRemito == null)
				return;
			while (!isNumeric(nroRemito)) {
				nroRemito = JOptionPane.showInputDialog(this, "Ingrese el número de remito", "Remito",
						JOptionPane.QUESTION_MESSAGE);
				if (nroRemito == null)
					return;
			}
			remitoInt = Integer.parseInt(nroRemito);

		}
		String ordenCompra = null;
		if (tipoMovimiento.equals(TipoMovimiento.INGRESO)) {
			String nroIngresado = JOptionPane.showInputDialog(this, "Ingrese la orden de compra", "Orden de compra",
					JOptionPane.QUESTION_MESSAGE);
			if (nroIngresado == null)
				return;
			while (!Strings.isNotBlank(nroIngresado)) {
				nroIngresado = JOptionPane.showInputDialog(this, "Ingrese la orden de compra", "Orden de compra",
						JOptionPane.QUESTION_MESSAGE);
				if (nroIngresado == null)
					return;
			}
			ordenCompra = (nroIngresado);

		}

		try {
			for (int i = 0; i < nRow; i++) {
				int parseInt = Integer.parseInt(tableModel.getValueAt(i, 0).toString());
				Producto unProducto = listaProductos.stream().filter(producto -> producto.getId() == parseInt).findAny()
						.get();
				Date fechaV = null;
				if (tipoMovimiento.equals(TipoMovimiento.INGRESO)) {
					fechaV = Constants.FORMATO_FECHA.parse((tableModel.getValueAt(i, 3).toString()));
					if (unProducto.getFechaVencimiento() == null || unProducto.getFechaVencimiento().before(fechaV)) {
						unProducto.setFechaVencimiento(fechaV);
						productoDao.save(unProducto);
					}
				}

				Integer cantidad = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
				com.sporto.ng.gestion_gn.model.MovimientoStock movimiento = com.sporto.ng.gestion_gn.model.MovimientoStock
						.builder().cantidad(cantidad).fecha(new Date()).tipoMovimiento(tipoMovimiento).fechaVencimiento(fechaV)
						.producto(unProducto).remito(remitoInt).ordenCompra(ordenCompra).comentario(textFieldComentario.getText()).build();
				movimientoDao.save(movimiento);

			}
			JOptionPane.showMessageDialog(this, "Los movimientos se registraron correctamente");
			setVisible(false);
		} catch (NumberFormatException | ParseException e) {
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

}
