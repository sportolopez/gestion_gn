package com.sporto.ng.gestion_ng.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * razón social nombre, apellido, mail, teléfono, límite de deuda (un flag que
 * le permite a la empresa limitar la venta o no, el sistema analiza la cta cte
 * total del cliente, si es mayor al flag cancelará la venta), dirección, cuit /
 * dni.
 * 
 * @author sebap
 *
 */

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String razonSocial;
	private String nombre;
	private String apellido;
	private String mail;
	private String telefono;
	private Float limiteDeuda;
	private String domicilio;
	private String documento;

}
