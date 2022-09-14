package com.sporto.ng.gestion_gn.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@ToString
public class MovimientoCaja {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne
	private Cliente cliente;
	@Enumerated(EnumType.STRING)
	private MedioPago medioPago;
	@Enumerated(EnumType.STRING)
	private TipoMovimiento tipoMovimiento;
	private Date fecha;
	private Double monto;
	private String comentario;
	@Transient
	private String detalleGasto;
	private String denominacion;	
}
