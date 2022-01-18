package com.sporto.ng.gestion_gn.view;

import java.awt.Component;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.sporto.ng.gestion_gn.config.Constants;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class ArqueoCajaDialog  extends JDialog{
	private JTable table;
	public ArqueoCajaDialog() {
	
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JLabel lblNewLabel = new JLabel("ARQUEO CAJA "+Constants.outFecha(new Date()));
		lblNewLabel.setFont(Constants.FUENTE_TITULO);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(lblNewLabel);
		

		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		
		table = new JTable();
		table.setRowHeight(40);
		table.setFont(Constants.FUENTE);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Total en Billetes de 1000", "500"},
				{"Total en Billetes de 500", "500"},
				{"Total en Billetes de 200", "500"},
				{"Total en Billetes de 100", "500"},
				{"Total en Billetes Varios", "500"},
			},
			new String[] {
				"New column", "New column"
			}
		));
		
		table.getColumnModel().getColumn(0).setMinWidth(200);
		
		panel.add(table);
		
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		
		
		JButton btnArqueoDelDia = new JButton(Constants.ICONO_CAJA);
		btnArqueoDelDia.setText("EXPORTAR");
		btnArqueoDelDia.setMnemonic(KeyEvent.VK_I);
		btnArqueoDelDia.setHorizontalTextPosition(SwingConstants.LEADING);
		panel_1.add(btnArqueoDelDia);

	}

}
