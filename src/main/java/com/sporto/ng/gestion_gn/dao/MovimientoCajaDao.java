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
	
	@Query(value="select sum(monto) as monto, fecha, cliente_id, id,comentario, medio_pago, tipo_movimiento, denominacion from movimiento_caja M where M.cliente_id =?1 group by fecha order by fecha desc", nativeQuery = true)
	List<MovimientoCaja> findGroupByLiberado(int cliente_id);
	
	@Query(value="select monto, fecha, cliente_id, id,comentario, medio_pago, tipo_movimiento, denominacion from movimiento_caja M where M.cliente_id =?1 and M.fecha = ?2", nativeQuery = true)
	List<MovimientoCaja> findLiberado(int cliente_id, String fecha);
	

	
}