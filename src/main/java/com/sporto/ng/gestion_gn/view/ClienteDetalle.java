package com.sporto.ng.gestion_gn.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.dao.MovimientoCajaDao;
import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.MovimientoCaja;
import com.sporto.ng.gestion_gn.model.Pedido;
import com.sporto.ng.gestion_gn.model.TipoMovimiento;
import com.sporto.ng.gestion_gn.pruebas.Impresora;
import java.awt.Font;

public class ClienteDetalle extends JDialog implements ActionListener {
	private static final int COLUMNA_ID = 4;
	private JTable tableIngresos;
	private JTextField textFieldRazon;
	private JTextField textFieldCuit;
	private JTextField textFieldEmail;
	private JTextField textFieldDomicilio;
	private JTextField textTipoCuenta;
	private JTextField textFieldDescubierto;
	private JTable tablePedidos;
	private Cliente unCliente;
	private MovimientoCajaDao movimientoCajaDao;
	private JTextField textFieldTotal;
	private List<MovimientoCaja> pagos;
	private List<Pedido> pedidosRealizados;

	public ClienteDetalle(HomeForm homeForm, Cliente unCliente, List<MovimientoCaja> pagos,
			List<Pedido> pedidosRealizados, MovimientoCajaDao movimientoCajaDao) {
		super(homeForm);
		this.pagos = pagos;
		this.pedidosRealizados = pedidosRealizados;
		this.movimientoCajaDao = movimientoCajaDao;

		initView();
		this.unCliente = unCliente;
		cargarCampos(unCliente);

		DefaultTableModel model = (DefaultTableModel) tableIngresos.getModel();
		DefaultTableModel modelPedidos = (DefaultTableModel) tablePedidos.getModel();
		for (MovimientoCaja movimientoStock : pagos) {

			if (movimientoStock.getTipoMovimiento().equals(TipoMovimiento.INGRESO)) {

				model.addRow(new Object[] { movimientoStock.getMedioPago(),
						Constants.outDouble(movimientoStock.getMonto()), Constants.outFecha(movimientoStock.getFecha()),
						Constants.ICONO_DETALLE, movimientoStock.getFecha() });
			}
		}
		double contador = 0 ;
		for (Pedido unPedido : pedidosRealizados) {
			contador += unPedido.getMonto();
			modelPedidos.addRow(new Object[] { unPedido.getId(), Constants.outFecha(unPedido.getFecha()),
					Constants.outDouble(unPedido.getMonto()) });

		}
		
		textFieldTotal.setText(Constants.outDouble(contador));
		
		
	}

	private void cargarCampos(Cliente unCliente) {
		textFieldRazon.setText(unCliente.getRazonSocial());
		textFieldCuit.setText(unCliente.getCuit());
		textFieldEmail.setText(unCliente.getEmail());
		textFieldDomicilio.setText(unCliente.getDomicilio());
		textTipoCuenta.setText(unCliente.getTipoCuenta().toString());
		textFieldDescubierto.setText(Constants.outDouble(unCliente.getLimiteDeuda()));
	}

