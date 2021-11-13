package com.sporto.ng.gestion_gn.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Formula;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Cliente cliente;
	
//	@ManyToMany
//	@JoinTable(
//	  name = "course_like", 
//	  joinColumns = @JoinColumn(name = "student_id"), 
//	  inverseJoinColumns = @JoinColumn(name = "course_id"))
//	@ManyToMany
//	private Collection<PedidoProducto> productos;
//    @OneToMany()
//    @JoinColumn(name = "pedido")
//    private Collection<PedidoProducto> productos;
//    
    
	@Formula("(select sum(pp.precio * pp.cantidad * (1-SUBSTRING(pp.descuento,1,1)/100)) from pedido_producto pp where pp.pedido_id = id)")
	private Double monto;
	
	
	@Enumerated(EnumType.STRING)
	private EstadoPedido estado;
	private Date fecha;

}
