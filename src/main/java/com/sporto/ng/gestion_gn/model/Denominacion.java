package com.sporto.ng.gestion_gn.model;

public enum Denominacion {
	BILLETE_1000("1000"), BILLETE_500("500"), BILLETE_200("200"), BILLETE_100("100"), BILLETE_VARIOS("Varios");

	private String stringValue;

	Denominacion(String string) {
		this.stringValue = string;
	}

	public String getStringValue() {
		return stringValue;
	}
}
