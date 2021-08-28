package com.sporto.ng.gestion_ng.dao;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sporto.ng.gestion_ng.model.Producto;

public interface ProductoDao extends CrudRepository<Producto, Integer> {

  Optional<Producto> findById(Integer id);
}