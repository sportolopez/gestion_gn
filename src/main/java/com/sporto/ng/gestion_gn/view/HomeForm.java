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

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_gn.config.Constants;

import lombok.Getter;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.BoxLayout;
import java.awt.Font;

/**
 *
 * @author sebap
 */
@SuppressWarnings("serial")
@Component
@Getter
public class HomeForm extends javax.swing.JFrame {

	private JPanel panelClientes;
	private ProductoPanel productosPanel;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JSeparator separator;

	/**
	 * Creates new form HomeForm
	 * 
	 * @throws IOException
	 */
	public HomeForm() throws IOException {
		setTitle("Distribuidora GN");
		URL resource = getClass().getClassLoader().getResource("icono.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(resource));
		this.productosPanel = new ProductoPanel(this);
		productosPanel.getPanel().setAlignmentY(java.awt.Component.TOP_ALIGNMENT);
		
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
		btnNewButton_2 = new JButton("Productos");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(productosPanel);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		btnNewButton_2.setFont(Constants.FUENTE);
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
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addComponent(layeredPane).addContainerGap())
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(layout.createSequentialGroup().addComponent(btnNewButton_2)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 107,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnNewButton_3_1, GroupLayout.PREFERRED_SIZE, 107,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnNewButton_3_1_1, GroupLayout.PREFERRED_SIZE, 107,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnProveedores, GroupLayout.PREFERRED_SIZE, 107,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnAdmin,
														GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)))
								.addGap(128)))));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(btnNewButton_2)
								.addComponent(btnNewButton_3).addComponent(btnNewButton_3_1)
								.addComponent(btnNewButton_3_1_1).addComponent(btnProveedores).addComponent(btnAdmin))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE).addContainerGap()));
		layeredPane.setLayout(new BoxLayout(layeredPane, BoxLayout.X_AXIS));

		layeredPane.add(productosPanel);

		getContentPane().setLayout(layout);

		pack();
	}// </editor-fold>//GEN-END:initComponents
}
