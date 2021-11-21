/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sporto.ng.gestion_gn.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.dao.ClienteDao;
import com.sporto.ng.gestion_gn.dao.PedidoDao;
import com.sporto.ng.gestion_gn.dao.PedidoProductoDao;
import com.sporto.ng.gestion_gn.dao.ProductoDao;

import lombok.Getter;

/**
 *
 * @author sebap
 */
@SuppressWarnings("serial")
@Component
@Getter
public class HomeForm extends javax.swing.JFrame {

	private ClientePanel panelClientes;
	private ProductoPanel productosPanel;
	private JButton botonProductos;
	private JButton btnNewButton_3;
	private JSeparator separator;
	private PedidoPanel panelPedidos;
	private JButton btnStock;
	private ClienteDao clienteDao;

	/**
	 * Creates new form HomeForm
	 * 
	 * @throws IOException
	 */
	public HomeForm(PedidoDao pedidoDao, ClienteDao clienteDao,PedidoProductoDao pedidoProductoDao,ProductoDao productoDao) throws IOException {
		this.clienteDao = clienteDao;
		setTitle("Distribuidora GN");
		URL resource = getClass().getClassLoader().getResource("icono.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(resource));
		this.productosPanel = new ProductoPanel(this);
		this.panelClientes = new ClientePanel(this);
		this.panelPedidos = new PedidoPanel(this,pedidoDao,productoDao,pedidoProductoDao);
		
		setPreferredSize(new Dimension(Constants.ANCHO, Constants.ALTO));
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		JLayeredPane layeredPane = new JLayeredPane();
		botonProductos = new JButton("PRODUCTOS");
		botonProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				productosPanel.showProductos();
				layeredPane.add(productosPanel);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		btnNewButton_3 = new JButton("CLIENTES");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				panelClientes.cargarLista(clienteDao.findAll());
				panelClientes.showClientes();
				layeredPane.add(panelClientes);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});

		btnStock = new JButton("STOCK");
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				productosPanel.showStock();
				layeredPane.add(productosPanel);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		JButton btnVentas = new JButton("VENTAS");
		btnVentas.setSize(300,btnVentas.getHeight());
		btnVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				panelClientes.cargarLista(clienteDao.findAll());
				panelClientes.showPrecios();
				layeredPane.add(panelClientes);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});

		JButton btnPedidos = new JButton("PEDIDOS");
		btnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				panelPedidos.cargarPedidos();
				layeredPane.add(panelPedidos);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		
		JButton btnCaja = new JButton("CAJA");
		btnCaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				panelClientes.cargarLista(clienteDao.findAll());
				panelClientes.showCaja();
				layeredPane.add(panelClientes);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		
		JButton btnAdmin = new JButton("ADMIN");
		btnAdmin.setEnabled(false);

		separator = new JSeparator();

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addComponent(layeredPane).addContainerGap())
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(layout.createSequentialGroup().addComponent(botonProductos)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 107,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnStock, GroupLayout.PREFERRED_SIZE, 107,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnVentas, GroupLayout.PREFERRED_SIZE, 107,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnPedidos, GroupLayout.PREFERRED_SIZE, 107,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnCaja, GroupLayout.PREFERRED_SIZE, 107,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnAdmin,
														GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)))
								.addGap(128)))));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(botonProductos)
								.addComponent(btnNewButton_3).addComponent(btnStock)
								.addComponent(btnVentas).addComponent(btnPedidos).addComponent(btnCaja).addComponent(btnAdmin))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE).addContainerGap()));
		layeredPane.setLayout(new BoxLayout(layeredPane, BoxLayout.X_AXIS));

		layeredPane.add(productosPanel);

		getContentPane().setLayout(layout);

		pack();
	}// </editor-fold>//GEN-END:initComponents
	
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
	    java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value instanceof javax.swing.plaf.FontUIResource)
	        UIManager.put (key, f);
	      }
	    } 
}
