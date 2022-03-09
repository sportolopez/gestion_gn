package com.sporto.ng.gestion_gn.model;

public interface ItemImprimible {
	public String getId();
	public String getDescripcion();
	public double getPrecio();
	public int getCantidad();
	public String getDescuento();
	public double calcularSubtotal();
}
