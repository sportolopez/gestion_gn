package com.sporto.ng.gestion_gn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	private String cuit;
	private String email;
	private String telefono;
	@Column(columnDefinition = "double default 0")
	private double limiteDeuda;
	@ManyToOne
	private Lista listaPrecio;
	private String domicilio;
	@Enumerated(EnumType.STRING)
	private TipoCuenta tipoCuenta;

}
