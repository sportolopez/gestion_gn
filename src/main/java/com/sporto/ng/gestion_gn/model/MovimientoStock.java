package com.sporto.ng.gestion_gn.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder(toBuilder = true)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@ToString
public class MovimientoStock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
//	@ManyToOne(fetch = FetchType.LAZY)
//	private Producto producto;
	private Date fecha;
	private int cantidad;
	private TipoMovimiento tipoMovimiento;
	private String usuario;

}
