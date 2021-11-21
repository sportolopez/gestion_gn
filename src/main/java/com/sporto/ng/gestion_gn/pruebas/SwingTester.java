package com.sporto.ng.gestion_gn.pruebas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sporto.ng.gestion_gn.config.Constants;

public class SwingTester {
   public static void main(String[] args) {
      createWindow();
   }

   private static void createWindow() {    
      JFrame frame = new JFrame("Swing Tester");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      createUI(frame);
      frame.setSize(560, 200);      
      frame.setLocationRelativeTo(null);  
      frame.setVisible(true);
   }

   private static void createUI(final JFrame frame){
      JPanel panel = new JPanel();
      LayoutManager layout = new FlowLayout();  
      panel.setLayout(layout);       
      ImageIcon arrowIcon = null;

      JButton iconButton = new JButton(Constants.ICONO_AGREGAR);
      iconButton.setText("Next");
      iconButton.setToolTipText("Move Ahead");
      iconButton.setHorizontalTextPosition(AbstractButton.LEADING); 
      iconButton.setMnemonic(KeyEvent.VK_I);
      iconButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame, "Icon Button clicked.");
         }
      });   

      panel.add(iconButton);
      frame.getContentPane().add(panel, BorderLayout.CENTER);    
   }
}