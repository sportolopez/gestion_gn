package com.sporto.ng.gestion_gn.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
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
import com.sporto.ng.gestion_gn.model.Denominacion;
import com.sporto.ng.gestion_gn.utils.ArqueoCajaExporter;

public class ArqueoCajaDialog extends JDialog {
	private JTable table;
	private JButton btnArqueoDelDia;
	private ArqueoCajaExporter arqueoCajaExporter;

	public JButton getBtnArqueoDelDia() {
		return btnArqueoDelDia;
	}

	public ArqueoCajaDialog(ArqueoCajaExporter arqueoCajaExporter, HomeForm homeForm) {
		super(homeForm);
		URL resource = getClass().getClassLoader().getResource("icono.ico");
		setIconImage(Toolkit.getDefaultToolkit().getImage(resource));
		setTitle("Arqueo de Caja");
		setSize(new Dimension(500, 500));
		setResizable(false);
		this.arqueoCajaExporter = arqueoCajaExporter;
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JLabel lblNewLabel = new JLabel("ARQUEO CAJA "+Constants.outFecha(new Date()));
		lblNewLabel.setFont(Constants.FUENTE_TITULO);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(lblNewLabel);
		

		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		
		table = new JTable();
		table.setEnabled(false);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setRowHeight(40);
		table.setFont(Constants.FUENTE);
		((DefaultTableModel)table.getModel()).addColumn("Columna 1");
		((DefaultTableModel)table.getModel()).addColumn("Columna 1");
		
		for (int i = 0; i < Denominacion.values().length; i++) {
			Denominacion denominacion = Denominacion.values()[i];
			Double totalListaDenominacion = arqueoCajaExporter.getTotalListaDenominacion(denominacion);
			ArrayList<Object> lista = new ArrayList<Object>();
			lista.add("Total en Billetes de "+denominacion.getStringValue());
			lista.add(Constants.outDouble(totalListaDenominacion));
			((DefaultTableModel)table.getModel()).addRow(lista.toArray());
		}
//		
		((DefaultTableModel)table.getModel()).addRow(new Object[]{"Total Banco",Constants.outDouble(arqueoCajaExporter.getTotalBanco())});
		((DefaultTableModel)table.getModel()).addRow(new Object[]{"Total Gastos",Constants.outDouble(arqueoCajaExporter.getTotalGastos())});
		((DefaultTableModel)table.getModel()).addRow(new Object[]{"SALDO CAJA",Constants.outDouble(arqueoCajaExporter.getSaldoCaja())});
	
		table.setDefaultRenderer(Object.class, new BoldRenderer());
		
		table.getColumnModel().getColumn(0).setMinWidth(200);
		table.getRowCount();
		
		panel.add(table);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setSize(new Dimension(0, 50));
		getContentPane().add(panel_1);
		
		
		btnArqueoDelDia = new JButton(Constants.ICONO_CAJA);
		btnArqueoDelDia.setText("EXPORTAR");
		btnArqueoDelDia.setMnemonic(KeyEvent.VK_I);
		btnArqueoDelDia.setHorizontalTextPosition(SwingConstants.LEADING);
		panel_1.add(btnArqueoDelDia);

	}
	
    class BoldRenderer extends DefaultTableCellRenderer {
    	
    	public BoldRenderer() {
    		super();
    		setHorizontalAlignment(SwingConstants.CENTER);
		}

        public Component getTableCellRendererComponent(JTable tblData,
                Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(
                    tblData, value, isSelected, hasFocus, row, column);

            if (tblData.getValueAt(row, 0).equals("SALDO CAJA")) {
                cellComponent.setFont(cellComponent.getFont().deriveFont(
                        Font.BOLD));
            } 

            return cellComponent;
        }
    }

}
