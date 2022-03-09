package com.sporto.ng.gestion_gn.view.validations;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

import com.jgoodies.common.base.Strings;

public class NumeroVerifier extends InputVerifier {

	private String campo;
	private java.util.Set<String> camposInvalidos;
	private int minimo;
	private int maximo;

	public NumeroVerifier(String nombreCampo, java.util.Set<String> camposInvalidos) {
		this.campo = nombreCampo;
		this.camposInvalidos = camposInvalidos;
		minimo = 0;
		maximo = Integer.MAX_VALUE;
	}

	public NumeroVerifier(String nombreCampo, java.util.Set<String> camposInvalidos, int min, int max) {
		this.campo = nombreCampo;
		this.camposInvalidos = camposInvalidos;
		this.minimo = min;
		this.maximo = max;
	}

	@Override
	public boolean verify(JComponent input) {
		String text = ((JTextField) input).getText();
		if (Strings.isEmpty(text)) {
			camposInvalidos.add(campo);
			return true;
		}

		try {
			Integer value = Integer.valueOf(text);
			boolean b = value > minimo && value < maximo;
			if (b)
				camposInvalidos.remove(campo);
			else
				camposInvalidos.add(campo);

			return true;
		} catch (NumberFormatException e) {
			System.out.println(e);
			camposInvalidos.add(campo);
			return true;
		}
	}
	
}
