package com.sporto.ng.gestion_gn.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableRowSorter;

import org.apache.commons.io.FilenameUtils;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.utils.JTableToExcel;
import com.sporto.ng.gestion_gn.view.model.ClienteTable;
import com.sporto.ng.gestion_gn.view.model.ClienteTableModel;

import lombok.Getter;

@Getter
public class ClientePanel extends JPanel {

	private JButton btnNuevoCliente;
	private JButton btnExportar;
	private ClienteTable tableClientes;
	TableRowSorter<ClienteTableModel> sorter;
	JTextField textFieldBuscador;
	private JButton btnImportar;

	public ClientePanel(JFrame parent) {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel lblTituloProductos = new JLabel("CLIENTES");
		lblTituloProductos.setBorder(new EmptyBorder(10, 0, 10, 0));
		lblTituloProductos.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTituloProductos.setFont(Constants.FUENTE_TITULO);
		lblTituloProductos.setBounds(10, 11, 125, 20);
		this.add(lblTituloProductos);

		crearBotonera();

		JLabel lblListadoActual = new JLabel("LISTADO ACTUAL");
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
		panelBuscador.setMaximumSize(new Dimension(3500, 50));
		panelBuscador.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelHEaderTabla.add(panelBuscador, BorderLayout.WEST);
		panelBuscador.add(new JLabel("BUSCAR"));

		textFieldBuscador = new JTextField();
		panelBuscador.add(textFieldBuscador);
		textFieldBuscador.setColumns(20);
		textFieldBuscador.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				filtrar();
			}
		});

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
					String heading = "Clientes";
					JTableToExcel.export(file, heading, "", tableClientes);
				}
			}
		});

		JScrollPane scrollPaneProductos = new JScrollPane();
		scrollPaneProductos.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		this.add(scrollPaneProductos);

		tableClientes = new ClienteTable();
		scrollPaneProductos.setViewportView(tableClientes);
		sorter = new TableRowSorter<ClienteTableModel>((ClienteTableModel) tableClientes.getModel());
		tableClientes.setRowSorter(sorter);

	}

	public JButton getBtnNuevoCliente() {
		return btnNuevoCliente;
	}

	public ClienteTable getTableClientes() {
		return tableClientes;
	}

	public JButton getBtnExportar() {
		return btnExportar;
	}

	private void crearBotonera() {
		JComponent panelBotonera = new JPanel();
		panelBotonera.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelBotonera.setMaximumSize(new Dimension(3500, 50));
		panelBotonera.setAlignmentY(Component.TOP_ALIGNMENT);
		FlowLayout fl_panelBotonera = (FlowLayout) panelBotonera.getLayout();
		fl_panelBotonera.setAlignOnBaseline(true);
		fl_panelBotonera.setAlignment(FlowLayout.LEFT);
		add(panelBotonera);

		btnNuevoCliente = new JButton(Constants.ICONO_AGREGAR);
		panelBotonera.add(btnNuevoCliente);
		btnNuevoCliente.setMnemonic(KeyEvent.VK_N);
		
		btnImportar = new JButton(Constants.ICONO_IMPORTAR);
		panelBotonera.add(btnImportar);
		btnImportar.setMnemonic(KeyEvent.VK_I);


	}

	public void cargarLista(List<Cliente> lista) {
		ClienteTableModel tableModel = (ClienteTableModel) tableClientes.getModel();
		tableModel.setRowCount(0);

		for (Cliente unCliente : lista) {
			tableModel.addCliente(unCliente);
		}

	}

	public void filtrar() {
		RowFilter<ClienteTableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter("(?i)" + textFieldBuscador.getText(), 0, 1, 2, 3);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}

}
