package com.sporto.ng.gestion_gn.config;

import java.awt.Font;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.text.MaskFormatter;

import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.view.model.PedidoProductoTableModel;

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
	public static String VERSION = "20211125.1";
	
	public static ImageIcon ICONO_AGREGAR = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/agregar.png"));
	public static ImageIcon ICONO_QUITAR = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/eliminar.png"));
	public static ImageIcon ICONO_ELIMINAR = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/Trash-empty-icon.png"));
	public static ImageIcon ICONO_CANCELAR = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/cancelar.png"));
	public static ImageIcon ICONO_ENTREGADO = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/delivered.png"));
	public static ImageIcon ICONO_IMPORTAR = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/icons8-xls-import-24.png"));
	public static ImageIcon ICONO_EXPORTAR = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/icons8-exportacion-xls-24.png"));
	public static ImageIcon ICONO_EDITAR = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/Pencil-icon.png"));
	public static ImageIcon ICONO_LIBERAR = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/tick.png"));
	public static ImageIcon ICONO_DETALLE = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/eye.png"));
	public static ImageIcon ICONO_PEDIDO = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/icons8-shopping-cart-24.png"));
	public static ImageIcon ICONO_PAGO = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/icons8-us-dollar-25.png"));
	public static ImageIcon ICONO_CAJA = new ImageIcon(Constants.class.getClassLoader().getResource("iconos/cajero-automatico.png"));
	
	private static List<Lista> LISTAS;
	
	
	public static MaskFormatter getMascaraFecha(){
		try {
			return new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String outFecha(Date fecha) {
		if(fecha == null)
			return "";
		
		return FORMATO_FECHA.format(fecha);
	}
	
	
	public static void setListas(List<Lista> lista) {
		LISTAS = lista;
	}
	
	public static Lista getLista(String nombreLista) {
		for (Lista lista : LISTAS) {
			if(lista.getNombre().equals(nombreLista))
				return lista;
		}
		return Lista.builder().nombre("-").build();
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	public static double round(double value) {
		return round(value,2);
	}
	
	public static String outDouble(double value) {
		return String.format("%1$,.2f",value);
	}
	
	public static Double parseDouble(String value) {
		try {
			return NumberFormat.getInstance().parse(value).doubleValue();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
