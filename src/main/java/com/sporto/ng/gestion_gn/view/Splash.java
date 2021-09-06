package com.sporto.ng.gestion_gn.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.jboss.logging.Logger.Level;
import org.slf4j.Logger;

public class Splash extends JFrame {

	private JLabel imglabel;
	private ImageIcon img;
	private static JProgressBar pbar;
	Thread t = null;

	public Splash() {
		super("Splash");
		setSize(631, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setUndecorated(true);
		img = new ImageIcon(getClass().getResource("/logo gn-02.png"));
		imglabel = new JLabel(img);
		add(imglabel);
		setLayout(null);
		pbar = new JProgressBar();
		pbar.setMinimum(0);
		pbar.setMaximum(100);
		pbar.setStringPainted(true);
		pbar.setForeground(Color.LIGHT_GRAY);
		imglabel.setBounds(0, 0, 631, 500);
		add(pbar);
		pbar.setPreferredSize(new Dimension(500, 30));
		pbar.setBounds(0, 500, 631, 20);

		Thread t = new Thread() {

			public void run() {
				int i = 0;
				while (i <= 100) {
					pbar.setValue(i);
					try {
						sleep(90);
					} catch (InterruptedException ex) {
						System.out.println("asdasd");
					}
					i++;
				}
			}
		};
		t.start();
	}
}