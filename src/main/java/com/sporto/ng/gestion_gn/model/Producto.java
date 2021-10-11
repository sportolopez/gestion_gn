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
	
//	@ElementCollection(fetch = FetchType.EAGER)
//	@CollectionTable(name = "precio", joinColumns = @JoinColumn(name = "id_producto"))
//	@MapKeyColumn(name = "lista")
//	@Column(name = "precio")
//	private Map<String, Double> precios;
	//@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
	// @JoinColumn(name = "id_producto")
	//private Set<MovimientoStock> movimientoStock;

//	public Set<Entry<String, Double>> getPreciosSet() {
//		if (precios != null)
//			return precios.entrySet();
//		else
//			return new HashSet<Entry<String, Double>>();
//	}

//	public String getPreciosString() {
//		StringBuffer sb = new StringBuffer();
//		for (Entry<String, Double> iterable_element : precios.entrySet()) {
//			sb.append(iterable_element.getKey());
//			sb.append(": ");
//			sb.append(iterable_element.getValue());
//			sb.append(" ");
//		}
//		return sb.toString();
//	}
	
	public Integer getStock() {
		return ingresos - egresos;
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
