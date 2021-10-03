package com.sporto.ng.gestion_gn.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.logging.log4j.util.Strings;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.dao.MovimientoStockDao;
import com.sporto.ng.gestion_gn.dao.ProductoDao;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.model.TipoMovimiento;
import com.sporto.ng.gestion_gn.utils.Java2sAutoComboBox;
import com.sporto.ng.gestion_gn.view.validations.FechaVerifier;
import com.sporto.ng.gestion_gn.view.validations.NumeroVerifier;

public class MovimientoStock extends JDialog {
	private JTable table;
	private JTextField textFieldCantidad;
	private JTextField textFieldVencimiento;
	private ImageIcon eliminarIcon;
	private JButton botonGuardar;
	private java.util.Set<String> camposInvalidos = new HashSet<String>();
	List<Producto> listaProductos;
	MovimientoStockDao movimientoDao;
	ProductoDao productoDao;
	
	public MovimientoStock(List<Producto> listaProductos, MovimientoStockDao movimientoDao, ProductoDao productoDao) {
		this.listaProductos = listaProductos;
		this.movimientoDao = movimientoDao;
		this.productoDao = productoDao;
		URL resource = getClass().getClassLoader().getResource("icono.ico");
		eliminarIcon = new ImageIcon(getClass().getClassLoader().getResource("iconos/Trash-empty-icon.png"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(resource));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 760, 261);
		setTitle("Ingresar Stock");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JLabel lblNewLabel = new JLabel("INGRESAR STOCK");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(lblNewLabel);

		JPanel panelAgregar = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelAgregar.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panelAgregar);

		JLabel lblNewLabel_1 = new JLabel("Codigo");
		panelAgregar.add(lblNewLabel_1);

		List<String> myWords = listaProductos.stream().map(Producto::getDescripcionCombo).collect(Collectors.toList());
		myWords = new ArrayList<>(myWords);
		myWords.add(0, "");
		Java2sAutoComboBox textCodigoProducto = new Java2sAutoComboBox(myWords);
//		JComboBox textCodigoProducto = new JComboBox();
//		DefaultComboBoxModel mod=new DefaultComboBoxModel(listaProductos.toArray());
//		textCodigoProducto.setModel(mod);
		// textCodigoProducto.setDataList(myWords);

		panelAgregar.add(textCodigoProducto);

		JLabel lblNewLabel_3 = new JLabel("Cantidad");
		panelAgregar.add(lblNewLabel_3);

		textFieldCantidad = new JTextField();
		panelAgregar.add(textFieldCantidad);
		textFieldCantidad.setColumns(8);

		JLabel lblNewLabel_4 = new JLabel("Vencimiento");
		panelAgregar.add(lblNewLabel_4);

		textFieldVencimiento = new JFormattedTextField(Constants.getMascaraFecha());
		textFieldVencimiento.setBounds(107, 45, 86, 20);
		textFieldVencimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldVencimiento.setToolTipText("DD/MM/AAAA");
		textFieldVencimiento.setFont(Constants.FUENTE);
		panelAgregar.add(textFieldVencimiento);
		textFieldVencimiento.setColumns(10);

		JButton agregarStockBtn = new JButton("AGREGAR STOCK");
		agregarStockBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String selectedItem = (String) textCodigoProducto.getSelectedItem();
				if (Strings.isNotEmpty(selectedItem)) {
					
					if (validar()) {
						Integer idProducto = Integer.parseInt(selectedItem.split(":")[0]);
						Producto unProducto = listaProductos.stream().filter(producto -> producto.getId() == idProducto)
								.findAny().get();
						((DefaultTableModel) table.getModel())
								.addRow(new Object[] { unProducto.getId(), unProducto.getDescripcion(), 
										textFieldCantidad.getText(), textFieldVencimiento.getText(),eliminarIcon });
					}
					

				}

			}
		});
		agregarStockBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelAgregar.add(agregarStockBtn);

		JPanel panelBotones = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBotones.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		table = new JTable();
		table.setRowHeight(30);
		String[] columnNames = { "C\u00F3digo Producto", "Descrici\u00F3n",  "Cantidad","Vencimiento", "Cancelar" };
		Object[][] data = { };

		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public Class getColumnClass(int column) {
				if (column == 4)
					return ImageIcon.class;
				return Object.class;
			}

			@Override
			public boolean isCellEditable(int row, int col) {
				if (col > 3 || col == 1) {
					return false;
				} else {
					return true;
				}
			}

		};
		table.setModel(model);
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = table.rowAtPoint(evt.getPoint());
				int col = table.columnAtPoint(evt.getPoint());
				if (col == 4) {
					((DefaultTableModel) table.getModel()).removeRow(row);
				}

			}
		});
		scrollPane.setViewportView(table);
		getContentPane().add(panelBotones);

		JButton btnNewButton = new JButton("CANCELAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelBotones.add(btnNewButton);

		botonGuardar = new JButton("GUARDAR");
		botonGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarMovimientos();
			}
		});
		botonGuardar.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelBotones.add(botonGuardar);
		configValidations();
		pack();

	}

	public JButton getBotonGuardar() {
		return botonGuardar;
	}
	
	public JTable getTablaMovimientos() {
		return table;
	}
	
	private void configValidations() {
		textFieldVencimiento.setInputVerifier(new FechaVerifier("Fecha de Vencimiento", camposInvalidos));
		textFieldCantidad.setInputVerifier(new NumeroVerifier("Cantidad", camposInvalidos));
	}
	
	public boolean validar() {
		textFieldVencimiento.requestFocus();
		textFieldCantidad.requestFocus();
		botonGuardar.requestFocus();
		if (camposInvalidos.size() > 0) {
			JOptionPane.showMessageDialog(new JFrame(), "Campos invalidos:" + camposInvalidos, "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;

	}
	
	public void guardarMovimientos() {
		TableModel tableModel = table.getModel();
		int nRow = tableModel.getRowCount(), nCol = tableModel.getColumnCount();
		
		try {
			for (int i = 0 ; i < nRow ; i++) {
				int parseInt = Integer.parseInt(tableModel.getValueAt(i, 0).toString());
				Producto unProducto = listaProductos.stream().filter(producto -> producto.getId() == parseInt).findAny().get();
				Date fechaV = Constants.FORMATO_FECHA.parse((tableModel.getValueAt(i, 3).toString()));
				if(unProducto.getFechaVencimiento() == null || unProducto.getFechaVencimiento().before(fechaV)) {
					unProducto.setFechaVencimiento(fechaV);
					productoDao.save(unProducto);
				}
				Integer cantidad = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
				com.sporto.ng.gestion_gn.model.MovimientoStock movimiento = com.sporto.ng.gestion_gn.model.MovimientoStock.builder().cantidad(cantidad).fecha(new Date()).tipoMovimiento(TipoMovimiento.INGRESO).producto(unProducto).build();
				movimientoDao.save(movimiento);
				
			}
			JOptionPane.showMessageDialog(this, "Los movimientos se registraron correctamente");
			setVisible(false);
		} catch (NumberFormatException | ParseException e) {
			throw new RuntimeException(e);
		}
			
			
	}

}
