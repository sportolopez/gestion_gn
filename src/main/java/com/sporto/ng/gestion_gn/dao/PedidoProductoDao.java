package com.sporto.ng.gestion_gn.dao;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import com.sporto.ng.gestion_gn.model.Pedido;
import com.sporto.ng.gestion_gn.model.PedidoProducto;
import com.sporto.ng.gestion_gn.model.PedidoProductoId;

public interface PedidoProductoDao extends CrudRepository<PedidoProducto, PedidoProductoId> {

	public Collection<PedidoProducto> findByPedido(Pedido pedido);
	
}