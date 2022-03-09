package com.sporto.ng.gestion_gn.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@IdClass(PedidoServicioId.class)
public class PedidoServicio implements ItemImprimible, Serializable {

	@Id
	@ManyToOne
	private Pedido pedido;
	@Id
	@ManyToOne
	private Servicio servicio;

	private double precio;
	private String comentario;
	
	@Override
	public String getId() {
		return servicio.getNombre();
	}
	@Override
	public String getDescripcion() {
		return comentario;
	}
	@Override
	public int getCantidad() {
		return 1;
	}
	@Override
	public String getDescuento() {
		return "";
	}
	@Override
	public double calcularSubtotal() {
		return precio;
	}
	
}
