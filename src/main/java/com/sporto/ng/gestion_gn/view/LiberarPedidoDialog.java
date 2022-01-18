package com.sporto.ng.gestion_gn.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

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
import com.sporto.ng.gestion_gn.dao.ProductoDao;
import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.EstadoPedido;
import com.sporto.ng.gestion_gn.model.MedioPago;
import com.sporto.ng.gestion_gn.model.MovimientoCaja;
import com.sporto.ng.gestion_gn.model.Pedido;
import com.sporto.ng.gestion_gn.model.TipoCuenta;
import com.sporto.ng.gestion_gn.model.TipoMovimiento;
import com.sporto.ng.gestion_gn.pruebas.Impresora;
import com.sporto.ng.gestion_gn.view.model.LiberarPedidoTable;
import com.sporto.ng.gestion_gn.view.model.LiberarPedidoTableModel;
import com.sporto.ng.gestion_gn.view.model.PagoTable;
import com.sporto.ng.gestion_gn.view.model.PagoTableModel;
import javax.swing.DefaultComboBoxModel;

public class LiberarPedidoDialog extends JDialog {
	private static final String DENOMINACION_VARIOS = "Varios";
	private LiberarPedidoTable tablePedidos;
	private JButton botonGuardar;
	private Cliente cliente;
	private PedidoDao pedidoDao;
	private JTextField textFieldEstadoCC;
	private JTextField cantidadBilletes;
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
	
