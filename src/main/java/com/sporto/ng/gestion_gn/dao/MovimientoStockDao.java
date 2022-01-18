package com.sporto.ng.gestion_gn.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sporto.ng.gestion_gn.model.MovimientoStock;
import com.sporto.ng.gestion_gn.model.Producto;

public interface MovimientoStockDao extends CrudRepository<MovimientoStock, Integer> {

	Optional<MovimientoStock> findById(Integer id);

	List<MovimientoStock> findAll();
	
	List<MovimientoStock> findByProducto(Producto producto);
	
	List<MovimientoStock> findByProductoOrderByFechaDesc(Producto producto);
}