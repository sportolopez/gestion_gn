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
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

import org.apache.logging.log4j.util.Strings;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.dao.PedidoDao;
import com.sporto.ng.gestion_gn.dao.PedidoProductoDao;
import com.sporto.ng.gestion_gn.dao.PrecioDao;
import com.sporto.ng.gestion_gn.dao.ProductoDao;
import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.EstadoPedido;
import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.model.Pedido;
import com.sporto.ng.gestion_gn.model.PedidoProducto;
import com.sporto.ng.gestion_gn.model.Precio;
import com.sporto.ng.gestion_gn.model.PrecioId;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.pruebas.Impresora;
import com.sporto.ng.gestion_gn.utils.Java2sAutoComboBox;
import com.sporto.ng.gestion_gn.utils.Java2sAutoTextField;
import com.sporto.ng.gestion_gn.view.model.PedidoProductoTable;
import com.sporto.ng.gestion_gn.view.model.PedidoProductoTableModel;
import com.sporto.ng.gestion_gn.view.validations.FechaVerifier;
import com.sporto.ng.gestion_gn.view.validations.NumeroVerifier;

public class PedidoDialog extends JDialog {
	private PedidoProductoTable table;
	private JTextField textFieldCantidad;
	private JFormattedTextField textFieldVencimiento;
	private JButton botonGuardar;
	private java.util.Set<String> camposInvalidos = new HashSet<String>();
	private Java2sAutoComboBox textCodigoProducto;
	private JTextField textFieldStockActual;
	private JTextField textFieldLista;
	private JTextField textFieldTipoCliente;
	private PrecioDao precioDao;
	private ProductoDao productoDao;
	private JTextField textFieldCliente;
	private JTextField textFieldPrecio;
	private List<Precio> listaPrecios;
	private Cliente cliente;
	private JTextField comboBoxDescuento;
	private PedidoProductoDao pedidoProductoDao;
	private PedidoDao pedidoDao;
	private JTextField textFieldTotal;
	JDialog _self;
	private JTextField textFieldSaldo;
	private JPanel panelAgregar;
	private JLabel lblTitulo;
	private Pedido unPedidoEditar;
	private JButton btnImprimir;

	public PedidoDialog(ProductoDao productoDao, PedidoDao pedidoDao, JFrame owner, PrecioDao precioDao,
			PedidoProductoDao pedidoProductoDao) {
		super(owner);
		this.pedidoProductoDao = pedidoProductoDao;
		setResizable(false);
		this._self = this;
		this.precioDao = precioDao;
		this.pedidoDao = pedidoDao;
		this.productoDao = productoDao;
		URL resource = getClass().getClassLoader().getResource("icono.ico");
		setIconImage(Toolkit.getDefaultToolkit().getImage(resource));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 678, 533);
		setTitle("NUEVO PEDIDO");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		lblTitulo = new JLabel("NUEVO PEDIDO");
		lblTitulo.setFont(Constants.FUENTE_TITULO);
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(lblTitulo);

		JPanel panelCliente = new JPanel();
		getContentPane().add(panelCliente);

		JLabel lblNewLabel_1_1 = new JLabel("CLIENTE");
		panelCliente.add(lblNewLabel_1_1);

		textFieldCliente = new JTextField();
		textFieldCliente.setEditable(false);
		textFieldCliente.setColumns(18);
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

		JLabel lblNewLabel_3_2_1 = new JLabel("SALDO: ");
		panelCliente.add(lblNewLabel_3_2_1);

		textFieldSaldo = new JTextField();
		textFieldSaldo.setEditable(false);
		textFieldSaldo.setColumns(8);
		panelCliente.add(textFieldSaldo);

		JPanel panel = new JPanel();
		getContentPane().add(panel);

		JLabel lblProductos = new JLabel("PRODUCTOS");
		lblProductos.setFont(Constants.FUENTE_SUB_TITULO);
		lblProductos.setAlignmentX(0.5f);
		panel.add(lblProductos);

