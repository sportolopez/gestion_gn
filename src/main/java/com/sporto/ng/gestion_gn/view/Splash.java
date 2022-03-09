package com.sporto.ng.gestion_gn.view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.MatteBorder;

import com.sporto.ng.gestion_gn.config.Constants;

public class Splash extends JFrame {

	private JLabel imglabel;
	private ImageIcon img;
	Thread t = null;
	public Splash() {
		super("Splash");
		setSize(631, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setUndecorated(true);
		img = new ImageIcon(getClass().getResource("/logo.png"));
		imglabel = new JLabel(img);
		imglabel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		getContentPane().add(imglabel);
		getContentPane().setLayout(null);
		imglabel.setBounds(0, 0, 631, 500);
		JLabel lblNewLabel = new JLabel("Version: "+Constants.VERSION);
		imglabel.add(lblNewLabel);
		lblNewLabel.setBounds(480, 475, 200, 14);
		
		
		
	}
}