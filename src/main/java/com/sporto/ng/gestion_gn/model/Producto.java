/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sporto.ng.gestion_gn.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import com.sporto.ng.gestion_gn.config.Constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author sebap
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class Producto {

	@Id
	private int id;
	private int stock;
	private String categoria;
	private String descripcion;
	private boolean activo;
	private Date fechaVencimiento;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "precio", joinColumns = @JoinColumn(name = "id_producto"))
	@MapKeyColumn(name = "lista")
	@Column(name = "precio")
	private Map<String, Double> precios;
	@Column(columnDefinition = "double default 0")
	private Double costo;

	//@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
	// @JoinColumn(name = "id_producto")
	//private Set<MovimientoStock> movimientoStock;

	public Set<Entry<String, Double>> getPreciosSet() {
		if (precios != null)
			return precios.entrySet();
		else
			return new HashSet<Entry<String, Double>>();
	}

	public String getPreciosString() {
		StringBuffer sb = new StringBuffer();
		for (Entry<String, Double> iterable_element : precios.entrySet()) {
			sb.append(iterable_element.getKey());
			sb.append(": ");
			sb.append(iterable_element.getValue());
			sb.append(" ");
		}
		return sb.toString();
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
