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

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;

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
@ToString
public class Producto {

	@Id
	private int id;
	private int stock;
	private String descripcion;
	private boolean activo;
	private Date fechaVencimiento;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "precio", joinColumns = @JoinColumn(name = "id_producto"))
	@MapKeyColumn(name = "lista")
	@Column(name = "precio")
	private Map<String, Double> precios;

	public Set<Entry<String, Double>> getPreciosSet() {
    	if(precios!= null)
    		return precios.entrySet();
    	else
    		return new HashSet<Entry<String, Double>>();
    }

}
