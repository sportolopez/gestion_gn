/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sporto.ng.gestion_ng.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

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
	private int idProducto;
    private int stock;
    private String descripcion;
    private boolean activo;
    private Date fechaVencimiento;
    private Float precioAmba;
    private Float precioVip;
    private Float precioInterior;
    private Float precioGeneral;
}
