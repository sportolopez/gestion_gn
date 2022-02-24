package com.sporto.ng.gestion_gn.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.MovimientoCaja;

public interface MovimientoCajaDao extends CrudRepository<MovimientoCaja, Integer> {

	Optional<MovimientoCaja> findById(Integer id);

	List<MovimientoCaja> findAll();

	@Query("select  M from MovimientoCaja M where day(M.fecha) = ?1 and month(M.fecha) = ?2 and year(M.fecha) = ?3")
	List<MovimientoCaja> findByFecha(int dia, int mes, int year);
	
	List<MovimientoCaja> findByCliente(Cliente cliente);
	
	@Query(value="select sum(monto) as monto, fecha, cliente_id, id,comentario, medio_pago, tipo_movimiento, denominacion from movimiento_caja M where M.cliente_id =?1 group by fecha order by fecha desc", nativeQuery = true)
	List<MovimientoCaja> findPagosCliente(int cliente_id);
	
	@Query(value="select monto, fecha, cliente_id, id,comentario, medio_pago, tipo_movimiento, denominacion from movimiento_caja M where M.cliente_id =?1 and M.fecha = ?2", nativeQuery = true)
	List<MovimientoCaja> findLiberado(int cliente_id, String fecha);

	@Modifying
	@Query(value="insert into cierre_caja (fecha,monto) values (:fecha,:monto) ",nativeQuery = true)
	List<MovimientoCaja> insertCierreCaja(@Param(value = "fecha") Date fecha,@Param(value = "monto")  Double monto);
	
	@Query(value="select monto from cierre_caja where fecha =?1 ", nativeQuery = true)
	Double selectCierreCaja(Date fech);

	@Query(value="select monto from cierre_caja cc where fecha < DATE(sysdate()) order by fecha desc limit 1 ", nativeQuery = true)
	Double selectMontoUltimoCierre();
	
	@Query(value="select fecha from cierre_caja cc where fecha < DATE(sysdate()) order by fecha desc limit 1 ", nativeQuery = true)
	Date selectFechaUltimoCierre();
	
}