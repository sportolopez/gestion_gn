package com.sporto.ng.gestion_gn.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.view.model.ProductoTableModel;

import lombok.Getter;
import lombok.Setter;
import java.awt.Font;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.border.SoftBevelBorder;

@Getter
@Setter
public class ProductoPanel extends JPanel {

	private ProductoTableModel productoTableModel;
	private JButton btnNuevoProducto;
	private JButton btnImportar;
	private JTable tableProductos;
	JTextField textFieldBuscadorProductos;
	TableRowSorter<ProductoTableModel> sorter;
	private JPanel panelBotones;
	JButton btnIngresoStock;
	private JLabel lblListadoActual;
	JButton btnEgresoStock;
	private final JLabel lblNewLabel = new JLabel("BUSCAR");
	private JPanel panelBuscador;
	private JPanel panelTitulos;
	private JLabel lblTituloProductos_1;
	private JLabel lblTituloProductos_2;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panelHEaderTabla;
	private JButton btnNewButton;
	private JPanel panel_2;

	public void filtrar() {
		RowFilter<ProductoTableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter("(?i)" + textFieldBuscadorProductos.getText(), 0, 1, 2);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}

	public ProductoPanel(JFrame parent) {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		panelTitulos = new JPanel();
		add(panelTitulos);
		panelTitulos.setMaximumSize(new Dimension(3500, 50));
		panelTitulos.setLayout(new BorderLayout(0, 0));
		JLabel lblTituloProductos = new JLabel("STOCK");
		panelTitulos.add(lblTituloProductos, BorderLayout.EAST);
		lblTituloProductos.setBorder(new EmptyBorder(10, 0, 10, 20));
		lblTituloProductos.setFont(Constants.FUENTE_TITULO);
		lblTituloProductos.setBounds(10, 11, 125, 20);

		lblTituloProductos_2 = new JLabel("PRODUCTOS");
		lblTituloProductos_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTituloProductos_2.setBorder(new EmptyBorder(10, 20, 10, 0));
		lblTituloProductos_2.setAlignmentX(0.5f);
		panelTitulos.add(lblTituloProductos_2);

		panelBotones = new JPanel();
		panelBotones.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelBotones.setMaximumSize(new Dimension(3500, 50));
		panelBotones.setAlignmentY(Component.TOP_ALIGNMENT);
		add(panelBotones);
		panelBotones.setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		panelBotones.add(panel, BorderLayout.WEST);

		btnNuevoProducto = new JButton(Constants.ICONO_AGREGAR);
		panel.add(btnNuevoProducto);
		btnNuevoProducto.setMnemonic(KeyEvent.VK_N);

		btnImportar = new JButton(Constants.ICONO_IMPORTAR);
		panel.add(btnImportar);
		btnImportar.setMnemonic(KeyEvent.VK_I);

		panel_1 = new JPanel();
		panelBotones.add(panel_1, BorderLayout.EAST);

		btnIngresoStock = new JButton(Constants.ICONO_AGREGAR);
		panel_1.add(btnIngresoStock);

		btnEgresoStock = new JButton(Constants.ICONO_QUITAR);
		panel_1.add(btnEgresoStock);

		lblListadoActual = new JLabel("LISTADO ACTUAL");
		lblListadoActual.setBorder(new EmptyBorder(10, 0, 10, 0));
		lblListadoActual.setHorizontalAlignment(SwingConstants.LEFT);
		lblListadoActual.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblListadoActual.setFont(Constants.FUENTE_SUB_TITULO);
		add(lblListadoActual);

		panelHEaderTabla = new JPanel();
		add(panelHEaderTabla);
		panelHEaderTabla.setLayout(new BorderLayout(50, 50));
		panelBuscador = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBuscador.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setAlignOnBaseline(true);
		panelBuscador.setMaximumSize(new Dimension(3500, 50));
		panelBuscador.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelHEaderTabla.add(panelBuscador, BorderLayout.WEST);
		panelBuscador.add(lblNewLabel);

		textFieldBuscadorProductos = new JTextField();
		panelBuscador.add(textFieldBuscadorProductos);
		textFieldBuscadorProductos.setColumns(20);

		panel_2 = new JPanel();
		panelHEaderTabla.add(panel_2, BorderLayout.EAST);

		btnNewButton = new JButton(Constants.ICONO_EXPORTAR);
		panel_2.add(btnNewButton);

		JScrollPane scrollPaneProductos = new JScrollPane();
		scrollPaneProductos.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		this.add(scrollPaneProductos);

		tableProductos = new JTable();

		tableProductos.setRowHeight(40);
		tableProductos.setFont(Constants.FUENTE);
		productoTableModel = new ProductoTableModel();
		sorter = new TableRowSorter<ProductoTableModel>(productoTableModel);
		tableProductos.setRowSorter(sorter);
		tableProductos.setModel(productoTableModel);
		tableProductos.getColumnModel().getColumn(0).setMaxWidth(100);
		tableProductos.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableProductos.getColumnModel().getColumn(1).setMaxWidth(200);
		tableProductos.getColumnModel().getColumn(1).setPreferredWidth(130);
		tableProductos.getColumnModel().getColumn(3).setMaxWidth(100);
		tableProductos.getColumnModel().getColumn(3).setPreferredWidth(80);
		tableProductos.getColumnModel().getColumn(4).setMaxWidth(200);
		tableProductos.getColumnModel().getColumn(4).setPreferredWidth(150);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableProductos.setDefaultRenderer(Object.class, rightRenderer);

		scrollPaneProductos.setViewportView(tableProductos);

	}

}
