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

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
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
import javax.swing.Icon;

@Getter
public class ClientePanel extends JPanel {

	private JButton btnNuevoCliente;
	private JButton btnExportar;
	private ClienteTable tableClientes;
	TableRowSorter<ClienteTableModel> sorter;
	JTextField textFieldBuscador;
	private JButton btnImportarPrecios;
	
	JLabel lblTitulo;
	private JButton btnEgreso;
	private JButton btnArqueoDelDia;

	public ClientePanel(JFrame parent) {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		lblTitulo = new JLabel("CLIENTES");
		lblTitulo.setFont(Constants.FUENTE_TITULO);
		lblTitulo.setBorder(new EmptyBorder(10, 20, 10, 0));
		lblTitulo.setAlignmentX(0.5f);
		panel.add(lblTitulo);

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
				jfc.setDialogTitle("Exportar clientes..");
				jfc.setFileFilter(new FileNameExtensionFilter(".xls", "xls"));
				if (jfc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
					File file = jfc.getSelectedFile();
					if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xls")) {
						file = new File(file.toString() + ".xls");
					}
					String heading = "Clientes";
					JTableToExcel.export(file, heading, "", tableClientes,8);
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
		
		btnImportarPrecios = new JButton(Constants.ICONO_IMPORTAR);
		panelBotonera.add(btnImportarPrecios);
		btnImportarPrecios.setMnemonic(KeyEvent.VK_I);
		btnEgreso = new JButton(Constants.ICONO_PAGO);
		btnEgreso.setHorizontalTextPosition(AbstractButton.LEADING); 
		btnEgreso.setText("EGRESO");
		btnEgreso.setMnemonic(KeyEvent.VK_I);
		panelBotonera.add(btnEgreso);
		
		btnArqueoDelDia = new JButton(Constants.ICONO_CAJA);
		btnArqueoDelDia.setText("ARQUEO CAJA");
		btnArqueoDelDia.setMnemonic(KeyEvent.VK_I);
		btnArqueoDelDia.setHorizontalTextPosition(SwingConstants.LEADING);
		panelBotonera.add(btnArqueoDelDia);


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
	
	public void showClientes() {
		lblTitulo.setText("CLIENTES");
		btnNuevoCliente.setVisible(true);
		btnImportarPrecios.setVisible(false);
		btnExportar.setVisible(true);
		btnEgreso.setVisible(false);
		ocultarColumna(ClienteTableModel.COLUMN_PEDIDO);
		ocultarColumna(ClienteTableModel.COLUMN_LIBERAR);
		ocultarColumna(ClienteTableModel.COLUMN_DOMICILIO);
		mostrarColumna(ClienteTableModel.COLUMN_EDITAR);
		mostrarColumna(ClienteTableModel.COLUMN_EXPORTAR);
		mostrarColumna(ClienteTableModel.COLUMN_EDITAR);
		mostrarColumna(ClienteTableModel.COLUMN_MAIL);
		mostrarColumna(ClienteTableModel.COLUMN_DOMICILIO);
	}
	
	public void showPrecios() {
		lblTitulo.setText("PRECIOS");
		btnNuevoCliente.setVisible(false);
		btnImportarPrecios.setVisible(true);
		btnExportar.setVisible(false);
		btnEgreso.setVisible(false);
		ocultarColumna(ClienteTableModel.COLUMN_LIBERAR);
		ocultarColumna(ClienteTableModel.COLUMN_EDITAR);
		ocultarColumna(ClienteTableModel.COLUMN_DOMICILIO);
		mostrarColumna(ClienteTableModel.COLUMN_PEDIDO);
		mostrarColumna(ClienteTableModel.COLUMN_EXPORTAR);
		mostrarColumna(ClienteTableModel.COLUMN_MAIL);
	}

	public void showCaja() {
		lblTitulo.setText("CAJA");
		btnNuevoCliente.setVisible(false);
		btnImportarPrecios.setVisible(false);
		btnExportar.setVisible(true);
		btnEgreso.setVisible(true);
		ocultarColumna(ClienteTableModel.COLUMN_PEDIDO);
		ocultarColumna(ClienteTableModel.COLUMN_EXPORTAR);
		ocultarColumna(ClienteTableModel.COLUMN_EDITAR);
		ocultarColumna(ClienteTableModel.COLUMN_MAIL);
		ocultarColumna(ClienteTableModel.COLUMN_DOMICILIO);
		mostrarColumna(ClienteTableModel.COLUMN_LIBERAR);
		
	}
	
	private void ocultarColumna(int columnIndex) {
		tableClientes.getColumnModel().getColumn(columnIndex).setMinWidth(0);
		tableClientes.getColumnModel().getColumn(columnIndex).setMaxWidth(0);
		tableClientes.getColumnModel().getColumn(columnIndex).setPreferredWidth(0);
	}

	private void mostrarColumna(int columnIndex) {
		int width = 100;
		tableClientes.getColumnModel().getColumn(columnIndex).setMinWidth(width);
		tableClientes.getColumnModel().getColumn(columnIndex).setMaxWidth(width);
		tableClientes.getColumnModel().getColumn(columnIndex).setPreferredWidth(width);
	}
}
