/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sporto.ng.gestion_ng.model;

import java.util.Date;
import java.util.Map;

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
    private String descripcion;
    private boolean activo;
    private Date fechaVencimiento;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "precio", joinColumns = @JoinColumn(name = "id_producto"))
    @MapKeyColumn(name = "lista")
    @Column(name = "precio")
    private Map<String,Double> precios;
    
    
    
}
