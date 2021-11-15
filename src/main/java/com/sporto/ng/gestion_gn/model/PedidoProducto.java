package com.sporto.ng.gestion_gn.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
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
@IdClass(PedidoProductoId.class)
public class PedidoProducto implements Serializable {

	@Id
	@ManyToOne
	private Pedido pedido;
	@Id
	@ManyToOne
	private Producto producto;

	private double precio;
	private String descuento;
	private int cantidad;
	
	public double calcularSubtotal() {
		double descuentoParsed = 1 - (double)Integer.parseInt(descuento.substring(0, 1)) / 100;
		double d = precio * descuentoParsed;
		return d * cantidad;
	}
}
