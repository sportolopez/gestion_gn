package com.sporto.ng.gestion_gn.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sporto.ng.gestion_gn.model.Pedido;
import com.sporto.ng.gestion_gn.model.PedidoServicio;
import com.sporto.ng.gestion_gn.model.PedidoServicioId;

public interface PedidoServicioDao extends CrudRepository<PedidoServicio, PedidoServicioId> {
	
	@Query(value="select nombre from servicio", nativeQuery = true)
	List<String> findAllServicios();
	public Collection<PedidoServicio> findByPedido(Pedido unPedido);
}