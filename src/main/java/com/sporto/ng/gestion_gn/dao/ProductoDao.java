package com.sporto.ng.gestion_gn.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sporto.ng.gestion_gn.model.Producto;

public interface ProductoDao extends CrudRepository<Producto, Integer> {

	Optional<Producto> findById(Integer id);

//  @Modifying
//  @Query("update EARAttachment ear set ear.status = ?1 where ear.id = ?2")
//  int bajaLogica(Integer status, Long id);

	@Query("SELECT P from Producto P where activo = true")
	List<Producto> findAll();

	@Modifying
	@Query("UPDATE Producto SET activo = false WHERE id=:idProducto")
	void deleteById(Integer idProducto);
}