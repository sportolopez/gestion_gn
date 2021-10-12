package com.sporto.ng.gestion_gn.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import org.apache.commons.io.FilenameUtils;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.utils.JTableToExcel;
import com.sporto.ng.gestion_gn.view.model.ProductoTableModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoPanel extends JPanel {

	private ProductoTableModel productoTableModel;
	private JButton btnNuevoProducto;
	private JButton btnImportar;
	private JTable tableProductos;
	JTextField textFieldBuscadorProductos;
	TableRowSorter<ProductoTableModel> sorter;
	JButton btnIngresoStock;
	private JLabel lblListadoActual;
	JButton btnEgresoStock;
	private final JLabel lblBuscar = new JLabel("BUSCAR");
	private JLabel lblTituloProductos_1;
	private JLabel lblTituloProductos_2;
	private JButton btnExportar;

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

		JPanel panelTitulos = new JPanel();
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

		JPanel panelBotones = new JPanel();
		panelBotones.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelBotones.setMaximumSize(new Dimension(3500, 50));
		panelBotones.setAlignmentY(Component.TOP_ALIGNMENT);
		add(panelBotones);
		panelBotones.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panelBotones.add(panel, BorderLayout.WEST);

		btnNuevoProducto = new JButton(Constants.ICONO_AGREGAR);
		panel.add(btnNuevoProducto);
		btnNuevoProducto.setMnemonic(KeyEvent.VK_N);

		btnImportar = new JButton(Constants.ICONO_IMPORTAR);
		panel.add(btnImportar);
		btnImportar.setMnemonic(KeyEvent.VK_I);

		JPanel panel_1 = new JPanel();
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

		JPanel panelHEaderTabla = new JPanel();
		add(panelHEaderTabla);
		panelHEaderTabla.setLayout(new BorderLayout(50, 50));
		JPanel panelBuscador = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBuscador.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setAlignOnBaseline(true);
		panelBuscador.setMaximumSize(new Dimension(3500, 50));
		panelBuscador.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelHEaderTabla.add(panelBuscador, BorderLayout.WEST);
		panelBuscador.add(lblBuscar);

		textFieldBuscadorProductos = new JTextField();
		panelBuscador.add(textFieldBuscadorProductos);
		textFieldBuscadorProductos.setColumns(20);

		JPanel panel_2 = new JPanel();
		panelHEaderTabla.add(panel_2, BorderLayout.EAST);

		btnExportar = new JButton(Constants.ICONO_EXPORTAR);
		panel_2.add(btnExportar);
		
		btnExportar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Guardar como..");
				jfc.setFileFilter(new FileNameExtensionFilter(".xls", "xls"));
				if (jfc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
					File file = jfc.getSelectedFile();
					if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xls")) {
					    file = new File(file.toString() + ".xls");
					}
					String heading = "Stock";
					JTableToExcel.export(file, heading, "", tableProductos);
				}
			}
		});

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
