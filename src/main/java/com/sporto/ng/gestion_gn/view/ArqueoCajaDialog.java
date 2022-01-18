package com.sporto.ng.gestion_gn.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;

public class ArqueoCajaDialog  extends JDialog{
	private JTable table;
	private JButton btnArqueoDelDia;
	
	public JButton getBtnArqueoDelDia() {
		return btnArqueoDelDia;
	}

	public ArqueoCajaDialog() {
		setPreferredSize(new Dimension(500, 600));
		setResizable(false);
	
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JLabel lblNewLabel = new JLabel("ARQUEO CAJA "+Constants.outFecha(new Date()));
		lblNewLabel.setFont(Constants.FUENTE_TITULO);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(lblNewLabel);
		

		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		
		table = new JTable();
		table.setEnabled(false);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, rightRenderer);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
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
		
		
		btnArqueoDelDia = new JButton(Constants.ICONO_CAJA);
		btnArqueoDelDia.setText("EXPORTAR");
		btnArqueoDelDia.setMnemonic(KeyEvent.VK_I);
		btnArqueoDelDia.setHorizontalTextPosition(SwingConstants.LEADING);
		panel_1.add(btnArqueoDelDia);

	}

}
