package com.sporto.ng.gestion_gn.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.MovimientoCaja;

public interface MovimientoCajaDao extends CrudRepository<MovimientoCaja, Integer> {

	Optional<MovimientoCaja> findById(Integer id);

	List<MovimientoCaja> findAll();

	@Query("select  M from MovimientoCaja M where day(M.fecha) = ?1 and month(M.fecha) = ?2 and year(M.fecha) = ?3")
	List<MovimientoCaja> findByFecha(int dia, int mes, int year);
	
	List<MovimientoCaja> findByCliente(Cliente cliente);
}