package com.sporto.ng.gestion_gn.pruebas;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

public class IconosJTabbedPane {
    /**
     * Crea un JFrame con un JTabbedPane dentro. A cada pestaña del 
     * JTabbedPane le pone un icono y un color
     * @param args
     */
    public static void main(String[] args) {
        // La ventana principal
        JFrame v = new JFrame("Ejemplo Iconos JTabbedPane");

        // El JTabbedPane con sus pestañas e iconos en las mismas.
        JTabbedPane tp = new JTabbedPane();
        tp.add("uno", new JLabel("En la pestaña uno"));
        tp.add("dos", new JLabel("En la pestaña dos"));
        tp.add("tres", new JLabel("En la pestaña tres"));
        tp.add("cuatro", new JLabel("En la pestaña cuatro"));
        
        // Los iconos
        tp.setIconAt(0, new ImageIcon("kopete010.gif"));
        tp.setIconAt(1, new ImageIcon("kopete011.gif"));
        tp.setIconAt(2, new ImageIcon("kopete012.gif"));
        tp.setIconAt(3, new ImageIcon("kopete013.gif"));
        
        // Los colores de fondo
        tp.setBackgroundAt(0, Color.yellow);
        tp.setBackgroundAt(1, Color.red);
        tp.setBackgroundAt(2, Color.green);
        tp.setBackgroundAt(3, Color.blue);

        // Se visualiza todo
        v.getContentPane().add(tp);
        v.setSize(500,300);
        v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        v.setVisible(true);
    }
}