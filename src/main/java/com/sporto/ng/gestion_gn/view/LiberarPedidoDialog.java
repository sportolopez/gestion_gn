package com.sporto.ng.gestion_gn.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.dao.MovimientoCajaDao;
import com.sporto.ng.gestion_gn.dao.PedidoDao;
import com.sporto.ng.gestion_gn.dao.PedidoProductoDao;
import com.sporto.ng.gestion_gn.dao.PrecioDao;
import com.sporto.ng.gestion_gn.dao.ProductoDao;
import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.EstadoPedido;
import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.model.MedioPago;
import com.sporto.ng.gestion_gn.model.MovimientoCaja;
import com.sporto.ng.gestion_gn.model.Pedido;
import com.sporto.ng.gestion_gn.model.PedidoProducto;
import com.sporto.ng.gestion_gn.model.Precio;
import com.sporto.ng.gestion_gn.model.PrecioId;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.model.TipoMovimiento;
import com.sporto.ng.gestion_gn.pruebas.Impresora;
import com.sporto.ng.gestion_gn.view.model.LiberarPedidoTable;
import com.sporto.ng.gestion_gn.view.model.LiberarPedidoTableModel;
import com.sporto.ng.gestion_gn.view.model.PagoTable;
import com.sporto.ng.gestion_gn.view.model.PagoTableModel;
import com.sporto.ng.gestion_gn.view.model.PedidoProductoTableModel;

public class LiberarPedidoDialog extends JDialog {
	private LiberarPedidoTable tablePedidos;
	private JButton botonGuardar;
	private ProductoDao productoDao;
	private List<Precio> listaPrecios;
	private Cliente cliente;
	private PedidoDao pedidoDao;
	private JTextField textFieldEstadoCC;
	private JTextField montoPago;
	private JTextField pagoComentario;
	private JTextField totalPagos;
	private JTextField textFieldCliente;
	private JTextField textFieldLista;
	private JTextField textFieldTipoCliente;
	private JTextField textFieldDescubierto;
	private JTextField totalPedidos;
	private PagoTable tablePagos;
	private JTextField textFieldSaldo;
	private MovimientoCajaDao movimientoCajaDao;

