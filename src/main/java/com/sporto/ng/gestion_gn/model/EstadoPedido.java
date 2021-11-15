package com.sporto.ng.gestion_gn.model;

import java.awt.Color;

public enum EstadoPedido {
	EMITIDO(new Color(255,90,90)),LIBERADO(new Color(247,255,122)),RETIRADO(new Color(126,255,134)),CANCELADO(Color.LIGHT_GRAY);
	
	private Color color;
	
	private EstadoPedido(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
}
