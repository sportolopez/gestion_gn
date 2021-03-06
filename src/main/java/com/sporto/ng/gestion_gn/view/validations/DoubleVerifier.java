package com.sporto.ng.gestion_gn.view.validations;

import java.util.Set;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

import com.jgoodies.common.base.Strings;
import com.sporto.ng.gestion_gn.config.Constants;

public class DoubleVerifier extends InputVerifier {

	private String campo;
	private java.util.Set<String> camposInvalidos; 
	private boolean obligatorio;
	public DoubleVerifier(String nombreCampo, java.util.Set<String> camposInvalidos) {
		obligatorio = true;
		this.campo = nombreCampo;
		this.camposInvalidos = camposInvalidos;
	}

	public DoubleVerifier(String nombreCampo, Set<String> camposInvalidos, boolean obligatorio) {
		this.obligatorio = obligatorio;
		this.campo = nombreCampo;
		this.camposInvalidos = camposInvalidos;
	}

	@Override
	public boolean verify(JComponent input) {
		String text = ((JTextField) input).getText();
		if(!((JTextField) input).isEditable()) {
			//Cuenta EFECTIVO
			camposInvalidos.remove(campo);
			return true;
		}
		
		if(Strings.isEmpty(text) && obligatorio) {
			camposInvalidos.add(campo);
			return true;
		}
		
		try {
			Double value = Constants.parseDouble(text);
			boolean b = value > 0;
			if(b) 
				camposInvalidos.remove(campo);
			else
				camposInvalidos.add(campo);
			
			return true;
		} catch (Exception e) {
			System.out.println(e);
			camposInvalidos.add(campo);
			return true;
		}
	}
}
