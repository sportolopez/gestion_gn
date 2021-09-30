package com.sporto.ng.gestion_gn.config;

import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.text.MaskFormatter;

public class Constants {
	public static Font FUENTE = new Font("Dialog", Font.PLAIN, 12);
	public static DateFormat FORMATO_FECHA = new SimpleDateFormat("dd/MM/yyyy");
	public static int ANCHO = 1100;
	public static int ALTO = 700;
	public static String VERSION = "20210913.1";
	
	public static MaskFormatter getMascaraFecha(){
		try {
			return new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
