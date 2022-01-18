/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sporto.ng.gestion_gn.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLDelete;

import com.sporto.ng.gestion_gn.config.Constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author sebap
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Producto {

	@Id
	private int id;
	
	private String categoria;
	private String descripcion;
	private boolean activo;
	private Date fechaVencimiento;
	@Column(columnDefinition = "double default 0")
	private Double costo;
	@Formula("(select COALESCE(sum(MS.cantidad),0) from movimiento_stock MS where MS.producto_id = id and MS.tipo_movimiento = 'INGRESO')")
	private Integer ingresos;
	@Formula("(select COALESCE(sum(MS.cantidad),0) from movimiento_stock MS where MS.producto_id = id and MS.tipo_movimiento = 'EGRESO')")
	private Integer egresos;
	@Formula("(select COALESCE(sum(pp.cantidad),0) from pedido_producto pp, pedido p where p.id = pp.pedido_id && pp.producto_id = id and p.estado != 'ANULADO')")
	private Integer bloqueado_pedido;
	
	public Integer getStock() {
		return ingresos - egresos - bloqueado_pedido;
	}

	public String getFechaString() {
		if (fechaVencimiento == null) {
			return "";
		} else
			return Constants.FORMATO_FECHA.format(getFechaVencimiento());
	}
	
	public String getDescripcionCombo() {
		return getId() + ": " + getDescripcion();
	}
	
}
