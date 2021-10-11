package com.sporto.ng.gestion_gn.config;

import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.text.MaskFormatter;

public class Constants {
	public static final Font FUENTE_TABLE_HEADER = new Font("Dialog", Font.BOLD, 12);
	public static final Font FUENTE = new Font("Dialog", Font.PLAIN, 12);
	public static final Font FUENTE_BUTTON = new Font("Tahoma", Font.BOLD, 12);
	public static final Font FUENTE_LABEL = new Font("Tahoma", Font.BOLD, 12);
	public static final Font FUENTE_TITULO = new Font("Tahoma", Font.BOLD, 20);
	public static final Font FUENTE_SUB_TITULO = new Font("Tahoma", Font.BOLD, 16);
	
	public static DateFormat FORMATO_FECHA = new SimpleDateFormat("dd/MM/yyyy");
	public static int ANCHO = 1100;
	public static int ALTO = 700;
	public static String VERSION = "20210903.1";
	
	public static ImageIcon ICONO_AGREGAR = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/agregar.png"));
	public static ImageIcon ICONO_QUITAR = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/eliminar.png"));
	public static ImageIcon ICONO_ELIMINAR = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/Trash-empty-icon.png"));
	public static ImageIcon ICONO_IMPORTAR = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/icons8-xls-import-24.png"));
	public static ImageIcon ICONO_EXPORTAR = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/icons8-exportacion-xls-24.png"));
	
	public static MaskFormatter getMascaraFecha(){
		try {
			return new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
