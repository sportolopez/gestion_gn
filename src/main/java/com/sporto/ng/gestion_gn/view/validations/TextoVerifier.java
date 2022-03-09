package com.sporto.ng.gestion_gn.view.validations;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

import com.jgoodies.common.base.Strings;

public class TextoVerifier extends InputVerifier {

	private String campo;
	private java.util.Set<String> camposInvalidos;

	public TextoVerifier(String nombreCampo, java.util.Set<String> camposInvalidos) {
		this.campo = nombreCampo;
		this.camposInvalidos = camposInvalidos;
	}

	@Override
	public boolean verify(JComponent input) {
		String text = ((JTextField) input).getText();
		if (Strings.isEmpty(text)) {
			camposInvalidos.add(campo);
		} else {
			camposInvalidos.remove(campo);
		}
		return true;
	}
}
