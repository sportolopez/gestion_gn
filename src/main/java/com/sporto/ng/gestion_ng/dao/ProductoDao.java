package com.sporto.ng.gestion_ng.dao;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sporto.ng.gestion_ng.model.Producto;

@Repository
public interface ProductoDao extends CrudRepository<Producto, Integer> {

  Optional<Producto> findById(Integer id);
}