//package com.sporto.ng.gestion_gn.view;
//
//import java.awt.Component;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.print.PageFormat;
//import java.awt.print.Printable;
//import java.awt.print.PrinterException;
//import java.awt.print.PrinterJob;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//
//import javax.swing.BoxLayout;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JDialog;
//import javax.swing.JFormattedTextField;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JTextField;
//import javax.swing.SwingConstants;
//import javax.swing.table.DefaultTableModel;
//
//import org.apache.logging.log4j.util.Strings;
//
//import com.sporto.ng.gestion_gn.config.Constants;
//import com.sporto.ng.gestion_gn.dao.PedidoDao;
//import com.sporto.ng.gestion_gn.dao.PedidoProductoDao;
//import com.sporto.ng.gestion_gn.dao.PrecioDao;
//import com.sporto.ng.gestion_gn.dao.ProductoDao;
//import com.sporto.ng.gestion_gn.model.Cliente;
//import com.sporto.ng.gestion_gn.model.EstadoPedido;
//import com.sporto.ng.gestion_gn.model.Lista;
//import com.sporto.ng.gestion_gn.model.MovimientoCaja;
//import com.sporto.ng.gestion_gn.model.Pedido;
//import com.sporto.ng.gestion_gn.model.PedidoProducto;
//import com.sporto.ng.gestion_gn.model.Precio;
//import com.sporto.ng.gestion_gn.model.PrecioId;
//import com.sporto.ng.gestion_gn.model.Producto;
//import com.sporto.ng.gestion_gn.model.TipoMovimiento;
//import com.sporto.ng.gestion_gn.pruebas.Impresora;
//import com.sporto.ng.gestion_gn.view.model.PedidoProductoTableModel;
//import com.sporto.ng.gestion_gn.view.model.PagoTableModel;
//import com.sporto.ng.gestion_gn.view.validations.FechaVerifier;
//
//public class PagoDialog extends JDialog {
//	private JTable table;
//	private JFormattedTextField textFieldVencimiento;
//	private JButton botonGuardar;
//	private java.util.Set<String> camposInvalidos = new HashSet<String>();
//	private JComboBox tipoIngreso;
//	private JTextField textFieldStockActual;
//	private JTextField textFieldLista;
//	private JTextField textFieldTipoCliente;
//	private PrecioDao precioDao;
//	private ProductoDao productoDao;
//	private JTextField textFieldCliente;
//	private JTextField textFieldPrecio;
//	private List<Precio> listaPrecios;
//	private Cliente cliente;
//	private PedidoProductoDao pedidoProductoDao;
//	private PedidoDao pedidoDao;
//	private JTextField textFieldTotal;
//
//	public PagoDialog(ProductoDao productoDao, PedidoDao pedidoDao, JFrame owner, PrecioDao precioDao,
//			PedidoProductoDao pedidoProductoDao) {
//		super(owner);
//		setResizable(false);
//		this.precioDao = precioDao;
//		this.pedidoDao = pedidoDao;
//		this.productoDao = productoDao;
//		this.pedidoProductoDao = pedidoProductoDao;
//		URL resource = getClass().getClassLoader().getResource("icono.ico");
//		setIconImage(Toolkit.getDefaultToolkit().getImage(resource));
//		this.setModalityType(ModalityType.APPLICATION_MODAL);
//		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//		setBounds(100, 100, 678, 533);
//		setTitle("Ingresar Stock");
//		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
//
//		JLabel lblNewLabel = new JLabel("REGISTRAR INGRESO");
//		lblNewLabel.setFont(Constants.FUENTE_TITULO);
//		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//		getContentPane().add(lblNewLabel);
//
//		JPanel panelCliente = new JPanel();
//		getContentPane().add(panelCliente);
//
//		JLabel lblNewLabel_1_1 = new JLabel("CLIENTE");
//		panelCliente.add(lblNewLabel_1_1);
//
//		textFieldCliente = new JTextField();
//		textFieldCliente.setEditable(false);
//		textFieldCliente.setColumns(8);
//		panelCliente.add(textFieldCliente);
//
//		JLabel lblNewLabel_3_1_1 = new JLabel("Lista:");
//		panelCliente.add(lblNewLabel_3_1_1);
//
//		textFieldLista = new JTextField();
//		textFieldLista.setEditable(false);
//		textFieldLista.setColumns(8);
//		panelCliente.add(textFieldLista);
//
//		JLabel lblNewLabel_3_2 = new JLabel("TIPO CUENTA:");
//		panelCliente.add(lblNewLabel_3_2);
//
//		textFieldTipoCliente = new JTextField();
//		textFieldTipoCliente.setEditable(false);
//		textFieldTipoCliente.setColumns(8);
//		panelCliente.add(textFieldTipoCliente);
//
//		JPanel panel = new JPanel();
//		getContentPane().add(panel);
//
//		JLabel lblProductos = new JLabel("PAGO");
//		lblProductos.setFont(Constants.FUENTE_SUB_TITULO);
//		lblProductos.setAlignmentX(0.5f);
//		panel.add(lblProductos);
//
//		JPanel panelAgregar = new JPanel();
//		FlowLayout flowLayout_1 = (FlowLayout) panelAgregar.getLayout();
//		flowLayout_1.setAlignment(FlowLayout.LEFT);
//		getContentPane().add(panelAgregar);
//
//		JLabel lblNewLabel_1 = new JLabel("TIPO PAGO");
//		panelAgregar.add(lblNewLabel_1);
//
//		
//		Object[] headers = { "EFECTIVO", "TRANSFERENCIA"};
//		tipoIngreso = new JComboBox(headers);
//
//		panelAgregar.add(tipoIngreso);
//
//		JLabel lblNewLabel_3_1 = new JLabel("MONTO:");
//		panelAgregar.add(lblNewLabel_3_1);
//
//		textFieldStockActual = new JTextField();
//		textFieldStockActual.setColumns(8);
//		panelAgregar.add(textFieldStockActual);
//
//		JLabel lblNewLabel_3_1_2 = new JLabel("COMENTARIO:");
//		panelAgregar.add(lblNewLabel_3_1_2);
//
//		textFieldPrecio = new JTextField();
//		textFieldPrecio.setColumns(12);
//		panelAgregar.add(textFieldPrecio);
//
//		JLabel lblVencimiento = new JLabel("VENCIMIENTO");
//
//		textFieldVencimiento = new JFormattedTextField(Constants.getMascaraFecha());
//		textFieldVencimiento.setBounds(107, 45, 86, 20);
//		textFieldVencimiento.setHorizontalAlignment(SwingConstants.RIGHT);
//		textFieldVencimiento.setToolTipText("DD/MM/AAAA");
//		textFieldVencimiento.setFont(Constants.FUENTE);
//		textFieldVencimiento.setColumns(10);
//
//		JButton agregarStockBtn = new JButton("REGISTRAR ");
//		panelAgregar.add(agregarStockBtn);
//
//		JPanel panelBotones = new JPanel();
//		FlowLayout flowLayout = (FlowLayout) panelBotones.getLayout();
//		flowLayout.setAlignment(FlowLayout.RIGHT);
//		
//		JPanel panelTotal_1 = new JPanel();
//		getContentPane().add(panelTotal_1);
//		
//		JLabel lblPedidosALiberar = new JLabel("PAGOS A REGISTRAR");
//		lblPedidosALiberar.setFont(new Font("Tahoma", Font.BOLD, 16));
//		lblPedidosALiberar.setAlignmentX(0.5f);
//		panelTotal_1.add(lblPedidosALiberar);
//
//		JScrollPane scrollPane = new JScrollPane();
//		getContentPane().add(scrollPane);
//
//		table = new JTable();
//		PagoTableModel dataModel = new PagoTableModel();
//		MovimientoCaja build = MovimientoCaja.builder()
//									.cliente(cliente)
//									.tipoMovimiento(TipoMovimiento.INGRESO)
//									.fecha(new Date()).build();
//		dataModel.addPago(build);
//		table.setModel(dataModel);
//
//		table.addMouseListener(new java.awt.event.MouseAdapter() {
//			@Override
//			public void mouseClicked(java.awt.event.MouseEvent evt) {
//				int row = table.rowAtPoint(evt.getPoint());
//				int col = table.columnAtPoint(evt.getPoint());
//				if (col == PedidoProductoTableModel.COLUMNA_ELIMINAR) {
//					((DefaultTableModel) table.getModel()).removeRow(row);
//				}
//
//			}
//		});
//
//		scrollPane.setViewportView(table);
//
//		JPanel panelTotal = new JPanel();
//		FlowLayout flowLayout_2 = (FlowLayout) panelTotal.getLayout();
//		flowLayout_2.setAlignment(FlowLayout.RIGHT);
//		getContentPane().add(panelTotal);
//
//		JLabel lblNewLabel_2 = new JLabel("TOTAL:");
//		panelTotal.add(lblNewLabel_2);
//
//		textFieldTotal = new JTextField();
//		textFieldTotal.setEnabled(false);
//		panelTotal.add(textFieldTotal);
//		textFieldTotal.setColumns(10);
//		getContentPane().add(panelBotones);
//
//		JButton btnNewButton = new JButton("CANCELAR");
//		btnNewButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				setVisible(false);
//			}
//		});
//		panelBotones.add(btnNewButton);
//
//		botonGuardar = new JButton("GUARDAR");
//		botonGuardar.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				guardarMovimientos();
//			}
//		});
//		panelBotones.add(botonGuardar);
//		configValidations();
//		pack();
//
//	}
//
//	public void printComponenet(Component component) {
//		PrinterJob pj = PrinterJob.getPrinterJob();
//		pj.setJobName(" Print Component ");
//
//		pj.setPrintable(new Printable() {
//			public int print(Graphics pg, PageFormat pf, int pageNum) {
//				if (pageNum > 0) {
//					return Printable.NO_SUCH_PAGE;
//				}
//
//				Graphics2D g2 = (Graphics2D) pg;
//				g2.translate(pf.getImageableX(), pf.getImageableY());
//				component.paint(g2);
//				return Printable.PAGE_EXISTS;
//			}
//		});
//		if (pj.printDialog() == false)
//			return;
//
//		try {
//			pj.print();
//		} catch (PrinterException ex) {
//			// handle exception
//		}
//	}
//
//	private void onProductoChange(String idSelected) {
//		try {
//			if (Strings.isEmpty(idSelected)) {
//				textFieldStockActual.setText("");
//				textFieldPrecio.setText("");
//				return;
//			}
//			Producto findById = obtenerProducto(idSelected);
//			precargarStock(findById);
//			precargarPrecio(findById, cliente.getListaPrecio());
//		} catch (Exception e1) {
//			throw new RuntimeException(e1);
//		}
//	}
//
//	protected void precargarPrecio(Producto unProducto, Lista listaPrecio) {
//		if (null == unProducto) {
//			textFieldPrecio.setText("");
//		} else {
//			PrecioId build = PrecioId.builder().producto(unProducto.getId()).lista(listaPrecio.getNombre()).build();
//			Optional<Precio> unPrecio = precioDao.findById(build);
//			if (!unPrecio.isPresent()) {
//				JOptionPane.showMessageDialog(this,
//						"El producto no tiene precio en esa lista, por favor carguelo y vuelva a probar", "Error",
//						JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//
//			textFieldPrecio.setText(unPrecio.get().getPrecio().toString());
//		}
//
//	}
//
//	private void precargarStock(Producto unProducto) {
//		if (null == unProducto) {
//			textFieldStockActual.setText("");
//		} else {
//			textFieldStockActual.setText(unProducto.getStock().toString());
//		}
//	}
//
////	private ActionListener registrarMovimiento() {
////		return new ActionListener() {
////			public void actionPerformed(ActionEvent e) {
////
////				String selectedItem = (String) textCodigoProducto.getSelectedItem();
////				if (Strings.isNotEmpty(selectedItem)) {
////
////					if (validar()) {
////						Producto unProducto = obtenerProducto(selectedItem);
////
////						Object descuentoSeleccionado = comboBoxDescuento.getSelectedItem();
////						table.registrarMovimiento(unProducto, Integer.valueOf(textFieldCantidad.getText()),
////								Double.parseDouble(textFieldPrecio.getText()), descuentoSeleccionado.toString());
////
////						textFieldTotal.setText(table.getTotal().toString());
////						
////						limpiarCampos();
////					}
////
////				}
////			}
////
////		};
////	}
//
//	private Producto obtenerProducto(String selectedItem) {
//		return productoDao.findById(Integer.valueOf(selectedItem.split(":")[0])).get();
//	}
//
//	public void limpiarCampos() {
//		textFieldVencimiento.setValue(null);
//	}
//
//	public JButton getBotonGuardar() {
//		return botonGuardar;
//	}
//
//	public JTable getTablaMovimientos() {
//		return table;
//	}
//
//	private void configValidations() {
//		textFieldVencimiento.setInputVerifier(new FechaVerifier("Fecha de Vencimiento", camposInvalidos, true));
//	}
//
//	public boolean validar() {
//		textFieldVencimiento.requestFocus();
//		botonGuardar.requestFocus();
//		if (camposInvalidos.size() > 0) {
//			JOptionPane.showMessageDialog(new JFrame(), "Campos invalidos:" + camposInvalidos, "Error",
//					JOptionPane.ERROR_MESSAGE);
//			return false;
//		}
//		return true;
//
//	}
//
//	public void guardarMovimientos() {
//		PedidoProductoTableModel tableModel = (PedidoProductoTableModel) table.getModel();
//		int nRow = tableModel.getRowCount();
//
//		if (nRow == 0) {
//			JOptionPane.showMessageDialog(this, "Ingrese al menos un producto", "Error", JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//		try {
//
//			Pedido unPedido = Pedido.builder().cliente(cliente).estado(EstadoPedido.EMITIDO).fecha(new Date()).build();
//			pedidoDao.save(unPedido);
//			Collection<PedidoProducto> pedidoProductos = tableModel.getPedidoProductos(unPedido);
//			pedidoProductoDao.saveAll(pedidoProductos);
//
//			JOptionPane.showMessageDialog(this, "El pedido fue gardado correctamente..Imprimiendo");
//
//			imprimir(unPedido,pedidoProductos);
//
//			setVisible(false);
//		} catch (NumberFormatException e) {
//			throw new RuntimeException(e);
//		}
//
//	}
//
//	
//
//	private void imprimir(Collection<PedidoProducto> productos) {
//		Impresora impresora = new Impresora();
//		impresora.imprimirPago(unPedido, productos);
//		
//	}
//
//	public static boolean isNumeric(String strNum) {
//		if (strNum == null) {
//			return false;
//		}
//		try {
//			double d = Integer.parseInt(strNum);
//		} catch (NumberFormatException nfe) {
//			return false;
//		}
//		return true;
//	}
//
//	public void nuevoPago(Cliente unCliente) {
//		this.cliente = unCliente;
//		textFieldCliente.setText(unCliente.getRazonSocial());
//		textFieldLista.setText(unCliente.getListaPrecio().getNombre());
//		textFieldTipoCliente.setText(unCliente.getTipoCuenta().name());
//		listaPrecios = precioDao.findByLista(unCliente.getListaPrecio());
//		//((DefaultTableModel) table.getModel()).setRowCount(0);
//		this.setVisible(true);
//	}
//
//}
