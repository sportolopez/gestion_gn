package com.sporto.ng.gestion_gn.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import com.sporto.ng.gestion_gn.config.Constants;

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
public class PedidoProducto implements ItemImprimible, Serializable {

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
		double descuentoParsed = 1 - (double)Integer.parseInt(descuento) / 100;
		double d = precio * descuentoParsed;
		return Constants.round(d * cantidad, 2);
	}

	@Override
	public String getId() {
		return String.valueOf(producto.getId());
	}

	@Override
	public String getDescripcion() {
		return producto.getDescripcion();
	}

}
