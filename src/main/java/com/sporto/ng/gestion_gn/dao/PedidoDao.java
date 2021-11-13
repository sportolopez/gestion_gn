package com.sporto.ng.gestion_gn.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.EstadoPedido;
import com.sporto.ng.gestion_gn.model.Pedido;

public interface PedidoDao extends CrudRepository<Pedido, Integer> {

	List<Pedido> findByEstado(EstadoPedido estado);
	List<Pedido> findByClienteAndEstado(Cliente cliente, EstadoPedido estado);
	
}