package com.sporto.ng.gestion_gn.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
import lombok.ToString;

@Entity
@Builder(toBuilder = true)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MovimientoStock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	private Producto producto;
	private Date fecha;
	private int cantidad;
	@Enumerated(EnumType.STRING)
	private TipoMovimiento tipoMovimiento;
	private String usuario;
	private Date fechaVencimiento;
	private Integer remito;
	private String ordenCompra;


}
