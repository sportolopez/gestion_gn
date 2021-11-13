package com.sporto.ng.gestion_gn.model;

import java.awt.Color;

public enum EstadoPedido {
	EMITIDO(new Color(255,90,90)),LIBERADO(new Color(247,255,122)),RETIRADO(new Color(92,51,255)),ANULADO(new Color(118,112,131)),CANCELADO(Color.LIGHT_GRAY);
	
	private Color color;
	
	private EstadoPedido(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
}
