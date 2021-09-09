/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sporto.ng.gestion_gn.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_gn.config.Constants;

import lombok.Getter;
import java.awt.Toolkit;

/**
 *
 * @author sebap
 */
@SuppressWarnings("serial")
@Component
@Getter
public class HomeForm extends javax.swing.JFrame   {
	
	private JPanel panelClientes;
	private ProductoPanel productosPanel;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JSeparator separator;

	/**
	 * Creates new form HomeForm
	 */
	public HomeForm() {
		setResizable(false); 
		setTitle("Distribuidora GN");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\sebap\\git\\gestion_gn\\src\\main\\resources\\logo gn-02.png"));
		this.productosPanel = new ProductoPanel(this);
		productosPanel.getBtnImportar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		productosPanel.getTableProductos().setSize(1268, 0);
		productosPanel.getBtnNuevoProducto().setLocation(1172, 59);
		productosPanel.getBtnImportar().setLocation(1073, 59);
		productosPanel.setBounds(0, 0, 1314, 500);
		productosPanel.getBtnNuevoProducto().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
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
		
				btnNewButton_2 = new JButton("Productos");
				btnNewButton_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						layeredPane.removeAll();
						layeredPane.add(productosPanel);
						layeredPane.repaint();
						layeredPane.revalidate();
					}
				});
		
				btnNewButton_3 = new JButton("Clientes");
				btnNewButton_3.setEnabled(false);
				btnNewButton_3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						layeredPane.removeAll();
						layeredPane.add(panelClientes);
						layeredPane.repaint();
						layeredPane.revalidate();
					}
				});
		
		JButton btnNewButton_3_1 = new JButton("Pedidos");
		btnNewButton_3_1.setEnabled(false);
		
		JButton btnNewButton_3_1_1 = new JButton("Caja");
		btnNewButton_3_1_1.setEnabled(false);
		
		JButton btnProveedores = new JButton("Proveedores");
		btnProveedores.setEnabled(false);
		
		JButton btnAdmin = new JButton("Admin");
		btnAdmin.setEnabled(false);
		
		separator = new JSeparator();

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createSequentialGroup()
									.addComponent(btnNewButton_2)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnNewButton_3_1, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnNewButton_3_1_1, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnProveedores, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnAdmin, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)))
							.addGap(128))))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_2)
						.addComponent(btnNewButton_3)
						.addComponent(btnNewButton_3_1)
						.addComponent(btnNewButton_3_1_1)
						.addComponent(btnProveedores)
						.addComponent(btnAdmin))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(layeredPane, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);


		
		layeredPane.add(productosPanel);
		layeredPane.setLayout(null);

		getContentPane().setLayout(layout);
		setPreferredSize(new Dimension(1350, 600));
		pack();
	}// </editor-fold>//GEN-END:initComponents
}
