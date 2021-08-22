/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sporto.ng.gestion_ng.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_ng.dao.ProductoDao;

/**
 *
 * @author sebap
 */
@Component
public class HomeForm extends javax.swing.JFrame   {
	
	private JPanel panelClientes;
	private JPanel panelProductos;
	private JScrollPane scrollPane_1;
	private JTextField textField_1;
	private JLabel lblClientes;
	private JButton btnNewButton_1;
	private JButton btnImportar_1;
	private JButton btnNuevoProducto_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	ProductoDao dao;

	/**
	 * Creates new form HomeForm
	 */
	@Autowired
	public HomeForm(ProductoDao dao,JPanel panelProductos) {
		this.dao = dao;
		this.panelProductos = panelProductos;
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
		
				btnNewButton_2 = new JButton("Productos(F1)");
				btnNewButton_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						layeredPane.removeAll();
						layeredPane.add(panelProductos);
						layeredPane.repaint();
						layeredPane.revalidate();
					}
				});
		
				btnNewButton_3 = new JButton("Clientes (F2)");
				btnNewButton_3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						layeredPane.removeAll();
						layeredPane.add(panelClientes);
						layeredPane.repaint();
						layeredPane.revalidate();
					}
				});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup()
							.addComponent(btnNewButton_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_2)
						.addComponent(btnNewButton_3))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(layeredPane, GroupLayout.PREFERRED_SIZE, 562, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);


		
		layeredPane.add(panelProductos);

		

		panelClientes = new JPanel();
		panelClientes.setLayout(null);
		panelClientes.setBounds(0, 50, 625, 539);
		layeredPane.add(panelClientes);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 91, 574, 194);
		panelClientes.add(scrollPane_1);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 60, 254, 20);
		panelClientes.add(textField_1);

		lblClientes = new JLabel("Clientes");
		lblClientes.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblClientes.setBounds(10, 11, 125, 20);
		panelClientes.add(lblClientes);

		btnNewButton_1 = new JButton("Buscar");
		btnNewButton_1.setBounds(277, 59, 89, 23);
		panelClientes.add(btnNewButton_1);

		btnImportar_1 = new JButton("Importar");
		btnImportar_1.setBounds(376, 59, 89, 23);
		panelClientes.add(btnImportar_1);

		btnNuevoProducto_1 = new JButton("Nuevo");
		btnNuevoProducto_1.setBounds(475, 59, 109, 23);
		panelClientes.add(btnNuevoProducto_1);
		getContentPane().setLayout(layout);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * @param args the command line arguments
	 */
//	public static void main(String args[]) {
//
//		SpringApplication.run(HomeForm.class, args);
//		/* Set the Nimbus look and feel */
//		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
//		// (optional) ">
//		/*
//		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
//		 * look and feel. For details see
//		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//		 */
//		try {
//			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//				if ("Nimbus".equals(info.getName())) {
//					javax.swing.UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
//			}
//		} catch (ClassNotFoundException ex) {
//			java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (InstantiationException ex) {
//			java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (IllegalAccessException ex) {
//			java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
//			java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		}
//		// </editor-fold>
//
//		/* Create and display the form */
//		java.awt.EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				new HomeForm().setVisible(true);
//			}
//		});
//	}

	
}
