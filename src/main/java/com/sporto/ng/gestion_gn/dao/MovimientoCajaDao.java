package com.sporto.ng.gestion_gn.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.MovimientoCaja;
import com.sporto.ng.gestion_gn.model.MovimientoStock;

public interface MovimientoCajaDao extends CrudRepository<MovimientoCaja, Integer> {

	Optional<MovimientoCaja> findById(Integer id);

	List<MovimientoCaja> findAll();
	
	List<MovimientoCaja> findByCliente(Cliente cliente);
}