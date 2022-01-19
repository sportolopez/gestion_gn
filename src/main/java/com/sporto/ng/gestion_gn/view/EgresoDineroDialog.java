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
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.dao.GastoCajaDao;
import com.sporto.ng.gestion_gn.model.GastoCaja;
import com.sporto.ng.gestion_gn.model.MedioPago;
import com.sporto.ng.gestion_gn.model.MovimientoCaja;
import com.sporto.ng.gestion_gn.model.TipoMovimiento;
import com.sporto.ng.gestion_gn.view.model.PagoTable;
import com.sporto.ng.gestion_gn.view.model.PagoTableModel;
import com.sporto.ng.gestion_gn.view.model.PedidoProductoTableModel;

public class EgresoDineroDialog extends JDialog {
	private JButton botonGuardar;
	
	private JTextField montoPago;
	private JComboBox<String> pagoComentario;
	private JTextField totalPagos;
	private PagoTable tablePagos;
	private GastoCajaDao gastoCajaDao;

	public EgresoDineroDialog(GastoCajaDao gastoCaja,
			JFrame owner) {
		super(owner);
		setResizable(false);
		this.gastoCajaDao = gastoCaja;

		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("icono.ico")));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 678, 533);
		setTitle("Ingresar Stock");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JLabel lblNewLabel = new JLabel("REGISTROS DE GASTOS");
		lblNewLabel.setFont(Constants.FUENTE_TITULO);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(lblNewLabel);

		JPanel panelPago = new JPanel();
		panelPago.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "GASTOS", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		getContentPane().add(panelPago);
		panelPago.setLayout(new BoxLayout(panelPago, BoxLayout.Y_AXIS));

		JPanel panelBotones = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBotones.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);

		JPanel panelPagosInputs = new JPanel();
		panelPago.add(panelPagosInputs);

		JLabel lblNewLabel_1_2 = new JLabel("FORMA:");
		panelPagosInputs.add(lblNewLabel_1_2);

		JComboBox<MedioPago> pagoMedioPago = new JComboBox(MedioPago.values());
		pagoMedioPago.setEnabled(false);
		panelPagosInputs.add(pagoMedioPago);

		JLabel lblNewLabel_3_1_3 = new JLabel("MONTO:");
		panelPagosInputs.add(lblNewLabel_3_1_3);

		montoPago = new JTextField();
		montoPago.setColumns(8);
		panelPagosInputs.add(montoPago);

		JLabel lblNewLabel_3_1_2_1 = new JLabel("COMENTARIO:");
		panelPagosInputs.add(lblNewLabel_3_1_2_1);

		List<String> findMotivosEgreso = gastoCajaDao.findMotivosEgreso();
		pagoComentario = new JComboBox<String>(findMotivosEgreso.toArray(new String[findMotivosEgreso.size()]));
		panelPagosInputs.add(pagoComentario);

		JButton agregarStockBtn_1 = new JButton("REGISTRAR ");
		agregarStockBtn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				double parseDouble = 0;
				try {
					parseDouble = Constants.parseDouble(montoPago.getText());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Ingrese un monto válido", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				MovimientoCaja unMovimientoPago = MovimientoCaja.builder()
						.comentario(pagoComentario.getSelectedItem().toString()).fecha(new Date())
						.medioPago((MedioPago) pagoMedioPago.getSelectedItem()).monto(parseDouble)
						.tipoMovimiento(TipoMovimiento.INGRESO).build();
				tablePagos.addPago(unMovimientoPago);
				limpiarCampos();
				totalPagos.setText(tablePagos.getTotal().toString());
			}
		});
		panelPagosInputs.add(agregarStockBtn_1);

		JScrollPane scrollPanePagos = new JScrollPane();
		tablePagos = new PagoTable();
		tablePagos.getColumnModel().getColumn(PagoTableModel.COLUMN_DENOMINACION).setMinWidth(0);
		tablePagos.getColumnModel().getColumn(PagoTableModel.COLUMN_DENOMINACION).setMaxWidth(0);
		
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
			}
		});
		scrollPanePagos.setViewportView(tablePagos);
		panelPago.add(scrollPanePagos);

		JPanel panelPagoTotal = new JPanel();
		FlowLayout fl_panelPagoTotal = (FlowLayout) panelPagoTotal.getLayout();
		fl_panelPagoTotal.setAlignment(FlowLayout.RIGHT);
		panelPago.add(panelPagoTotal);

		JLabel lblNewLabel_2_1_1 = new JLabel("TOTAL GASTOS:");
		panelPagoTotal.add(lblNewLabel_2_1_1);

		totalPagos = new JTextField();
		totalPagos.setText("0");
		totalPagos.setEnabled(false);
		totalPagos.setColumns(10);
		panelPagoTotal.add(totalPagos);
		getContentPane().add(panelBotones);

		JButton btnNewButton = new JButton("CANCELAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panelBotones.add(btnNewButton);

		botonGuardar = new JButton("REGISTRAR");
		botonGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarMovimientos();
			}
		});
		panelBotones.add(botonGuardar);
		pack();

	}

	protected void limpiarCampos() {
		pagoComentario.setSelectedIndex(0);
		montoPago.setText("");

	}

	public JButton getBotonGuardar() {
		return botonGuardar;
	}

	public void guardarMovimientos() {

		List<GastoCaja> pagos = tablePagos.getGastos();
		for (GastoCaja movimientoCaja : pagos) {
			gastoCajaDao.save(movimientoCaja);
		}
		JOptionPane.showMessageDialog(this, "Los gastos se registraron con éxito", " ", JOptionPane.INFORMATION_MESSAGE);
		setVisible(false);
		
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