		panelAgregar = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelAgregar.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panelAgregar);

		JLabel lblNewLabel_1 = new JLabel("CÓDIGO");
		panelAgregar.add(lblNewLabel_1);

		List<String> myWords = productoDao.findAll().stream().map(Producto::getDescripcionCombo)
				.collect(Collectors.toList());
		myWords = new ArrayList<>(myWords);
		myWords.add(0, "");
		textCodigoProducto = new Java2sAutoComboBox(myWords);
		textCodigoProducto.getEditor().getEditorComponent().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				onProductoChange((String) ((Java2sAutoTextField) e.getSource()).getText());
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textCodigoProducto.addActionListener(
				e -> onProductoChange(((Java2sAutoComboBox) e.getSource()).getSelectedItem().toString()));

		panelAgregar.add(textCodigoProducto);

		JLabel lblNewLabel_3_1 = new JLabel("STOCK:");
		panelAgregar.add(lblNewLabel_3_1);

		textFieldStockActual = new JTextField();
		textFieldStockActual.setEditable(false);
		textFieldStockActual.setColumns(8);
		panelAgregar.add(textFieldStockActual);

		JLabel lblNewLabel_3_1_2 = new JLabel("PRECIO:");
		panelAgregar.add(lblNewLabel_3_1_2);

		textFieldPrecio = new JTextField();
		textFieldPrecio.setEditable(false);
		textFieldPrecio.setColumns(8);
		panelAgregar.add(textFieldPrecio);

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
		agregarStockBtn.addActionListener(registrarMovimiento());

		JLabel lblNewLabel_3_3 = new JLabel("DESCUENTO");
		panelAgregar.add(lblNewLabel_3_3);

		comboBoxDescuento = new JTextField();
		comboBoxDescuento.setColumns(3);
