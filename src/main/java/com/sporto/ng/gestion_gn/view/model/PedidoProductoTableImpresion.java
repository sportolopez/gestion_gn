package com.sporto.ng.gestion_gn.view.model;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.MessageFormat;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.sporto.ng.gestion_gn.model.Pedido;
import com.sporto.ng.gestion_gn.utils.MyTablePrintable;

public class PedidoProductoTableImpresion extends JTable {

	private MessageFormat[] header;
	private MessageFormat[] footer;
	
	
	public PedidoProductoTableImpresion() {
		
		
	}

	

}