	public LiberarPedidoDialog(ProductoDao productoDao, PedidoDao pedidoDao, MovimientoCajaDao movimientoCajaDao,
			JFrame owner) {
		super(owner);
		setResizable(false);
		this.pedidoDao = pedidoDao;
		this.productoDao = productoDao;
		this.movimientoCajaDao = movimientoCajaDao;

		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("icono.ico")));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 678, 533);
		setTitle("Ingresar Stock");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JLabel lblNewLabel = new JLabel("LIBERAR PEDIDOS");
		lblNewLabel.setFont(Constants.FUENTE_TITULO);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(lblNewLabel);

		JPanel panelCliente = new JPanel();
		panelCliente.setBorder(
				new TitledBorder(null, "CLIENTE", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		getContentPane().add(panelCliente);

		JLabel lblNewLabel_1_1 = new JLabel("Nombre:");
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

		JLabel lblNewLabel_3_2_1 = new JLabel("LIMITE DESCUBIERTO:");
		panelCliente.add(lblNewLabel_3_2_1);

		textFieldDescubierto = new JTextField();
		textFieldDescubierto.setText("$10000");
		textFieldDescubierto.setEditable(false);
		textFieldDescubierto.setColumns(8);
		panelCliente.add(textFieldDescubierto);

		JLabel lblNewLabel_1 = new JLabel("SALDO");
		panelCliente.add(lblNewLabel_1);

		textFieldSaldo = new JTextField();
		textFieldSaldo.setEditable(false);
		textFieldSaldo.setColumns(8);
		panelCliente.add(textFieldSaldo);

		JPanel panelPago = new JPanel();
		panelPago.setBorder(new TitledBorder(null, "PAGOS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panelPago);
		panelPago.setLayout(new BoxLayout(panelPago, BoxLayout.Y_AXIS));

		JPanel panelBotones = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBotones.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);

		JPanel panelPagosInputs = new JPanel();
		panelPago.add(panelPagosInputs);

		JLabel lblNewLabel_1_2 = new JLabel("MEDIO DE PAGO:");
		panelPagosInputs.add(lblNewLabel_1_2);

		JComboBox<MedioPago> pagoMedioPago = new JComboBox(MedioPago.values());
		panelPagosInputs.add(pagoMedioPago);

		JLabel lblNewLabel_3_1_3 = new JLabel("MONTO:");
		panelPagosInputs.add(lblNewLabel_3_1_3);

		montoPago = new JTextField();
		montoPago.setColumns(8);
		panelPagosInputs.add(montoPago);

		JLabel lblNewLabel_3_1_2_1 = new JLabel("COMENTARIO:");
		panelPagosInputs.add(lblNewLabel_3_1_2_1);

		pagoComentario = new JTextField();
		pagoComentario.setColumns(15);
		panelPagosInputs.add(pagoComentario);

		JButton agregarStockBtn_1 = new JButton("REGISTRAR ");
		agregarStockBtn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				double parseDouble = 0;
				try {
					parseDouble = Double.parseDouble(montoPago.getText());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Ingrese un monto válido", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				MovimientoCaja unMovimientoPago = MovimientoCaja.builder().cliente(cliente)
						.comentario(pagoComentario.getText()).fecha(new Date())
						.medioPago((MedioPago) pagoMedioPago.getSelectedItem()).monto(parseDouble)
						.tipoMovimiento(TipoMovimiento.INGRESO).build();
				tablePagos.addPago(unMovimientoPago);
				limpiarCampos();
				totalPagos.setText(tablePagos.getTotal().toString());
				actualizarEstadoCC();
			}
		});
		panelPagosInputs.add(agregarStockBtn_1);

		JScrollPane scrollPanePagos = new JScrollPane();
		tablePagos = new PagoTable();

		tablePagos.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				JTable target = (JTable) evt.getSource();
				int row = target.rowAtPoint(evt.getPoint());
				int col = target.columnAtPoint(evt.getPoint());
				if (col == PagoTableModel.COLUMN_ELIMINAR) {
					((DefaultTableModel) target.getModel()).removeRow(row);
				}
				totalPagos.setText(tablePagos.getTotal().toString());
				actualizarEstadoCC();
			}
		});
		scrollPanePagos.setViewportView(tablePagos);
		panelPago.add(scrollPanePagos);

		JPanel panelPagoTotal = new JPanel();
		FlowLayout fl_panelPagoTotal = (FlowLayout) panelPagoTotal.getLayout();
		fl_panelPagoTotal.setAlignment(FlowLayout.RIGHT);
		panelPago.add(panelPagoTotal);

		JLabel lblNewLabel_2_1_1 = new JLabel("TOTAL PAGOS:");
		panelPagoTotal.add(lblNewLabel_2_1_1);

		totalPagos = new JTextField();
		totalPagos.setText("0");
		totalPagos.setEnabled(false);
		totalPagos.setColumns(10);
		panelPagoTotal.add(totalPagos);

		JPanel panelPedidos = new JPanel();
		panelPedidos.setBorder(
				new TitledBorder(null, "PEDIDOS A LIBERAR", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panelPedidos);
		panelPedidos.setLayout(new BoxLayout(panelPedidos, BoxLayout.Y_AXIS));

		JScrollPane scrollPanePedidos = new JScrollPane();
		panelPedidos.add(scrollPanePedidos);

		JPanel panelTotal_2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelTotal_2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		panelPedidos.add(panelTotal_2);

		JLabel lblNewLabel_2_1 = new JLabel("TOTAL PEDIDOS:");
		panelTotal_2.add(lblNewLabel_2_1);

		totalPedidos = new JTextField();
		totalPedidos.setText("0");
		totalPedidos.setEditable(true);
		totalPedidos.setHorizontalAlignment(JTextField.RIGHT);
		totalPedidos.setColumns(10);
		panelTotal_2.add(totalPedidos);

		tablePedidos = new LiberarPedidoTable();
		tablePedidos.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				super.mouseReleased(evt);
				JTable target = (JTable) evt.getSource();
				int row = target.rowAtPoint(evt.getPoint());
				int col = target.columnAtPoint(evt.getPoint());
				if (col == LiberarPedidoTableModel.COLUMN_LIBERAR) {
					Double total = (double) 0;
					for (int i = 0; i < target.getRowCount(); ++i) {

						Boolean valueAt = (Boolean) target.getValueAt(i, col);
						if (valueAt) {
							Double valueAt2 = (Double) target.getValueAt(i, LiberarPedidoTableModel.COLUMN_TOTAL);
							total += valueAt2;
						}
					}
					totalPedidos.setText(total.toString());
					actualizarEstadoCC();
				}
			}
		});
		scrollPanePedidos.setViewportView(tablePedidos);

		JPanel panelTotal_3 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panelTotal_3.getLayout();
		flowLayout_4.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panelTotal_3);

		JLabel lblNewLabel_2_2 = new JLabel("NUEVO SALDO:");
		panelTotal_3.add(lblNewLabel_2_2);

		textFieldEstadoCC = new JTextField();
		textFieldEstadoCC.setText("0");
		textFieldEstadoCC.setEditable(true);
		textFieldEstadoCC.setHorizontalAlignment(JTextField.RIGHT);
		textFieldEstadoCC.setColumns(10);
		panelTotal_3.add(textFieldEstadoCC);
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
		pack();

	}

	protected void limpiarCampos() {
		pagoComentario.setText("");
		montoPago.setText("");

	}

	public JButton getBotonGuardar() {
		return botonGuardar;
	}

	public JTable getTablaMovimientos() {
		return tablePedidos;
	}

	public void guardarMovimientos() {

		double limiteDeuda = cliente.getLimiteDeuda() * -1;
		Double estadoCC = Double.valueOf(textFieldEstadoCC.getText());

		if (estadoCC < limiteDeuda) {
			JOptionPane.showMessageDialog(this, "El cliente supera su liminte de descubierto.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;

		}

		if (estadoCC > 0) {
			JOptionPane.showMessageDialog(this, "El cliente no puede tener un saldo a favor.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;

		}

		List<MovimientoCaja> pagos = tablePagos.getPagos(cliente);
		registrarIngresos(pagos);
		actualizarPedidos(tablePedidos);

		Impresora impresora = new Impresora();
		impresora.imprimirPagos(cliente, pagos);
		impresora.setVisible(true);

		JOptionPane.showMessageDialog(this, "Se registraron los movimientos.", " ", JOptionPane.INFORMATION_MESSAGE);
		this.setVisible(false);
	}

	private void actualizarPedidos(LiberarPedidoTable table) {

		List<Integer> pedidosSeleccionados = table.getPedidosSeleccionados();

		for (Integer idSelecciondo : pedidosSeleccionados) {
			Pedido unPedido = pedidoDao.findById(idSelecciondo).get();
			unPedido.setEstado(EstadoPedido.LIBERADO);
			pedidoDao.save(unPedido);
		}

	}

	private void registrarIngresos(List<MovimientoCaja> pagos) {

		for (MovimientoCaja movimientoCaja : pagos) {
			movimientoCajaDao.save(movimientoCaja);
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

	public void liberarPedidos(Cliente unCliente) {
		this.cliente = unCliente;
		textFieldCliente.setText(unCliente.getRazonSocial());
		textFieldLista.setText(unCliente.getListaPrecio().getNombre());
		textFieldTipoCliente.setText(unCliente.getTipoCuenta().name());
		textFieldDescubierto.setText(String.valueOf(unCliente.getLimiteDeuda()));
		textFieldSaldo.setText(unCliente.getSaldo().toString());
		((DefaultTableModel) tablePagos.getModel()).setRowCount(0);
		((DefaultTableModel) tablePedidos.getModel()).setRowCount(0);

		tablePedidos.addPedidos(pedidoDao.findByClienteAndEstado(unCliente, EstadoPedido.EMITIDO));
		totalPagos.setText("0");
		totalPedidos.setText("0");
		textFieldEstadoCC.setText("0");
		actualizarEstadoCC();
		this.setVisible(true);
	}

	private void actualizarEstadoCC() {

		double ingresos = Double.parseDouble(totalPagos.getText());
		double egresos = Double.parseDouble(totalPedidos.getText());

		double estadoCC = cliente.getSaldo() + ingresos - egresos;

		textFieldEstadoCC.setText(Double.valueOf(estadoCC).toString());

	}

}
