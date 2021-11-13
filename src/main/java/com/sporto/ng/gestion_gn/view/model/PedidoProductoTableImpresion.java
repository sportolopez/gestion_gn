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

	@Override
	public Printable getPrintable(PrintMode printMode, MessageFormat headerFormat, MessageFormat footerFormat) {
		return new MyTablePrintable(this, PrintMode.FIT_WIDTH, header, footer);
	}
	
	public void imprimir(Pedido unPedido) throws PrinterException {
		header = new MessageFormat[3];
		header[0] = new MessageFormat("");
		header[1] = new MessageFormat("Nro pedido: " + unPedido.getId());
		header[2] = new MessageFormat("CLIENTE: " + unPedido.getCliente().getRazonSocial() + "           DOMICILIO: "
				+ unPedido.getCliente().getDomicilio());

		footer = new MessageFormat[1];
		footer[0] = new MessageFormat(" footer ");

		PrinterJob job = PrinterJob.getPrinterJob();
		PageFormat pf = job.defaultPage();
		Paper paper = pf.getPaper();
		double margin = 10.;
		paper.setImageableArea(margin, 10, paper.getWidth() - 2 * margin, paper.getImageableHeight());
		pf.setPaper(paper);
		job.setPrintable(this.getPrintable(JTable.PrintMode.FIT_WIDTH,
				new MessageFormat("Nro pedido: " + unPedido.getId()), null), job.validatePage(pf));

		boolean printAccepted = job.printDialog();
		if (printAccepted) {
			try {
				job.print();
			} catch (PrinterException e) {
				throw new RuntimeException(e);
			}
		}

	}

}
