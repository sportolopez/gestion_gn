package com.sporto.ng.gestion_gn.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.MovimientoStock;
import com.sporto.ng.gestion_gn.model.Producto;

public class DetalleProducto extends JDialog {
	private JTable table;
	private JTextField textFieldCategoria;
	private JTextField textFieldCodigo;
	private JTextField textFieldDescripcion;
	private JTextField textFieldStock;
	private JTextField textFieldVencimiento;
	private JTextField textFieldCosto;

	public DetalleProducto(HomeForm homeForm, Producto unProducto,List<MovimientoStock> ingresos) {
		super(homeForm);
		setSize(new Dimension(700, 350));
		setMinimumSize(new Dimension(600, 300));
		initView();
		cargarCampos(unProducto);
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (MovimientoStock movimientoStock : ingresos) {
			model.addRow(new Object[] { movimientoStock.getRemito(),movimientoStock.getCantidad(), movimientoStock.getFecha(),movimientoStock.getFechaVencimiento() });
		}
		
	}

	private void cargarCampos(Producto unProducto) {
		textFieldCategoria.setText(unProducto.getCategoria());
		textFieldCodigo.setText(unProducto.getCosto().toString());
		textFieldDescripcion.setText(unProducto.getDescripcion());
		textFieldStock.setText(unProducto.getStock().toString());
		textFieldVencimiento.setText(Constants.FORMATO_FECHA.format(unProducto.getFechaVencimiento()));
		textFieldCosto.setText(unProducto.getCosto().toString());
	}

	private void initView() {
		setBounds(100, 100, 500, 350);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel panelTitulo = new JPanel();
		panelTitulo.setMaximumSize(new Dimension(32767, 10000));
		getContentPane().add(panelTitulo);
		panelTitulo.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Detalle Producto");
		lblNewLabel.setMaximumSize(new Dimension(79, 40));
		lblNewLabel.setFont(Constants.FUENTE_TITULO);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitulo.add(lblNewLabel);

		JPanel panelDetalle = new JPanel();
		getContentPane().add(panelDetalle);
		panelDetalle.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblCodigoLabel = new JLabel("CÓDIGO");
		lblCodigoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panelDetalle.add(lblCodigoLabel);

		textFieldCodigo = new JTextField();
		textFieldCodigo.setEditable(false);
		panelDetalle.add(textFieldCodigo);
		textFieldCodigo.setColumns(10);

		JLabel lblCategoria = new JLabel("CATEGORÍA");
		lblCategoria.setHorizontalAlignment(SwingConstants.RIGHT);
		panelDetalle.add(lblCategoria);

		textFieldCategoria = new JTextField();
		textFieldCategoria.setEditable(false);
		panelDetalle.add(textFieldCategoria);
		textFieldCategoria.setColumns(10);

		JLabel lblStock = new JLabel("STOCK");
		lblStock.setHorizontalAlignment(SwingConstants.RIGHT);
		panelDetalle.add(lblStock);

		textFieldStock = new JTextField();
		textFieldStock.setEditable(false);
		textFieldStock.setColumns(10);
		panelDetalle.add(textFieldStock);

		JLabel lblDescripcion = new JLabel("DESCRIPCIÓN");
		lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		panelDetalle.add(lblDescripcion);

		textFieldDescripcion = new JTextField();
		textFieldDescripcion.setEditable(false);
		panelDetalle.add(textFieldDescripcion);
		textFieldDescripcion.setColumns(10);

		JLabel lblFVencimiento = new JLabel("F. VENCIMIENTO");
		lblFVencimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		panelDetalle.add(lblFVencimiento);

		textFieldVencimiento = new JTextField();
		textFieldVencimiento.setEditable(false);
		textFieldVencimiento.setColumns(10);
		panelDetalle.add(textFieldVencimiento);

		JLabel lblCosto = new JLabel("COSTO");
		lblCosto.setHorizontalAlignment(SwingConstants.RIGHT);
		panelDetalle.add(lblCosto);

		textFieldCosto = new JTextField();
		textFieldCosto.setEditable(false);
		textFieldCosto.setColumns(10);
		panelDetalle.add(textFieldCosto);

		JPanel panelTitulo_1 = new JPanel();
		panelTitulo_1.setMaximumSize(new Dimension(32767, 10000));
		getContentPane().add(panelTitulo_1);
		panelTitulo_1.setLayout(new BorderLayout(0, 0));

		JPanel panelMovimientos = new JPanel();
		getContentPane().add(panelMovimientos);

		JTabbedPane tp = new JTabbedPane();

		panelMovimientos.add(tp);
		JScrollPane scrollPane = new JScrollPane();
		tp.add("Ingresos", scrollPane);
		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null }, },
				new String[] { "Nro Remito", "Cantidad", "Fecha Vencimiento", "Fecha Movimiento" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, Integer.class, Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setViewportView(table);
	}

}
