package com.sporto.ng.gestion_gn.utils;

import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

class PrinterTest {

	@Test
	void test() throws PrinterException {
		JFrame yourComponent = new JFrame();
		PrinterJob pjob = PrinterJob.getPrinterJob();
		PageFormat preformat = pjob.defaultPage();
		preformat.setOrientation(PageFormat.LANDSCAPE);
		PageFormat postformat = pjob.pageDialog(preformat);
		//If user does not hit cancel then print.
		if (preformat != postformat) {
		    //Set print component
		    pjob.setPrintable(new Printer(yourComponent), postformat);
		    if (pjob.printDialog()) {
		        pjob.print();
		    }
		}
	}

}
