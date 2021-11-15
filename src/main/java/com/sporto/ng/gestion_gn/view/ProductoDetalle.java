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
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.MovimientoStock;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.model.TipoMovimiento;

public class ProductoDetalle extends JDialog {
	private JTable tableIngresos;
	private JTextField textFieldCodigo;
	private JTextField textFieldCategoria;
	private JTextField textFieldStock;
	private JTextField textFieldDescripcion;
	private JTextField textFieldVencimiento;
	private JTextField textFieldCosto;
	private JTable tableEgresos;

	public ProductoDetalle(HomeForm homeForm, Producto unProducto, List<MovimientoStock> ingresos) {
		super(homeForm);

		initView();
		cargarCampos(unProducto);

		DefaultTableModel model = (DefaultTableModel) tableIngresos.getModel();
		DefaultTableModel modelEgresos = (DefaultTableModel) tableEgresos.getModel();
		for (MovimientoStock movimientoStock : ingresos) {

			if (movimientoStock.getTipoMovimiento().equals(TipoMovimiento.INGRESO)) {

				model.addRow(new Object[] { movimientoStock.getOrdenCompra(), movimientoStock.getRemito(),
						movimientoStock.getCantidad(), Constants.outFecha(movimientoStock.getFechaVencimiento()),
						Constants.outFecha(movimientoStock.getFecha()), movimientoStock.getComentario() });
			} else {

				modelEgresos.addRow(new Object[] { movimientoStock.getCantidad(),
						Constants.outFecha(movimientoStock.getFecha()), movimientoStock.getComentario() });
			}
		}

	}

	private void cargarCampos(Producto unProducto) {
		textFieldCategoria.setText(unProducto.getCategoria());
		textFieldCodigo.setText(String.valueOf(unProducto.getId()));
		textFieldDescripcion.setText(unProducto.getDescripcion());
		textFieldStock.setText(unProducto.getStock().toString());
		textFieldVencimiento.setText(Constants.outFecha(unProducto.getFechaVencimiento()));
		textFieldCosto.setText(unProducto.getCosto().toString());
	}

	private void initView() {
		setSize(new Dimension(800, 500));
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel panelTitulo = new JPanel();
		getContentPane().add(panelTitulo);
		panelTitulo.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Detalle Producto");
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

		JLabel lblCodigoLabel = new JLabel("CÓDIGO");
		lblCodigoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel1.add(lblCodigoLabel);

		textFieldCodigo = new JTextField();
		textFieldCodigo.setText((String) null);
		textFieldCodigo.setEditable(false);
		textFieldCodigo.setColumns(10);
		panel1.add(textFieldCodigo);

		JLabel lblCategoria = new JLabel("CATEGORÍA");
		lblCategoria.setHorizontalAlignment(SwingConstants.RIGHT);
		panel1.add(lblCategoria);

		textFieldCategoria = new JTextField();
		textFieldCategoria.setText((String) null);
		textFieldCategoria.setEditable(false);
		textFieldCategoria.setColumns(10);
		panel1.add(textFieldCategoria);

		JLabel lblStock = new JLabel("STOCK");
		lblStock.setHorizontalAlignment(SwingConstants.RIGHT);
		panel1.add(lblStock);

		textFieldStock = new JTextField();
		textFieldStock.setText((String) null);
		textFieldStock.setEditable(false);
		textFieldStock.setColumns(10);
		panel1.add(textFieldStock);

		JPanel panel2 = new JPanel();
		panelDetalle.add(panel2);
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel lblDescripcion = new JLabel("DESCRIPCIÓN");
		lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		panel2.add(lblDescripcion);

		textFieldDescripcion = new JTextField();
		textFieldDescripcion.setText((String) null);
		textFieldDescripcion.setEditable(false);
		textFieldDescripcion.setColumns(15);
		panel2.add(textFieldDescripcion);

		JLabel lblFVencimiento = new JLabel("F. VENCIMIENTO");
		lblFVencimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		panel2.add(lblFVencimiento);

		textFieldVencimiento = new JTextField();
		textFieldVencimiento.setText((String) null);
		textFieldVencimiento.setEditable(false);
		textFieldVencimiento.setColumns(10);
		panel2.add(textFieldVencimiento);

		JLabel lblCosto = new JLabel("COSTO");
		lblCosto.setHorizontalAlignment(SwingConstants.RIGHT);
		panel2.add(lblCosto);

		textFieldCosto = new JTextField();
		textFieldCosto.setText((String) null);
		textFieldCosto.setEditable(false);
		textFieldCosto.setColumns(10);
		panel2.add(textFieldCosto);

		JPanel panelMovimientos = new JPanel();
		getContentPane().add(panelMovimientos);
		panelMovimientos.setLayout(new BorderLayout(0, 0));

		JTabbedPane tp = new JTabbedPane();
		tp.setSize(new Dimension(0, 50));

		panelMovimientos.add(tp);
		JScrollPane scrollPaneIngresos = new JScrollPane();
		scrollPaneIngresos.setSize(new Dimension(0, 50));
		tp.add("Ingresos", scrollPaneIngresos);
		JScrollPane scrollPaneEgresos = new JScrollPane();
		scrollPaneEgresos.setSize(new Dimension(0, 50));
		tp.add("Egresos", scrollPaneEgresos);

		tableIngresos = new JTable();
		tableIngresos.setSize(new Dimension(0, 50));
		tableIngresos.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "OC", "Nro Remito", "Cantidad",
				"Fecha Vencimiento", "Fecha Movimiento", "Comentario" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, Integer.class, Integer.class };

			public Class getColumnClass(int columnIndex) {
				return String.class;
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		tableEgresos = new JTable();
		tableEgresos.setSize(new Dimension(0, 50));
		tableEgresos.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Cantidad", "Fecha Movimiento", "Comentario" }) {
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
		scrollPaneEgresos.setViewportView(tableEgresos);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableIngresos.setDefaultRenderer(Object.class, rightRenderer);
		tableEgresos.setDefaultRenderer(Object.class, rightRenderer);
	}

}
