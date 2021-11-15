package com.sporto.ng.gestion_gn.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import com.sporto.ng.gestion_gn.view.model.PedidoProductoTable;
import java.awt.GridLayout;
import java.awt.Component;
import javax.swing.border.BevelBorder;

public class ImpresionPedido extends JPanel{
	private JTextField textFieldRazonSocial;
	private JTextField textFieldMail;
	public ImpresionPedido() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("NOTA DE PEDIDO");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblNewLabel);
		
		JPanel panelCliente = new JPanel();
		panelCliente.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		add(panelCliente);
		panelCliente.setLayout(new GridLayout(0, 5, 0, 0));
		
		JLabel lblNewLabel_1_1 = new JLabel("RAZON SOCIAL:");
		panelCliente.add(lblNewLabel_1_1);
		
		textFieldRazonSocial = new JTextField();
		textFieldRazonSocial.setEditable(false);
		textFieldRazonSocial.setColumns(8);
		panelCliente.add(textFieldRazonSocial);
		
		JLabel lblNewLabel_3_2 = new JLabel("MAIL:");
		panelCliente.add(lblNewLabel_3_2);
		
		textFieldMail = new JTextField();
		textFieldMail.setEditable(false);
		textFieldMail.setColumns(8);
		panelCliente.add(textFieldMail);
		
		JPanel panelPedido = new JPanel();
		add(panelPedido);
		
		JScrollPane scrollPane = new JScrollPane();
		panelPedido.add(scrollPane);
		PedidoProductoTable table = new PedidoProductoTable();
		scrollPane.setViewportView(table);
	}
	
	

}