	public LiberarPedidoDialog(PedidoDao pedidoDao, MovimientoCajaDao movimientoCajaDao,
			JFrame owner) {
		super(owner);
		setResizable(false);
		this.pedidoDao = pedidoDao;
		this.movimientoCajaDao = movimientoCajaDao;

		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("icono.ico")));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 678, 533);
		setTitle("Liberar pedidos");
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
		
		JLabel lblCantidadMonto = new JLabel("CANTIDAD:");
		
		JLabel lblDenominacion = new JLabel("EN BILLETES DE:");
		JComboBox pagoDenominacion = new JComboBox(new Object[]{});
		
		
		pagoDenominacion.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	String denominacion = (String) pagoDenominacion.getSelectedItem();
		    	if(DENOMINACION_VARIOS.equals(denominacion)) {
		    		lblCantidadMonto.setText("MONTO:");
		    	}else {
		    		lblCantidadMonto.setText("CANTIDAD:");
		    	}
		    			
		    }
		});
		
		JComboBox<MedioPago> pagoMedioPago = new JComboBox(MedioPago.values());
		pagoMedioPago.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	MedioPago mediodepago = (MedioPago) pagoMedioPago.getSelectedItem();
		    	if(!MedioPago.EFECTIVO.equals(mediodepago)) {
		    		lblDenominacion.setVisible(false);
		    		pagoDenominacion.setVisible(false);
		    		lblCantidadMonto.setText("MONTO:");
		    	}else {
		    		lblDenominacion.setVisible(true);
		    		pagoDenominacion.setVisible(true);
		    		lblCantidadMonto.setText("CANTIDAD:");
		    	}
		    			
		    }
		});
		panelPagosInputs.add(pagoMedioPago);
		
		panelPagosInputs.add(lblDenominacion);
		
		pagoDenominacion.setModel(new DefaultComboBoxModel(new String[] {"1000", "500", "200", "100", DENOMINACION_VARIOS}));
		panelPagosInputs.add(pagoDenominacion);

		panelPagosInputs.add(lblCantidadMonto);

		cantidadBilletes = new JTextField();
		cantidadBilletes.setColumns(8);
		panelPagosInputs.add(cantidadBilletes);

		JLabel lblNewLabel_3_1_2_1 = new JLabel("COMENTARIO:");
		panelPagosInputs.add(lblNewLabel_3_1_2_1);

		pagoComentario = new JTextField();
		pagoComentario.setColumns(15);
		panelPagosInputs.add(pagoComentario);

		JButton registrarBtn = new JButton("REGISTRAR ");
		registrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				double parseDouble = 0;
				String denominacionString = pagoDenominacion.getSelectedItem().toString();
				try {
					double cantidadBilletesDouble = Constants.parseDouble(cantidadBilletes.getText());
					
					if(MedioPago.EFECTIVO.equals((MedioPago) pagoMedioPago.getSelectedItem())
							&&
							!DENOMINACION_VARIOS.equals(denominacionString)) {
						double denominacion = Double.parseDouble(denominacionString);
						parseDouble = cantidadBilletesDouble * denominacion ;
					}else {
						denominacionString = "";
						parseDouble = cantidadBilletesDouble;
					}
						
					
				} catch (RuntimeException e1) {
					JOptionPane.showMessageDialog(null, "Ingrese un monto válido", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				MovimientoCaja unMovimientoPago = MovimientoCaja.builder().cliente(cliente)
						.comentario(pagoComentario.getText()).fecha(new Date())
						.medioPago((MedioPago) pagoMedioPago.getSelectedItem())
						.monto(parseDouble)
						.tipoMovimiento(TipoMovimiento.INGRESO)
						.denominacion(denominacionString).build();
				tablePagos.addPago(unMovimientoPago);
				limpiarCampos();
				totalPagos.setText(tablePagos.getTotal().toString());
				actualizarEstadoCC();
			}
		});
		panelPagosInputs.add(registrarBtn);

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
							Double valueAt2 = Constants.parseDouble(target.getValueAt(i, LiberarPedidoTableModel.COLUMN_TOTAL).toString());
							total += valueAt2;
						}
					}
					totalPedidos.setText(Constants.outDouble(total));
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

		botonGuardar = new JButton("VISTA PREVIA");
		botonGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double ingresos = Constants.parseDouble(totalPagos.getText());
				double egresos = Constants.parseDouble(totalPedidos.getText());
				if(ingresos == 0 && egresos == 0) {
					JOptionPane.showMessageDialog(new JDialog(), "Debe registar un ingreso o liberar un pedido.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				guardarMovimientos();
			}
		});
		panelBotones.add(botonGuardar);
		pack();

	}

	protected void limpiarCampos() {
		pagoComentario.setText("");
		cantidadBilletes.setText("");

	}

	public JButton getBotonGuardar() {
		return botonGuardar;
	}

	public JTable getTablaMovimientos() {
		return tablePedidos;
	}

	public void guardarMovimientos() {

		double limiteDeuda = cliente.getLimiteDeuda() * -1;
		Double estadoCC = Constants.parseDouble(textFieldEstadoCC.getText());

		if (estadoCC < limiteDeuda) {
			JOptionPane.showMessageDialog(this, "El cliente supera su liminte de descubierto.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;

		}

//		if (estadoCC > 0) {
//			JOptionPane.showMessageDialog(this, "El cliente no puede tener un saldo a favor.", "Error",
//					JOptionPane.ERROR_MESSAGE);
//			return;
//
//		}

		List<MovimientoCaja> pagos = tablePagos.getPagos(cliente);


		Impresora impresora = new Impresora();
		impresora.imprimirPagos(cliente, pagos,estadoCC);
		impresora.getBtnImprimir().addActionListener(new ActionListener() {
			private JDialog padre;
			@Override
			public void actionPerformed(ActionEvent e) {
				registrarIngresos(pagos);
				
				actualizarPedidos(tablePedidos);
				padre.setVisible(false);
			}
			private ActionListener init(JDialog padre) {
				this.padre = padre;
				return this;
			}
		}.init(this));
		impresora.setVisible(true);

		
		
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
		textFieldDescubierto.setText(Constants.outDouble(unCliente.getLimiteDeuda()));
		textFieldSaldo.setText(Constants.outDouble(unCliente.getSaldo()));
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

		double ingresos = Constants.parseDouble(totalPagos.getText());
		double egresos = Constants.parseDouble(totalPedidos.getText());

		double estadoCC = cliente.getSaldo() + ingresos - egresos;

		textFieldEstadoCC.setText(Constants.outDouble(estadoCC));

	}

}
