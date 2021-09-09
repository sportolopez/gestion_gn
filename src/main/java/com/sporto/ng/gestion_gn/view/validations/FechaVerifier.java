package com.sporto.ng.gestion_gn.view.validations;

import java.text.ParseException;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

import com.sporto.ng.gestion_gn.config.Constants;

public class FechaVerifier extends InputVerifier {

	private String campo;
	
	private java.util.Set<String> camposInvalidos;

	public FechaVerifier(String nombreCampo, java.util.Set<String> camposInvalidos) {
		this.campo = nombreCampo;
		this.camposInvalidos = camposInvalidos;
	}

	@Override
	public boolean verify(JComponent input) {
		String text = ((JTextField) input).getText();
		try {
			Constants.FORMATO_FECHA.setLenient(false);
			Constants.FORMATO_FECHA.parse(text);
			camposInvalidos.remove(campo);

			return true;
		} catch (ParseException e) {

			camposInvalidos.add(campo);
			return true;
		}
	}

}
