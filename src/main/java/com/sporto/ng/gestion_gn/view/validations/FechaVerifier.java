package com.sporto.ng.gestion_gn.view.validations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class FechaVerifier extends InputVerifier {

	private String campo;
	private DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	private java.util.Set<String> camposInvalidos;

	public FechaVerifier(String nombreCampo, java.util.Set<String> camposInvalidos) {
		this.campo = nombreCampo;
		this.camposInvalidos = camposInvalidos;
	}

	@Override
	public boolean verify(JComponent input) {
		String text = ((JTextField) input).getText();
		try {
			formatoFecha.setLenient(false);
			Date parse = formatoFecha.parse(text);
			camposInvalidos.remove(campo);

			return true;
		} catch (ParseException e) {

			camposInvalidos.add(campo);
			return true;
		}
	}

}