	private void initView() {
		setSize(new Dimension(800, 500));
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel panelTitulo = new JPanel();
		getContentPane().add(panelTitulo);
		panelTitulo.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Detalle Cliente");
		lblNewLabel.setMaximumSize(new Dimension(800, 50));
		lblNewLabel.setFont(Constants.FUENTE_TITULO);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitulo.add(lblNewLabel);

		JPanel panelDetalle = new JPanel();
		panelDetalle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelDetalle.setMinimumSize(new Dimension(10, 200));
		getContentPane().add(panelDetalle);
		panelDetalle.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JPanel panel1 = new JPanel();
		panelDetalle.add(panel1);
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		panel1.add(lblNombre);

		textFieldRazon = new JTextField();
		textFieldRazon.setText((String) null);
		textFieldRazon.setEditable(false);
		textFieldRazon.setColumns(30);
		panel1.add(textFieldRazon);

		JLabel lblCuit = new JLabel("CUIT");
		lblCuit.setHorizontalAlignment(SwingConstants.RIGHT);
		panel1.add(lblCuit);

		textFieldCuit = new JTextField();
		textFieldCuit.setText((String) null);
		textFieldCuit.setEditable(false);
		textFieldCuit.setColumns(10);
		panel1.add(textFieldCuit);

		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		panel1.add(lblEmail);

		textFieldEmail = new JTextField();
		textFieldEmail.setText((String) null);
		textFieldEmail.setEditable(false);
		textFieldEmail.setColumns(20);
		panel1.add(textFieldEmail);

		JPanel panel2 = new JPanel();
		panelDetalle.add(panel2);
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel lblDomicilio = new JLabel("DIRECCIÓN");
		lblDomicilio.setHorizontalAlignment(SwingConstants.RIGHT);
		panel2.add(lblDomicilio);

		textFieldDomicilio = new JTextField();
		textFieldDomicilio.setText((String) null);
		textFieldDomicilio.setEditable(false);
		textFieldDomicilio.setColumns(30);
		panel2.add(textFieldDomicilio);

		JLabel lbTipoCuenta = new JLabel("TIPO CUENTA");
		lbTipoCuenta.setHorizontalAlignment(SwingConstants.RIGHT);
		panel2.add(lbTipoCuenta);

		textTipoCuenta = new JTextField();
		textTipoCuenta.setText((String) null);
		textTipoCuenta.setEditable(false);
		textTipoCuenta.setColumns(10);
		panel2.add(textTipoCuenta);

		JLabel lblCosto = new JLabel("COSTO");
		lblCosto.setHorizontalAlignment(SwingConstants.RIGHT);

		textFieldDescubierto = new JTextField();
		textFieldDescubierto.setText((String) null);
		textFieldDescubierto.setEditable(false);
		textFieldDescubierto.setColumns(10);

		JPanel panelMovimientos = new JPanel();
		getContentPane().add(panelMovimientos);
		panelMovimientos.setLayout(new BorderLayout(0, 0));

		JTabbedPane tp = new JTabbedPane();
		tp.setSize(new Dimension(0, 50));

		panelMovimientos.add(tp);
		JScrollPane scrollPaneIngresos = new JScrollPane();
		scrollPaneIngresos.setSize(new Dimension(0, 50));
		
		tp.add("Pagos", scrollPaneIngresos);
		JScrollPane scrollPaneEgresos = new JScrollPane();
		JPanel panelPedidos = new JPanel();
		panelPedidos.setSize(new Dimension(0, 50));
		panelPedidos.setLayout(new BoxLayout(panelPedidos, BoxLayout.Y_AXIS));
		panelPedidos.add(scrollPaneEgresos);
		tp.add("Pedidos", panelPedidos);

		JPanel panelTotal = new JPanel();
		panelPedidos.add(panelTotal);
		panelTotal.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JLabel lblTotal = new JLabel("TOTAL EN PEDIDOS: ");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelTotal.add(lblTotal);
		
		textFieldTotal = new JTextField();
		textFieldTotal.setEditable(false);
		panelTotal.add(textFieldTotal);
		textFieldTotal.setColumns(10);
		
		tableIngresos = new JTable();
		tableIngresos.setRowHeight(35);
		tableIngresos.setFont(Constants.FUENTE);
		tableIngresos.setSize(new Dimension(0, 50));
		tableIngresos.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Medio Pago", "Monto", "Fecha", "Imprimir", "ID" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, ImageIcon.class,
					String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		});

		tableIngresos.getColumnModel().getColumn(COLUMNA_ID).setMinWidth(0);
		tableIngresos.getColumnModel().getColumn(COLUMNA_ID).setMaxWidth(0);
		tableIngresos.getColumnModel().getColumn(COLUMNA_ID).setWidth(0);

		tableIngresos.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				JTable target = (JTable) evt.getSource();
				int row = target.rowAtPoint(evt.getPoint());
				int columna = target.getSelectedColumn();
				if (columna == 3) {
					Object col = target.getValueAt(row, COLUMNA_ID);
					List<MovimientoCaja> findGroupByLiberado = movimientoCajaDao.findLiberado(unCliente.getId(),
							col.toString());
					Impresora impresora = new Impresora();
					impresora.reimprimirPago(unCliente, findGroupByLiberado, unCliente.getSaldo());
					impresora.setVisible(true);
				}
			}

		});

		tablePedidos = new JTable();
		tablePedidos.setRowHeight(35);
		tablePedidos.setFont(Constants.FUENTE);
		tablePedidos.setSize(new Dimension(0, 50));
		tablePedidos.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nro", "Fecha", "Monto Total" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, Integer.class, Integer.class };

			public Class getColumnClass(int columnIndex) {
				return String.class;
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		scrollPaneIngresos.setViewportView(tableIngresos);
		scrollPaneEgresos.setViewportView(tablePedidos);


		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnNewButton = new JButton("IMPRIMIR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Impresora impresora = new Impresora();
				impresora.imprimirCliente(unCliente, pagos, unCliente.getSaldo(), pedidosRealizados);
				impresora.setVisible(true);
			}
		});
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("CERRAR");
		panel_1.add(btnNewButton_1);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableIngresos.setDefaultRenderer(Object.class, rightRenderer);
		tablePedidos.setDefaultRenderer(Object.class, rightRenderer);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();

	}

}
