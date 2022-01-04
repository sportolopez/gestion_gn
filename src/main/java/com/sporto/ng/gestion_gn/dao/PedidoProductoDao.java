package com.sporto.ng.gestion_gn.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sporto.ng.gestion_gn.model.EstadoPedido;
import com.sporto.ng.gestion_gn.model.Pedido;
import com.sporto.ng.gestion_gn.model.PedidoProducto;
import com.sporto.ng.gestion_gn.model.PedidoProductoId;
import com.sporto.ng.gestion_gn.model.Producto;

public interface PedidoProductoDao extends CrudRepository<PedidoProducto, PedidoProductoId> {

	public Collection<PedidoProducto> findByPedido(Pedido pedido);
	public List<PedidoProducto> findByProductoAndPedidoEstadoNot(Producto findById, EstadoPedido estado);
	
}