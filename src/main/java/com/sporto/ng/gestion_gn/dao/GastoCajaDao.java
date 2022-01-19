package com.sporto.ng.gestion_gn.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sporto.ng.gestion_gn.model.GastoCaja;

public interface GastoCajaDao extends CrudRepository<GastoCaja, Integer> {

	Optional<GastoCaja> findById(Integer id);

	List<GastoCaja> findAll();
	@Query("select  M from GastoCaja M where day(M.fecha) = ?1 and month(M.fecha) = ?2 and year(M.fecha) = ?3")
	List<GastoCaja> findByFecha(int dia, int mes, int year);
	
	@Query(value="select nombre from caja_egreso_motivo", nativeQuery = true)
	List<String> findMotivosEgreso();
}