//		comboBoxDescuento.setModel(new DefaultComboBoxModel(
//				new String[] { "0 %", "1 %", "2 %", "3 %", "4 %", "5 %", "6 %", "7 %", "8 %", "9 %" }));
		panelAgregar.add(comboBoxDescuento);
		
		JLabel lblNewLabel_3_3_1 = new JLabel("%");
		panelAgregar.add(lblNewLabel_3_3_1);
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
				if (col == PedidoProductoTableModel.COLUMNA_ELIMINAR) {
					((DefaultTableModel) table.getModel()).removeRow(row);
					textFieldTotal.setText("$ " + Constants.outDouble(table.getTotal()));
				}

			}
		});

		scrollPane.setViewportView(table);

		JPanel panelTotal = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panelTotal.getLayout();
		flowLayout_2.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panelTotal);

		JLabel lblNewLabel_2 = new JLabel("TOTAL:");
		panelTotal.add(lblNewLabel_2);

		textFieldTotal = new JTextField();
		textFieldTotal.setEditable(false);
		textFieldTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		panelTotal.add(textFieldTotal);
		textFieldTotal.setColumns(10);
		getContentPane().add(panelBotones);

		JButton btnNewButton = new JButton("CANCELAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		btnImprimir = new JButton("IMPRIMIR");

		panelBotones.add(btnImprimir);
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

	private void onProductoChange(String idSelected) {
		try {
			if (Strings.isEmpty(idSelected)) {
				textFieldStockActual.setText("");
				textFieldPrecio.setText("");
				return;
			}
			Producto findById = obtenerProducto(idSelected);
			precargarStock(findById);
			precargarPrecio(findById, cliente.getListaPrecio());
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}

	protected void precargarPrecio(Producto unProducto, Lista listaPrecio) {
		if (null == unProducto) {
			textFieldPrecio.setText("");
		} else {
			PrecioId build = PrecioId.builder().producto(unProducto.getId()).lista(listaPrecio.getNombre()).build();
			Optional<Precio> unPrecio = precioDao.findById(build);
			if (!unPrecio.isPresent()) {
				JOptionPane.showMessageDialog(this,
						"El producto no tiene precio en esa lista, por favor carguelo y vuelva a probar", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			textFieldPrecio.setText(Constants.outDouble(unPrecio.get().getPrecio()));
		}

	}

	private void precargarStock(Producto unProducto) {
		if (null == unProducto) {
			textFieldStockActual.setText("");
		} else {
			Integer cantidadEnTabla = table.getCantidadEnTabla(unProducto.getId());
			int neto = unProducto.getStock()- cantidadEnTabla;
			textFieldStockActual.setText(Integer.toString(neto));
		}
	}

	private ActionListener registrarMovimiento() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String selectedItem = (String) textCodigoProducto.getSelectedItem();
				if (Strings.isNotEmpty(selectedItem)) {

					if (validar()) {
						Producto unProducto = obtenerProducto(selectedItem);

						Object descuentoSeleccionado = comboBoxDescuento.getText();
						table.registrarMovimiento(unProducto, Integer.valueOf(textFieldCantidad.getText()),
								Constants.parseDouble(textFieldPrecio.getText()), descuentoSeleccionado.toString());

						textFieldTotal.setText(Constants.outDouble(table.getTotal()));

						limpiarCampos();
					}

				}
			}

		};
	}

	private Producto obtenerProducto(String selectedItem) {
		return productoDao.findById(Integer.valueOf(selectedItem.split(":")[0])).get();
	}

	public void limpiarCampos() {
		textFieldCantidad.setText("");
		textFieldVencimiento.setValue(null);
		textCodigoProducto.setSelectedIndex(0);
		textFieldStockActual.setText("");
		textFieldPrecio.setText("");
		textCodigoProducto.setSelectedIndex(0);
		comboBoxDescuento.setText("0");
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
		comboBoxDescuento.setInputVerifier(new NumeroVerifier("Descuento", camposInvalidos, -1, 100));
	}

	public boolean validar() {
		textFieldVencimiento.requestFocus();
		textFieldCantidad.requestFocus();
		comboBoxDescuento.requestFocus();
		botonGuardar.requestFocus();
		if (camposInvalidos.size() > 0) {
			JOptionPane.showMessageDialog(new JFrame(), "Campos invalidos:" + camposInvalidos, "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;

	}

	public void guardarMovimientos() {
		PedidoProductoTableModel tableModel = (PedidoProductoTableModel) table.getModel();
		int nRow = tableModel.getRowCount();

		if (nRow == 0) {
			JOptionPane.showMessageDialog(this, "Ingrese al menos un producto", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {

			Pedido unPedido = Pedido.builder().cliente(cliente).estado(EstadoPedido.EMITIDO).fecha(new Date()).build();
			pedidoDao.save(unPedido);
			Collection<PedidoProducto> pedidoProductos = tableModel.getPedidoProductos(unPedido);
			pedidoProductoDao.saveAll(pedidoProductos);

			imprimir(unPedido, pedidoProductos);

		} catch (NumberFormatException e) {
			throw new RuntimeException(e);
		}

	}

	private void imprimir(Pedido unPedido, Collection<PedidoProducto> productos) {
		Impresora impresora = new Impresora();
		impresora.imprimirPedido(unPedido, productos);
		impresora.setVisible(true);

		JOptionPane.showMessageDialog(this, "El pedido " + unPedido.getId() + " fue registrado con éxito.", " ",
				JOptionPane.INFORMATION_MESSAGE);
		this.setVisible(false);
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
		this.cliente = unCliente;
		textFieldCliente.setText(unCliente.getRazonSocial());
		textFieldLista.setText(unCliente.getListaPrecio().getNombre());
		textFieldTipoCliente.setText(unCliente.getTipoCuenta().name());
		textFieldTotal.setText("");
		textFieldSaldo.setText(Constants.outDouble(unCliente.getSaldo()));

		textCodigoProducto.setSelectedIndex(0);
		textFieldCantidad.setText("");
		comboBoxDescuento.setText("0");
		listaPrecios = precioDao.findByLista(unCliente.getListaPrecio());
		((PedidoProductoTableModel) table.getModel()).setRowCount(0);
		this.setVisible(true);
	}

	public void cargarPedido(Pedido unPedido) {
		this.unPedidoEditar = unPedido;
		Collection<PedidoProducto> listaProductosEditar = pedidoProductoDao.findByPedido(unPedido);
		Cliente unCliente = unPedido.getCliente();
		textFieldCliente.setText(unCliente.getRazonSocial());
		textFieldLista.setText(unCliente.getListaPrecio().getNombre());
		textFieldTipoCliente.setText(unCliente.getTipoCuenta().name());
		textFieldSaldo.setText(Constants.outDouble(unCliente.getSaldo()));
		((PedidoProductoTableModel) table.getModel()).setRowCount(0);
		((PedidoProductoTableModel) table.getModel()).addAll(listaProductosEditar);
		textFieldTotal.setText(Constants.outDouble(table.getTotal()));
		botonGuardar.setVisible(false);
		table.ocultarColumnaEliminar();
		panelAgregar.setVisible(false);
		lblTitulo.setText("DETALLE PEDIDO " + unPedido.getId());
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Se hace click en imprimir");
				Impresora impresora = new Impresora();
				impresora.reimprimirPedido(unPedido, listaProductosEditar);
				impresora.setVisible(true);
			}
		});
		this.setVisible(true);

	}
}
